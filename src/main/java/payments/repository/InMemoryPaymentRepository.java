/**
 * @author Wassim
 */

package payments.repository;

import java.util.ArrayList;
import java.util.List;

import payments.businesslogic.enums.AccountType;
import payments.businesslogic.models.Account;
import payments.businesslogic.models.TokenInfo;
import payments.businesslogic.models.Transaction;

public class InMemoryPaymentRepository implements IPaymentRepository {

    private static List<TokenInfo> tokens;
    private static List<Account> accounts;
    private static List<Transaction> transactions;

    public InMemoryPaymentRepository() {
        accounts = new ArrayList<Account>();
        tokens = new ArrayList<TokenInfo>();
        transactions = new ArrayList<Transaction>();

        // for testing
        addDummyData();
    }

    @Override
    public Account getMerchantAccount(String accountId) {
        Account account = accounts.stream()
                .filter(acc -> acc.AccountId.equals(accountId) && acc.Type == AccountType.MERCHANT).findFirst()
                .orElse(null);
        return account;
    }

    @Override
    public Account getCustomerAccount(String accountId) {
        Account account = accounts.stream()
                .filter(acc -> acc.AccountId.equals(accountId) && acc.Type == AccountType.CUSTOMER).findFirst()
                .orElse(null);
        return account;
    }

    @Override
    public TokenInfo getTokenInfo(String token) {
        TokenInfo tokenInfo = tokens.stream().filter(t -> t.Token.equals(token)).findFirst().orElse(null);
        return tokenInfo;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public void markTokenAsUsed(String token) {
        TokenInfo tokenInfo = tokens.stream().filter(t -> t.Token.equals(token)).findFirst().orElse(null);
        tokenInfo.setUsed();
    }

    private void addDummyData() {

        accounts.add(new Account(AccountType.MERCHANT, "mid1", "mid1ba"));
        accounts.add(new Account(AccountType.CUSTOMER, "cid1", "cid1ba"));

        tokens.add(new TokenInfo("token1", "cid1"));
        tokens.add(new TokenInfo("token2", "cid1"));
        tokens.add(new TokenInfo("token3", "cid1"));

        TokenInfo token4 = new TokenInfo("token4", "cid1");
        token4.setUsed();
        tokens.add(token4);

    }
}
