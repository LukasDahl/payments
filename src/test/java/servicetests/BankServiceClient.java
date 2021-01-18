package servicetests;

import java.math.BigDecimal;

import bankservice.Account;
import bankservice.BankService;
import bankservice.BankServiceException_Exception;
import bankservice.BankServiceService;
import bankservice.User;

public class BankServiceClient {
    public BankService bankService = new BankServiceService().getBankServicePort();

    public BankServiceClient() {
        bankService = new BankServiceService().getBankServicePort();
    }

    public Account createBankAccount(String firstName, String lastName, String CPR, String balance)
            throws BankServiceException_Exception {

        var user = new User();
        user.setCprNumber(CPR);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        var bankAccountId = this.bankService.createAccountWithBalance(user, new BigDecimal(balance));
        return this.bankService.getAccount(bankAccountId);
    }

    public void deleteBankAccount(String bankAccountId) throws BankServiceException_Exception {
        this.bankService.retireAccount(bankAccountId);
    }

    public Account getBankAccount(String bankAccountId) throws BankServiceException_Exception {
        return this.bankService.getAccount(bankAccountId);
    }
}
