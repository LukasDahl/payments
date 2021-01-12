/**
 * @author Wassim
 */

package org.gr15.repository;

import org.gr15.businesslogic.models.Account;
import org.gr15.businesslogic.models.Transaction;
import org.gr15.businesslogic.models.TokenInfo;

public interface IPaymentRepository {

    Account getMerchantAccount(String accountId);

    Account getCustomerAccount(String accountId);

    TokenInfo getTokenInfo(String token);

    void saveTransaction(Transaction transaction);

    void markTokenAsUsed(String token);
}
