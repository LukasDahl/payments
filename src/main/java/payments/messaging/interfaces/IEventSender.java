/**
 * @author Wassim
 */

package payments.messaging.interfaces;

import payments.messaging.models.Event;

public interface IEventSender {
    void sendEvent(Event event, String exchangeName, String queueType, String topic) throws Exception;
}
