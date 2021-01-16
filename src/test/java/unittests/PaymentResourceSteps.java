package unittests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import payments.businesslogic.Interfaces.IPaymentService;
import payments.businesslogic.exceptions.TokenNotFound;
import payments.rest.PaymentServiceFactory;

public class PaymentResourceSteps {

    String token;
    IPaymentService paymentService;
    PaymentServiceFactory paymentServiceFactory;

    private void initLocalVars() {
        paymentService = mock(IPaymentService.class);
        paymentServiceFactory = mock(PaymentServiceFactory.class);

        when(paymentServiceFactory.getService()).thenReturn(paymentService);
    }

    @Before
    public void initialization() {
        initLocalVars();
    }

    // @When("Creating payment with unknown token {string}")
    // public void creatingPaymentWithUnknownToken(String token) {
    // this.token = token;
    // when(paymentService).thenThrow(new TokenNotFound(token));
    // }

    // @Then("An exception of type TokenNotFound is thrown")
    // public void anExceptionOfTypeTokenNotFoundIsThrown() {
    // assertThrows(TokenNotFound.class, () ->
    // this.paymentService.createPayment(payment));
    // }

}
