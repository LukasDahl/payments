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
    public void createPayment(Payment payment) throws MerchantNotFound, TokenNotFound, DtuPaySystemException,
            TokenAlreadyUsed, QueueException, BankServiceException_Exception {

        // 1. check token and get customer account Id
        TokenInfo tokenInfo = this.queueService.validateToken(payment.Token);
        if (tokenInfo == null) {
            throw new TokenNotFound("Token is unknown");

            // TODO: align reponse error codes to throw the appropriate exception
            // throw new TokenAlreadyUsed("Token is already used");
        }

        // 2. check merchant and get merchant's bank account Id
        Account merchantAccount = this.queueService.validateAccount(payment.MerchantId);
        if (merchantAccount == null) {
            throw new MerchantNotFound("Merchant is unknown");
        }

        // 3. check customer and get customer's bank account Id
        Account customerAccount = this.queueService.validateAccount(tokenInfo.CustomerId);
        if (customerAccount == null) {
            throw new DtuPaySystemException("Data inconsistency: customer not found!");
        }

        // 4. call bank
        this.bankService.transferMoneyFromTo(customerAccount.BankAccountId, merchantAccount.BankAccountId,
                payment.Amount, payment.Description);

        // 5. create a transaction
        Transaction transaction = new Transaction(payment.Amount, payment.Token, merchantAccount.AccountId,
                customerAccount.AccountId, payment.Description);

        // 6. store transaction in db
        this.paymentRepository.saveTransaction(transaction);

        // 7. publish transaction created event
        this.queueService.publishTransactionCreatedEvent(transaction);
    }
}
