/**
 * @author Wassim
 */

package org.gr15.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.gr15.businesslogic.Interfaces.IPaymentService;
import org.gr15.businesslogic.exceptions.DtuPaySystemError;
import org.gr15.businesslogic.exceptions.MerchantNotFound;
import org.gr15.businesslogic.exceptions.TokenAlreadyUsed;
import org.gr15.businesslogic.exceptions.TokenNotFound;
import org.gr15.businesslogic.models.Payment;
import org.gr15.businesslogic.services.BankService;
import org.gr15.businesslogic.services.PaymentService;
import org.gr15.businesslogic.services.QueueService;
import org.gr15.repository.InMemoryPaymentRepository;
import org.gr15.rest.models.CreatePaymentRequest;
import org.gr15.rest.models.ErrorModel;

@Path("/payments")
public class PaymentsResource {

    private static IPaymentService _paymentService;

    public PaymentsResource() {
        if (_paymentService == null) {
            _paymentService = new PaymentService(new InMemoryPaymentRepository(), new BankService(),
                    new QueueService());
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPayment(CreatePaymentRequest createPaymentRequest)
            throws MerchantNotFound, TokenNotFound, TokenAlreadyUsed, DtuPaySystemError {

        try {
            // TODO: add request validation

            Payment payment = new Payment(createPaymentRequest.Amount, createPaymentRequest.Token,
                    createPaymentRequest.MerchantId, createPaymentRequest.Description);

            _paymentService.createPayment(payment);

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

        } catch (DtuPaySystemError e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorModel("DTUPay system error"))
                    .build();
        }
    }
}