/**
 * @author Wassim
 */

package payments.businesslogic.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    public String Id;
    public String Token;
    public BigDecimal Amount;
    public String MerchantId;
    public String CustomerId;
    public String Description;
    public LocalDateTime Time;

    public Transaction(BigDecimal amount, String token, String merchantId, String customerId, String description) {
        Id = UUID.randomUUID().toString();
        Token = token;
        Amount = amount;
        MerchantId = merchantId;
        CustomerId = customerId;
        Description = description;
        Time = LocalDateTime.now();
    }
}
