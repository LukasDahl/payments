/**
 * @author Wassim
 */

package payments.businesslogic.models;

import payments.businesslogic.enums.AccountType;

public class Account {
    public AccountType Type;
    public String AccountId;
    public String BankAccountId;

    public Account(AccountType type, String accountId, String bankAccountId) {
        Type = type;
        AccountId = accountId;
        BankAccountId = bankAccountId;
    }
}