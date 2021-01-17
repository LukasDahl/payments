/**
 * @author Wassim
 */

package servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import payments.rest.models.ErrorModel;

public class PaymentServiceSteps {

    String token, merchantId, description;
    ErrorModel error;

    PaymentServiceClient paymentServiceClient;
    TestQueueClient testQueueClient;

    public PaymentServiceSteps() {
        this.paymentServiceClient = new PaymentServiceClient();
        this.testQueueClient = new TestQueueClient();
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
        this.error = paymentServiceClient.createPayment(amount, this.token, this.merchantId, this.description);
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertNull(this.error);
    }

    @Then("the payment is not successful")
    public void thePaymentIsNotSuccessful() {
        assertNotNull(this.error);
    }

    @And("the error message should say {string}")
    public void theErrorMessageShouldSay(String message) {
        assertEquals(message, this.error.getMessage());
    }
}
