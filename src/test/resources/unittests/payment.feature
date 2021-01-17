# author: Wassim

Feature: Payment Service

    Scenario: Token is unknown
        Given An unknown token "unknown"
        When Creating payment with token "unknown"
        Then An exception of type TokenNotFound is thrown

    Scenario: Token is already used
        Given An already used token "alreadyUsed"
        When Creating payment with token "alreadyUsed"
        Then An exception of type TokenAlreadyUsed is thrown

    Scenario: Merchant is unkown
        Given A valid token "validToken" for customer "customerId1"
        And An unknown merchant "unknownMerchantId"
        When Creating payment with merchant "unknownMerchantId"
        Then An exception of type MerchantNotFound is thrown

    Scenario: Customer is not found
        Given A valid token "validToken" for customer "customerId1"
        And A valid merchant "validMerchantId" with bank account "validMerchantBankAccountId"
        And Customer account is not found for customer "customerId1"
        When Creating payment with token "validToken" and merchant "validMerchantId"
        Then An exception of type DtuPaySystemException is thrown

    Scenario: All payment data are valid
        Given A valid token "validToken" for customer "validCustomerId"
        And A valid merchant "validMerchantId" with bank account "validMerchantBankAccountId"
        And A valid customer "validCustomerId" with bank account "validCustomerBankAccountId"
        When Creating payment with token "validToken" and merchant "validMerchantId" with amount 100 and description "test transfer"
        Then A bank transfer is triggered from account "validCustomerBankAccountId" to account "validMerchantBankAccountId" for an amount of 100 and description "test transfer"
        Then The transacation is saved and published
