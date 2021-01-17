/**
 * @author Wassim
 */

package payments.rest.api;

import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bankservice.BankServiceException_Exception;
import payments.businesslogic.Interfaces.IPaymentService;
import payments.businesslogic.exceptions.DtuPaySystemException;
import payments.businesslogic.exceptions.MerchantNotFound;
import payments.businesslogic.exceptions.QueueException;
import payments.businesslogic.exceptions.TokenAlreadyUsed;
import payments.businesslogic.exceptions.TokenNotFound;
import payments.businesslogic.models.Payment;
import payments.rest.PaymentServiceFactory;
import payments.rest.models.CreatePaymentRequest;
import payments.rest.models.ErrorModel;

@Path("/payments")
public class PaymentsResource {

    private IPaymentService paymentService;

    public PaymentsResource() {
        paymentService = new PaymentServiceFactory().getService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPayment(CreatePaymentRequest createPaymentRequest)
            throws MerchantNotFound, TokenNotFound, TokenAlreadyUsed, DtuPaySystemException, QueueException,
            BankServiceException_Exception, URISyntaxException {

        try {
            createPaymentRequest.validate();

            Payment payment = new Payment(new BigDecimal(createPaymentRequest.getAmount(), MathContext.DECIMAL64),
                    createPaymentRequest.getToken(), createPaymentRequest.getMerchantId(),
                    createPaymentRequest.getDescription());

            var transaction = paymentService.createPayment(payment);

            return Response.created(new URI(String.format("payments/%s", transaction.getId()))).build();

        } catch (ValidationException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorModel(e.getMessage())).build();

        } catch (MerchantNotFound e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorModel(
                            String.format("merchant with id %s is unknown", createPaymentRequest.getMerchantId())))
                    .build();

        } catch (TokenNotFound e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorModel(String.format("token %s is unknown", createPaymentRequest.getToken())))
                    .build();

        } catch (TokenAlreadyUsed e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorModel(String.format("token %s is already used", createPaymentRequest.getToken())))
                    .build();

        } catch (DtuPaySystemException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorModel("DTUPay system error"))
                    .build();

        } catch (QueueException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorModel("DTUPay system error (queue)")).build();

        } catch (BankServiceException_Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorModel(String.format("DTUPay system error (bank): %s", e.getMessage()))).build();
        }
    }
}