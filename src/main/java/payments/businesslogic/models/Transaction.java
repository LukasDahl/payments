/**
 * @author Wassim
 */

package payments.businesslogic.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private String id;
    private String token;
    private BigDecimal amount;
    private String merchantId;
    private String customerId;
    private String description;
    private LocalDateTime time;

    public Transaction(BigDecimal amount, String token, String merchantId, String customerId, String description) {
        this.id = UUID.randomUUID().toString();
        this.token = token;
        this.amount = amount;
        this.merchantId = merchantId;
        this.customerId = customerId;
        this.description = description;
        this.time = LocalDateTime.now();
    }

    public String getId() {
        return this.id;
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

    public String getCustomerId() {
        return this.customerId;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
