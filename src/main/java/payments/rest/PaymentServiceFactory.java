/**
 * @author Wassim
 */

package payments.rest;

import payments.businesslogic.Interfaces.IBankService;
import payments.businesslogic.Interfaces.IPaymentService;
import payments.businesslogic.services.BankService;
import payments.businesslogic.services.PaymentService;
import payments.businesslogic.services.QueueService;
import payments.messaging.rabbitmq.RabbitMqListener;
import payments.messaging.rabbitmq.RabbitMqSender;
import payments.repository.IPaymentRepository;
import payments.repository.InMemoryPaymentRepository;

public class PaymentServiceFactory {
    static IPaymentService paymentService = null;

    private static final String QUEUE_TYPE = "topic";
    private static final String EXCHANGE_NAME = "paymentsExchange";

    // Testing
    private static final String ALL_EVENT_BASE = "#";
    // private static final String TOKEN_EVENT_BASE = "token.events.*";
    // private static final String ACCOUNT_EVENT_BASE = "account.events.*";

    public IPaymentService getService() {

        if (paymentService != null) {
            return paymentService;
        }

        IBankService bankService = new BankService();
        QueueService queueService = new QueueService(new RabbitMqSender());
        IPaymentRepository paymentRepository = new InMemoryPaymentRepository();

        paymentService = new PaymentService(paymentRepository, bankService, queueService);

        RabbitMqListener r = new RabbitMqListener(queueService);
        try {
            r.listen(EXCHANGE_NAME, QUEUE_TYPE, ALL_EVENT_BASE);
        } catch (Exception e) {
            throw new Error(e);
        }
        return paymentService;
    }
}
