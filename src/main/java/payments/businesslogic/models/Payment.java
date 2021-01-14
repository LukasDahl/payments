/**
 * @author Wassim
 */

package payments.businesslogic.models;

import java.math.BigDecimal;

public class Payment {
    public String Token;
    public BigDecimal Amount;
    public String MerchantId;
    public String Description;

    public Payment(BigDecimal amount, String token, String merchantId, String description) {
        Token = token;
        Amount = amount;
        MerchantId = merchantId;
        Description = description;
    }
}
