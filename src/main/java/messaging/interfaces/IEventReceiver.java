/**
 * @author Wassim
 */

package messaging.interfaces;

import messaging.models.Event;

public interface IEventReceiver {
    void receiveEvent(Event event) throws Exception;
}