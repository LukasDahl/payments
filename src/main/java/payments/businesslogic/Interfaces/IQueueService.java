/**
 * @author Wassim
 */

package payments.businesslogic.Interfaces;

import payments.businesslogic.models.TokenInfo;
import payments.businesslogic.models.Transaction;

public interface IQueueService {
    void publishTransactionCreatedEvent(Transaction transaction);

    void publishTokenUsedEvent(TokenInfo tokenInfo);
}
