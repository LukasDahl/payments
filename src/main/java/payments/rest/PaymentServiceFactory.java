/**
 * @author Wassim
 */

package payments.rest;

import bankservice.BankService;
import bankservice.BankServiceService;
import messaging.rabbitmq.RabbitMqListener;
import messaging.rabbitmq.RabbitMqSender;
import payments.businesslogic.Interfaces.IPaymentService;
import payments.businesslogic.services.PaymentService;
import payments.businesslogic.services.QueueService;
import payments.repository.IPaymentRepository;
import payments.repository.InMemoryPaymentRepository;

public class PaymentServiceFactory {
    static IPaymentService paymentService = null;

    private static final String QUEUE_TYPE = "topic";
    private static final String EXCHANGE_NAME = "paymentsExchange";

    private static final String TOKEN_EVENTS = "token.events.*";
    private static final String ACCOUNT_EVENTS = "account.events.*";

    public IPaymentService getService() {

        if (paymentService != null) {
            return paymentService;
        }

        BankService bankService = new BankServiceService().getBankServicePort();
        QueueService queueService = new QueueService(new RabbitMqSender("rabbitMq"));
        IPaymentRepository paymentRepository = new InMemoryPaymentRepository();

        paymentService = new PaymentService(paymentRepository, bankService, queueService);

        RabbitMqListener r = new RabbitMqListener(queueService, "rabbitMq");
        try {
            r.listen(EXCHANGE_NAME, QUEUE_TYPE, TOKEN_EVENTS);
            r.listen(EXCHANGE_NAME, QUEUE_TYPE, ACCOUNT_EVENTS);
        } catch (Exception e) {
            throw new Error(e);
        }
        return paymentService;
    }
}
