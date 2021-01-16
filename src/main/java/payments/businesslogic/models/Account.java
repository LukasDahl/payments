/**
 * @author Wassim
 */

package payments.businesslogic.models;

public class Account {
    public String id;
    public String bankAccountId;

    public Account(String id, String bankAccountId) {
        this.id = id;
        this.bankAccountId = bankAccountId;
    }
}
