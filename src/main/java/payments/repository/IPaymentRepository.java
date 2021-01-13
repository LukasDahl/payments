/**
 * @author Wassim
 */

package payments.repository;

import payments.businesslogic.models.Account;
import payments.businesslogic.models.Transaction;
import payments.businesslogic.models.TokenInfo;

public interface IPaymentRepository {

    Account getMerchantAccount(String accountId);

    Account getCustomerAccount(String accountId);

    TokenInfo getTokenInfo(String token);

    void saveTransaction(Transaction transaction);

    void markTokenAsUsed(String token);
}
