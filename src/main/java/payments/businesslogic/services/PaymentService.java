/**
 * @author Wassim
 */

package payments.businesslogic.services;

import payments.businesslogic.Interfaces.IBankService;
import payments.businesslogic.Interfaces.IPaymentService;
import payments.businesslogic.Interfaces.IQueueService;
import payments.businesslogic.exceptions.DtuPaySystemError;
import payments.businesslogic.exceptions.MerchantNotFound;
import payments.businesslogic.exceptions.TokenAlreadyUsed;
import payments.businesslogic.exceptions.TokenNotFound;
import payments.businesslogic.models.Account;
import payments.businesslogic.models.Payment;
import payments.businesslogic.models.TokenInfo;
import payments.businesslogic.models.Transaction;
import payments.repository.IPaymentRepository;

public class PaymentService implements IPaymentService {

    private IBankService _bankService;
    private IQueueService _queueService;
    private IPaymentRepository _paymentRepository;

    public PaymentService(IPaymentRepository paymentRepository, IBankService bankService, IQueueService queueService) {
        _bankService = bankService;
        _queueService = queueService;
        _paymentRepository = paymentRepository;
    }

    @Override
    public void createPayment(Payment payment)
            throws MerchantNotFound, TokenNotFound, DtuPaySystemError, TokenAlreadyUsed {

        // 1. validation
        // 1.1. check merchant
        Account merchantAccount = this._paymentRepository.getMerchantAccount(payment.MerchantId);
        if (merchantAccount == null) {
            throw new MerchantNotFound("Merchant is not known");
        }

        // 1.2. check token
        TokenInfo tokenInfo = this._paymentRepository.getTokenInfo(payment.Token);
        if (tokenInfo == null) {
            throw new TokenNotFound("Token is not known");
        }

        // 1.3. check if token is already used
        if (tokenInfo.IsUsed) {
            throw new TokenAlreadyUsed("Token is already used");
        }

        // 1.4. check customer account
        Account customerAccount = this._paymentRepository.getCustomerAccount(tokenInfo.CustomerId);
        if (customerAccount == null) {
            throw new DtuPaySystemError("Data inconsistency: customer not found!");
        }

        // 2. call bank
        this._bankService.transferMoney(merchantAccount.BankAccountId, customerAccount.BankAccountId, payment.Amount,
                payment.Description);

        // 3. create a transaction
        Transaction transaction = new Transaction(payment.Amount, payment.Token, merchantAccount.AccountId,
                customerAccount.AccountId, payment.Description);

        // 4. store in db
        // 4.1. save transaction
        this._paymentRepository.saveTransaction(transaction);

        // 4.2. update token status
        this._paymentRepository.markTokenAsUsed(tokenInfo.Token);

        // 5. publish events
        // 5.1. token used event
        this._queueService.publishTokenUsedEvent(tokenInfo);

        // 5.2. transaction created event
        this._queueService.publishTransactionCreatedEvent(transaction);
    }
}
