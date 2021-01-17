# author: Wassim

Feature: Payment

    Scenario: Successful Payment
        Given a token "validToken"
        And a merchant with id "validMerchantId1"
        And a description "service test 1"
        When the merchant initiates a payment for 10 kr by the customer
        Then the payment is successful

    Scenario: Token is unknown
        Given a token "unknownToken"
        And a merchant with id "validMerchantId1"
        And a description "service test 2"
        When the merchant initiates a payment for 10 kr by the customer
        Then the payment is not successful
        And the error message should say "token unknownToken is unknown"

    Scenario: Token is already used
        Given a token "usedToken"
        And a merchant with id "validMerchantId1"
        And a description "service test 3"
        When the merchant initiates a payment for 10 kr by the customer
        Then the payment is not successful
        And the error message should say "token usedToken is already used"

    Scenario: Token is required
        Given a token ""
        And a merchant with id "validMerchantId1"
        And a description "service test 4"
        When the merchant initiates a payment for 10 kr by the customer
        Then the payment is not successful
        And the error message should say "token is required"

    Scenario: MerchantId is required
        Given a token "validToken"
        And a merchant with id ""
        And a description "service test 5"
        When the merchant initiates a payment for 10 kr by the customer
        Then the payment is not successful
        And the error message should say "merchantId is required"
