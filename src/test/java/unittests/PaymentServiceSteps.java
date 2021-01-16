package unittests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.MathContext;

import bankservice.BankService;
import bankservice.BankServiceException_Exception;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
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
import payments.businesslogic.services.PaymentService;
import payments.repository.IPaymentRepository;

public class PaymentServiceSteps {

    String token;
    String merchantId;
    BigDecimal amount;
    String description;
    Payment payment;

    BankService bankService;
    IQueueService queueService;
    IPaymentService paymentService;
    IPaymentRepository paymentRepository;

    private void initLocalVars() {
        this.token = "token";
        this.merchantId = "mid1";
        this.amount = new BigDecimal(100, MathContext.DECIMAL64);
        this.description = "test payment";

        bankService = mock(BankService.class);
        queueService = mock(IQueueService.class);
        paymentRepository = mock(IPaymentRepository.class);

        paymentService = new PaymentService(paymentRepository, bankService, queueService);
    }

    @Before
    public void initialization() {
        initLocalVars();
    }

    @Given("An unknown token {string}")
    public void anUnknownToken(String token) throws QueueException {
        when(queueService.validateToken(token)).thenReturn(new TokenInfo(token, false, ""));
    }

    @When("Creating payment with token {string}")
    public void creatingPaymentWithToken(String token) throws QueueException {
        this.payment = new Payment(amount, token, merchantId, description);
    }

    @Then("An exception of type TokenNotFound is thrown")
    public void anExceptionOfTypeTokenNotFoundIsThrown() {
        assertThrows(TokenNotFound.class, () -> this.paymentService.createPayment(payment));
    }

    @Given("An already used token {string}")
    public void anAlreadyUsedToken(String token) throws QueueException {
        when(queueService.validateToken(token)).thenReturn(new TokenInfo(token, true, "cid1"));
    }

    @Then("An exception of type TokenAlreadyUsed is thrown")
    public void anExceptionOfTypeTokenAlreadyUsedIsThrown() {
        assertThrows(TokenAlreadyUsed.class, () -> this.paymentService.createPayment(payment));
    }

    @Given("A valid token {string} for customer {string}")
    public void aValidToken(String token, String customerId) throws QueueException {
        this.token = token;
        when(queueService.validateToken(token)).thenReturn(new TokenInfo(token, false, customerId));
    }

    @And("An unknown merchant {string}")
    public void anUnknownMerchant(String merchantId) throws QueueException {
        when(queueService.validateAccount(merchantId)).thenReturn(new Account(merchantId, ""));
    }

    @When("Creating payment with merchant {string}")
    public void creatingPaymentWithMerchant(String merchantId) throws QueueException {
        this.payment = new Payment(amount, token, merchantId, description);
    }

    @Then("An exception of type MerchantNotFound is thrown")
    public void anExceptionOfTypeMerchantNotFoundIsThrown() {
        assertThrows(MerchantNotFound.class, () -> this.paymentService.createPayment(payment));
    }

    @And("A valid merchant {string} with bank account {string}")
    public void aValidMerchant(String merchantId, String bankAccountId) throws QueueException {
        when(queueService.validateAccount(merchantId)).thenReturn(new Account(merchantId, bankAccountId));
    }

    @And("Customer account is not found for customer {string}")
    public void customerAccountNotFound(String customerId) throws QueueException {
        when(queueService.validateAccount(customerId)).thenReturn(new Account(customerId, ""));
    }

    @When("Creating payment with token {string} and merchant {string}")
    public void creatingPaymentWithTokenAndMerchant(String token, String merchantId) throws QueueException {
        this.payment = new Payment(amount, token, merchantId, description);
    }

    @Then("An exception of type DtuPaySystemException is thrown")
    public void anExceptionOfTypeDtuPaySystemExceptionIsThrown() {
        assertThrows(DtuPaySystemException.class, () -> this.paymentService.createPayment(payment));
    }

    @And("A valid customer {string} with bank account {string}")
    public void aValidCustomer(String customerId, String bankAccountId) throws QueueException {
        when(queueService.validateAccount(customerId)).thenReturn(new Account(customerId, bankAccountId));
    }

    @When("Creating payment with token {string} and merchant {string} with amount {double} and description {string}")
    public void creatingPaymentWithTokenAndMerchantWithAmountAndDescriptionß(String token, String merchantId,
            Double amount, String description) throws QueueException, MerchantNotFound, TokenNotFound, TokenAlreadyUsed,
            DtuPaySystemException, BankServiceException_Exception {
        this.payment = new Payment(new BigDecimal(amount, MathContext.DECIMAL64), token, merchantId, description);
        this.paymentService.createPayment(payment);
    }

    @Then("A bank transfer is triggered from account {string} to account {string} for an amount of {double} and description {string}")
    public void aBankTransferIsTriggeredWithTheCorrectParamerters(String fromAccount, String toAccount, Double amount,
            String description) throws BankServiceException_Exception {

        verify(bankService).transferMoneyFromTo(fromAccount, toAccount, new BigDecimal(amount, MathContext.DECIMAL64),
                description);
    }

    @Then("The transacation is saved and published")
    public void theTransactionIsSavedAndPublished() throws BankServiceException_Exception, QueueException {

        verify(paymentRepository).saveTransaction(any());
        verify(queueService).publishTransactionCreatedEvent(any());
    }

}
