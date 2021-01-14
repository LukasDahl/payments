/**
 * @author Wassim
 */

package messaging.rabbitmq;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import messaging.interfaces.IEventSender;
import messaging.models.Event;

public class RabbitMqSender implements IEventSender {

    // private static final String EXCHANGE_NAME = "eventsExchange";
    // private static final String QUEUE_TYPE = "topic";
    // private static final String TOPIC = "events";

    @Override
    public void sendEvent(Event event, String exchangeName, String queueType, String topic) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitMq");

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(exchangeName, queueType);

            String message = new Gson().toJson(event);

            System.out.println("[x] sending " + message);

            channel.basicPublish(exchangeName, topic, null, message.getBytes("UTF-8"));

        }
    }
}