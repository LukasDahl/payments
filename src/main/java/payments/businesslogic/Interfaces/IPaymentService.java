/**
 * @author Wassim
 */

package payments.businesslogic.Interfaces;

import payments.businesslogic.exceptions.DtuPaySystemException;
import payments.businesslogic.exceptions.MerchantNotFound;
import payments.businesslogic.exceptions.QueueException;
import payments.businesslogic.exceptions.TokenAlreadyUsed;
import payments.businesslogic.exceptions.TokenNotFound;
import payments.businesslogic.models.Payment;

public interface IPaymentService {
    void createPayment(Payment payment)
            throws MerchantNotFound, TokenNotFound, TokenAlreadyUsed, DtuPaySystemException, QueueException;
}
