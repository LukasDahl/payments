/**
 * @author Wassim
 */

package payments.businesslogic.models;

public class Payment {
    public String Token;
    public Double Amount;
    public String MerchantId;
    public String Description;

    public Payment(Double amount, String token, String merchantId, String description) {
        Token = token;
        Amount = amount;
        MerchantId = merchantId;
        Description = description;
    }
}
