/**
 * @author Wassim
 */

package payments.businesslogic.exceptions;

public class TokenAlreadyUsed extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TokenAlreadyUsed(String message) {
        super(message);
    }
}
