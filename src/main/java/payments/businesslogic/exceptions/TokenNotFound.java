/**
 * @author Wassim
 */

package payments.businesslogic.exceptions;

public class TokenNotFound extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TokenNotFound(String message) {
        super(message);
    }
}
