/**
 * @author Wassim
 */

package payments.businesslogic.models;

public class Account {
    public String AccountId;
    public String BankAccountId;

    public Account(String accountId, String bankAccountId) {
        AccountId = accountId;
        BankAccountId = bankAccountId;
    }
}
