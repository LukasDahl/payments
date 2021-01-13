/**
 * @author Wassim
 */

package payments.businesslogic.exceptions;

public class MerchantNotFound extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public MerchantNotFound(String message) {
        super(message);
    }
}
