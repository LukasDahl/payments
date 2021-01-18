/**
 * @author Wassim
 */

package payments.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import payments.businesslogic.Interfaces.IPaymentService;

@ApplicationPath("/")
public class RestApplication extends Application {

    private IPaymentService paymentService;

    public RestApplication() {

        // adding it here to make sure we start listening as soon as the app is up and
        // running independent of any web request
        paymentService = new PaymentServiceFactory().getService();
    }
}
