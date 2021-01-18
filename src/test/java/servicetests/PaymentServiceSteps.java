/**
 * @author Wassim
 */

package servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.Random;

import bankservice.Account;
import bankservice.BankServiceException_Exception;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import payments.rest.models.ErrorModel;

public class PaymentServiceSteps {

    String token, merchantId, description;
    Account customerAccount, merchantAccount;
    ErrorModel errorResult;

    PaymentServiceClient paymentServiceClient;
    BankServiceClient bankServiceClient;
    TestQueueClient testQueueClient;

    public PaymentServiceSteps() throws BankServiceException_Exception {
        this.testQueueClient = new TestQueueClient();
        this.bankServiceClient = new BankServiceClient();
        this.paymentServiceClient = new PaymentServiceClient();

        // wanted to do this properly in the BeforeAll and AfterAll hooks
        // to clean the accounts but they are not working for me for some reason?!
        var CPR1 = String.format("150786-%04d", new Random().nextInt(10000));
        var CPR2 = String.format("170493-%04d", new Random().nextInt(10000));
        this.customerAccount = this.bankServiceClient.createBankAccount("Carmen", "Beard", CPR1, "1000");
        this.merchantAccount = this.bankServiceClient.createBankAccount("Cynthia", "Brownlee", CPR2, "3000");
        this.testQueueClient.setCustomerBankAccountId(customerAccount.getId());
        this.testQueueClient.setMerchantBankAccountId(merchantAccount.getId());

    }

    @Given("a token {string}")
    public void aToken(String token) {
        this.token = token;
    }

    @And("a merchant with id {string}")
    public void aMerchantWithId(String merchantId) {
        this.merchantId = merchantId;
    }

    @And("a description {string}")
    public void aDescription(String description) {
        this.description = description;
    }

    @When("the merchant initiates a payment for {int} kr by the customer")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(int amount) {
        this.errorResult = paymentServiceClient.createPayment(amount, this.token, this.merchantId, this.description);
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertNull(this.errorResult);
    }

    @Then("the payment is not successful")
    public void thePaymentIsNotSuccessful() {
        assertNotNull(this.errorResult);
    }

    @And("the error message should say {string}")
    public void theErrorMessageShouldSay(String message) {
        assertEquals(message, this.errorResult.getMessage());
    }
}
