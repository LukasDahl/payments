/**
 * @author Wassim
 */

package payments.rest.api;

import java.math.BigDecimal;
import java.math.MathContext;

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

    private static IPaymentService paymentService;

    public PaymentsResource() {
        paymentService = new PaymentServiceFactory().getService();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPayment(CreatePaymentRequest createPaymentRequest) throws MerchantNotFound, TokenNotFound,
            TokenAlreadyUsed, DtuPaySystemException, QueueException, BankServiceException_Exception {

        try {
            // TODO: add request validation

            Payment payment = new Payment(new BigDecimal(createPaymentRequest.Amount, MathContext.DECIMAL64),
                    createPaymentRequest.Token, createPaymentRequest.MerchantId, createPaymentRequest.Description);

            paymentService.createPayment(payment);

            return Response.ok().build();

        } catch (MerchantNotFound e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    new ErrorModel(String.format("merchant with id %s is unknown", createPaymentRequest.MerchantId)))
                    .build();

        } catch (TokenNotFound e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorModel(String.format("token %s is unknown", createPaymentRequest.Token))).build();

        } catch (TokenAlreadyUsed e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorModel(String.format("token %s is already used", createPaymentRequest.Token)))
                    .build();

        } catch (DtuPaySystemException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorModel("DTUPay system error"))
                    .build();

        } catch (QueueException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorModel("DTUPay system error (queue)")).build();

        } catch (BankServiceException_Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorModel("DTUPay system error (bank)")).build();
        }
    }
}