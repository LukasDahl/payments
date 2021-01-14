/**
 * @author Wassim
 */

package payments.businesslogic.models;

public class TokenInfo {
    public String Token;
    public String CustomerId;

    public TokenInfo(String token, String customerId) {
        Token = token;
        CustomerId = customerId;
    }
}
