/**
 * @author Wassim
 */

package payments.businesslogic.services;

import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import payments.businesslogic.Interfaces.IQueueService;
import payments.businesslogic.exceptions.QueueException;
import payments.businesslogic.models.Account;
import payments.businesslogic.models.TokenInfo;
import payments.businesslogic.models.Transaction;
import messaging.interfaces.IEventReceiver;
import messaging.interfaces.IEventSender;
import messaging.models.Event;

public class QueueService implements IEventReceiver, IQueueService {

    private static final String QUEUE_TYPE = "topic";
    private static final String EXCHANGE_NAME = "paymentsExchange";

    private static final String TOKEN_CMD_BASE = "token.cmds.";
    private static final String ACCOUNT_CMD_BASE = "account.cmds.";
    private static final String PAYMENT_EVENT_BASE = "payment.events.";

    private static final String VALIDATE_TOKEN_CMD = "validateToken";
    private static final String VALIDATE_ACCOUNT_CMD = "validateAccount";

    private static final String TOKEN_VALIDATED_EVENT = "tokenValidated";
    private static final String ACCOUNT_VALIDATED_EVENT = "accountValidated";
    private static final String TRANSACTION_CREATED_EVENT = "transactionCreated";

    private IEventSender eventSender;
    private CompletableFuture<Account> accountResult;
    private CompletableFuture<TokenInfo> tokenInfoResult;

    public QueueService(IEventSender eventSender) {
        this.eventSender = eventSender;
    }

    @Override
    public TokenInfo validateToken(String token) throws QueueException {

        Event event = new Event(VALIDATE_TOKEN_CMD, token);

        tokenInfoResult = new CompletableFuture<TokenInfo>();
        try {
            eventSender.sendEvent(event, EXCHANGE_NAME, QUEUE_TYPE, TOKEN_CMD_BASE + VALIDATE_TOKEN_CMD);
        } catch (Exception e) {
            throw new QueueException("Error while validating token");
        }

        return tokenInfoResult.join();
    }

    @Override
    public Account validateAccount(String accountId) throws QueueException {

        Event event = new Event(VALIDATE_ACCOUNT_CMD, accountId);

        accountResult = new CompletableFuture<Account>();
        try {
            eventSender.sendEvent(event, EXCHANGE_NAME, QUEUE_TYPE, ACCOUNT_CMD_BASE + VALIDATE_ACCOUNT_CMD);
        } catch (Exception e) {
            throw new QueueException("Error while validating account");
        }

        return accountResult.join();
    }

    @Override
    public void publishTransactionCreatedEvent(Transaction transaction) throws QueueException {

        Event event = new Event(TRANSACTION_CREATED_EVENT, transaction);

        try {
            eventSender.sendEvent(event, EXCHANGE_NAME, QUEUE_TYPE, PAYMENT_EVENT_BASE + TRANSACTION_CREATED_EVENT);
        } catch (Exception e) {
            throw new QueueException("Error while publishing transaction created event");
        }
    }

    @Override
    public void receiveEvent(Event event) throws QueueException {

        System.out.println("Handling event: " + event);

        if (event.getEventType().equals(TOKEN_VALIDATED_EVENT)) {

            var tokenInfo = new Gson().fromJson(new Gson().toJson(event.getEventInfo()), TokenInfo.class);

            tokenInfoResult.complete(tokenInfo);

        } else if (event.getEventType().equals(ACCOUNT_VALIDATED_EVENT)) {

            var account = new Gson().fromJson(new Gson().toJson(event.getEventInfo()), Account.class);

            accountResult.complete(account);

        } else {
            System.out.println("event ignored: " + event);
        }
    }

}
