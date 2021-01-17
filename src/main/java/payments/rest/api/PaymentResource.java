/**
 * @author Wassim
 */

package payments.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import payments.businesslogic.Interfaces.IPaymentService;
import payments.rest.PaymentServiceFactory;

@Path("payments/{id}")
@Produces(MediaType.APPLICATION_JSON)
public class PaymentResource {

    private IPaymentService paymentService;

    public PaymentResource() {
        paymentService = new PaymentServiceFactory().getService();
    }

    @GET
    public Response getPayment(@PathParam("id") String id) {
        var transaction = this.paymentService.getPayment(id);
        if (transaction == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(transaction).build();
    }
}
