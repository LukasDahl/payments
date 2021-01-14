/**
 * @author Wassim
 */

package payments.messaging.interfaces;

import payments.messaging.models.Event;

public interface IEventReceiver {
    void receiveEvent(Event event) throws Exception;
}