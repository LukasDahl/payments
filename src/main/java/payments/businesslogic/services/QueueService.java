/**
 * @author Wassim
 */

package payments.businesslogic.services;

import payments.businesslogic.Interfaces.IQueueService;
import payments.businesslogic.models.TokenInfo;
import payments.businesslogic.models.Transaction;

public class QueueService implements IQueueService {

    @Override
    public void publishTransactionCreatedEvent(Transaction transaction) {
        // TODO Auto-generated method stub

    }

    @Override
    public void publishTokenUsedEvent(TokenInfo tokenInfo) {
        // TODO Auto-generated method stub

    }

}
