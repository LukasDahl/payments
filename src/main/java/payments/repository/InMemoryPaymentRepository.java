/**
 * @author Wassim
 */

package payments.repository;

import java.util.ArrayList;
import java.util.List;

import payments.businesslogic.models.Transaction;

public class InMemoryPaymentRepository implements IPaymentRepository {

    private static List<Transaction> transactions;

    public InMemoryPaymentRepository() {
        transactions = new ArrayList<Transaction>();
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public Transaction getTransaction(String id) {
        return transactions.stream().filter(transaction -> transaction.getId().equals(id)).findFirst().orElse(null);
    }
}
