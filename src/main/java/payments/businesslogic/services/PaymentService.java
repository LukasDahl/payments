/**
 * @author Wassim
 */

package payments.businesslogic.services;

import bankservice.BankService;
import bankservice.BankServiceException_Exception;
import payments.businesslogic.Interfaces.IPaymentService;
import payments.businesslogic.Interfaces.IQueueService;
import payments.businesslogic.exceptions.DtuPaySystemException;
import payments.businesslogic.exceptions.MerchantNotFound;
import payments.businesslogic.exceptions.QueueException;
import payments.businesslogic.exceptions.TokenAlreadyUsed;
import payments.businesslogic.exceptions.TokenNotFound;
import payments.businesslogic.models.Account;
import payments.businesslogic.models.Payment;
import payments.businesslogic.models.TokenInfo;
import payments.businesslogic.models.Transaction;
import payments.repository.IPaymentRepository;

public class PaymentService implements IPaymentService {

    private BankService bankService;
    private IQueueService queueService;
    private IPaymentRepository paymentRepository;

    public PaymentService(IPaymentRepository paymentRepository, BankService bankService, IQueueService queueService) {
        this.bankService = bankService;
        this.queueService = queueService;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Transaction createPayment(Payment payment) throws MerchantNotFound, TokenNotFound, DtuPaySystemException,
            TokenAlreadyUsed, QueueException, BankServiceException_Exception {

        // 1. check token and get customer account Id
        TokenInfo tokenInfo = this.queueService.validateToken(payment.getToken());
        if (tokenInfo.getCustomerId() == null || tokenInfo.getCustomerId().trim().isEmpty()) {
            throw new TokenNotFound("Token is unknown");
        } else if (tokenInfo.getIsUsed()) {
            throw new TokenAlreadyUsed("Token is already used");
        }

        // 2. check merchant and get merchant's bank account Id
        Account merchantAccount = this.queueService.validateAccount(payment.getMerchantId());
        if (merchantAccount.getBankAccountId() == null || merchantAccount.getBankAccountId().trim().isEmpty()) {
            throw new MerchantNotFound("Merchant is unknown");
        }

        // 3. check customer and get customer's bank account Id
        Account customerAccount = this.queueService.validateAccount(tokenInfo.getCustomerId());
        if (customerAccount.getBankAccountId() == null || customerAccount.getBankAccountId().trim().isEmpty()) {
            throw new DtuPaySystemException("Data inconsistency: customer not found!");
        }

        // 4. call bank
        this.bankService.transferMoneyFromTo(customerAccount.getBankAccountId(), merchantAccount.getBankAccountId(),
                payment.getAmount(), payment.getDescription());

        // 5. create a transaction
        Transaction transaction = new Transaction(payment.getAmount(), payment.getToken(), merchantAccount.getId(),
                customerAccount.getId(), payment.getDescription());

        // 6. store transaction in db
        this.paymentRepository.saveTransaction(transaction);

        // 7. publish transaction created event
        this.queueService.publishTransactionCreatedEvent(transaction);

        return transaction;
    }

    @Override
    public Transaction getPayment(String id) {
        return this.paymentRepository.getTransaction(id);
    }
}
