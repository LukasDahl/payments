/**
 * @author Wassim
 */

package org.gr15.businesslogic.Interfaces;

public interface IBankService {
    // TODO: check return type
    void transferMoney(String merchantBankAccountId, String customerBankAccountId, Double amount, String Description);
}
