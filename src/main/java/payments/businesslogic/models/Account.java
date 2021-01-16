/**
 * @author Wassim
 */

package payments.businesslogic.models;

public class Account {
    private String id;
    private String bankAccountId;

    public Account(String id, String bankAccountId) {
        this.id = id;
        this.bankAccountId = bankAccountId;
    }

    public String getId() {
        return this.id;
    }

    public String getBankAccountId() {
        return this.bankAccountId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBankAccountId(String bankAccountId) {
        this.bankAccountId = bankAccountId;
    }
}
