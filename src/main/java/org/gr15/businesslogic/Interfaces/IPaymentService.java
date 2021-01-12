/**
 * @author Wassim
 */

package org.gr15.businesslogic.Interfaces;

import org.gr15.businesslogic.exceptions.DtuPaySystemError;
import org.gr15.businesslogic.exceptions.MerchantNotFound;
import org.gr15.businesslogic.exceptions.TokenAlreadyUsed;
import org.gr15.businesslogic.exceptions.TokenNotFound;
import org.gr15.businesslogic.models.Payment;

public interface IPaymentService {
    void createPayment(Payment payment) throws MerchantNotFound, TokenNotFound, TokenAlreadyUsed, DtuPaySystemError;
}
