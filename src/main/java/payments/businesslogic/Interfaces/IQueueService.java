/**
 * @author Wassim
 */

package payments.businesslogic.Interfaces;

import payments.businesslogic.exceptions.QueueException;
import payments.businesslogic.models.Account;
import payments.businesslogic.models.TokenInfo;
import payments.businesslogic.models.Transaction;

public interface IQueueService {
    void publishTransactionCreatedEvent(Transaction transaction) throws QueueException;

    TokenInfo validateToken(String token) throws QueueException;

    Account validateAccount(String accountId) throws QueueException;
}
