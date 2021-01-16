/**
 * @author Wassim
 */

package payments.rest.models;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.validation.ValidationException;

public class CreatePaymentRequest {
    private String token;
    private Double amount;
    private String merchantId;
    private String description;

    public CreatePaymentRequest() {
    }

    @JsonbCreator
    public CreatePaymentRequest(@JsonbProperty("amount") Double amount, @JsonbProperty("token") String token,
            @JsonbProperty("merchantId") String merchantId, @JsonbProperty("description") String description) {
        this.amount = amount;
        this.token = token;
        this.merchantId = merchantId;
        this.description = description;
    }

    public void validate() throws ValidationException {
        if (this.token == null || this.token.trim().isEmpty()) {
            throw new ValidationException("token is required");
        }
        if (this.merchantId == null || this.merchantId.trim().isEmpty()) {
            throw new ValidationException("merchantId is required");
        }
        if (this.amount == null) {
            throw new ValidationException("amount is required");
        }
    }

    public String getToken() {
        return this.token;
    }

    public Double getAmount() {
        return this.amount;
    }

    public String getMerchantId() {
        return this.merchantId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
