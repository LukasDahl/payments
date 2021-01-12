/**
 * @author Wassim
 */

package org.gr15.businesslogic.services;

import org.gr15.businesslogic.Interfaces.IQueueService;
import org.gr15.businesslogic.models.TokenInfo;
import org.gr15.businesslogic.models.Transaction;

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
