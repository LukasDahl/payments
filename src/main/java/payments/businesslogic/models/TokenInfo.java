/**
 * @author Wassim
 */

package payments.businesslogic.models;

public class TokenInfo {
    public String Token;
    public boolean IsUsed;
    public String CustomerId;

    public TokenInfo(String token, String customerId) {
        Token = token;
        IsUsed = false;
        CustomerId = customerId;
    }

    public void setUsed() {
        this.IsUsed = true;
    }
}
