/**
 * @author Wassim
 */

package servicetests;

import com.google.gson.Gson;

import messaging.interfaces.IEventReceiver;
import messaging.interfaces.IEventSender;
import messaging.models.Event;
import messaging.rabbitmq.RabbitMqListener;
import messaging.rabbitmq.RabbitMqSender;
import payments.businesslogic.models.Account;
import payments.businesslogic.models.TokenInfo;
import payments.businesslogic.models.Transaction;

public class TestQueueClient implements IEventReceiver {

    private static final String QUEUE_TYPE = "topic";
    private static final String EXCHANGE_NAME = "paymentsExchange";

    private static final String VALID_TOKEN = "validToken";
    private static final String USED_TOKEN = "usedToken";

    private static final String CUSTOMER_ID = "cid1";
    private String CUSTOMER_BANK_ACCOUNT_ID;

    private static final String MERCHANT_ID = "validMerchantId1";
    private String MERCHANT_BANK_ACCOUNT_ID;

    IEventSender sender;

    public TestQueueClient() {
        this.sender = new RabbitMqSender("localhost");

        RabbitMqListener r = new RabbitMqListener(this, "localhost");
        try {
            r.listen(EXCHANGE_NAME, QUEUE_TYPE, "account.cmds.*");
            r.listen(EXCHANGE_NAME, QUEUE_TYPE, "token.cmds.*");
            r.listen(EXCHANGE_NAME, QUEUE_TYPE, "payment.events.*");
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    public void setMerchantBankAccountId(String merchantBankAccountId) {
        this.MERCHANT_BANK_ACCOUNT_ID = merchantBankAccountId;
    }

    public void setCustomerBankAccountId(String customerBankAccountId) {
        this.CUSTOMER_BANK_ACCOUNT_ID = customerBankAccountId;
    }

    @Override
    public void receiveEvent(Event event) throws Exception {

        System.out.println("handling event: " + event);

        if (event.getEventType().equals("validateToken")) {

            var token = new Gson().fromJson(new Gson().toJson(event.getEventInfo()), String.class);

            // consider it unknown token by default
            Event e = new Event("tokenValidated", new TokenInfo(token, false, ""));

            if (token.equals(VALID_TOKEN)) {
                e = new Event("tokenValidated", new TokenInfo(token, false, "cid1"));
            } else if (token.equals(USED_TOKEN)) {
                e = new Event("tokenValidated", new TokenInfo(token, true, "cid1"));
            }

            sender.sendEvent(e, EXCHANGE_NAME, QUEUE_TYPE, "token.events.tokenValidated");

        } else if (event.getEventType().equals("validateAccount")) {

            var accountId = new Gson().fromJson(new Gson().toJson(event.getEventInfo()), String.class);

            // consider it not found by default
            Event e = new Event("accountValidated", new Account(accountId, ""));

            if (accountId.equals(CUSTOMER_ID)) {
                e = new Event("accountValidated", new Account(accountId, this.CUSTOMER_BANK_ACCOUNT_ID));
            } else if (accountId.equals(MERCHANT_ID)) {
                e = new Event("accountValidated", new Account(accountId, this.MERCHANT_BANK_ACCOUNT_ID));
            }

            sender.sendEvent(e, EXCHANGE_NAME, QUEUE_TYPE, "account.events.accountValidated");

        } else if (event.getEventType().equals("transactionCreated")) {

            var trans = new Gson().fromJson(new Gson().toJson(event.getEventInfo()), Transaction.class);

            System.out.println(trans.getCustomerId() + ", " + trans.getMerchantId() + ", " + trans.getToken() + ", "
                    + trans.getAmount().toPlainString() + ", " + trans.getId());

        } else {
            System.out.println("event ignored: " + event);
        }
    }

}
