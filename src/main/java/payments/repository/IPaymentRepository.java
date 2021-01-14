/**
 * @author Wassim
 */

package payments.repository;

import payments.businesslogic.models.Transaction;

public interface IPaymentRepository {

    void saveTransaction(Transaction transaction);

}
