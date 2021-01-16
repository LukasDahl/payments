/**
 * @author Wassim
 */

package payments.businesslogic.models;

import java.math.BigDecimal;

public class Payment {
    private String token;
    private BigDecimal amount;
    private String merchantId;
    private String description;

    public Payment(BigDecimal amount, String token, String merchantId, String description) {
        this.token = token;
        this.amount = amount;
        this.merchantId = merchantId;
        this.description = description;
    }

    public String getToken() {
        return this.token;
    }

    public BigDecimal getAmount() {
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

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public void setDescriptionId(String description) {
        this.description = description;
    }
}
