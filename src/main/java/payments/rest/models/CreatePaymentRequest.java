/**
 * @author Wassim
 */

package payments.rest.models;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

public class CreatePaymentRequest {
    public String Token;
    public Double Amount;
    public String MerchantId;
    public String Description;

    public CreatePaymentRequest() {
    }

    @JsonbCreator
    public CreatePaymentRequest(@JsonbProperty("amount") Double amount, @JsonbProperty("token") String token,
            @JsonbProperty("merchantId") String merchantId, @JsonbProperty("description") String description) {
        this.Amount = amount;
        this.Token = token;
        this.MerchantId = merchantId;
        this.Description = description;
    }
}
