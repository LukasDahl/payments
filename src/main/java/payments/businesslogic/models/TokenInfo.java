/**
 * @author Wassim
 */

package payments.businesslogic.models;

public class TokenInfo {
    public String Token;
    public Boolean IsUsed;
    public String CustomerId;

    public TokenInfo(String token, Boolean isUsed, String customerId) {
        Token = token;
        IsUsed = isUsed;
        CustomerId = customerId;
    }
}
