/**
 * @author Wassim
 */

package org.gr15.businesslogic.Interfaces;

import org.gr15.businesslogic.models.TokenInfo;
import org.gr15.businesslogic.models.Transaction;

public interface IQueueService {
    void publishTransactionCreatedEvent(Transaction transaction);

    void publishTokenUsedEvent(TokenInfo tokenInfo);
}
