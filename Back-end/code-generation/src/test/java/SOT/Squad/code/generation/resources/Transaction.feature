Feature: Transaction API

  Scenario: Add a transaction as EMPLOYEE
    Given I am logged in as username "serena" with password "kenter"
    And The endpoint for transactions is available for method "POST"
    When the transaction is added
    Then the transaction is successfully created

  Scenario: Add a transaction as CUSTOMER
    Given I am logged in as "CUSTOMER" with password "CUSTOMER"
    And The endpoint for "transactions" is available for method "POST"
    When the transaction is added
    Then the transaction is successfully created

  Scenario: Retrieve a transaction by ID
    Given a valid transaction ID
    When the transaction is retrieved
    Then the transaction details are returned

  Scenario: Update a transaction
    Given a valid transaction ID
    And updated transaction details
    When the transaction is updated
    Then the transaction is successfully modified

  Scenario: Delete a transaction
    Given a valid transaction ID
    When the transaction is deleted
    Then the transaction is successfully removed

  Scenario: Getting all transactions
    Given I am logged in as "EMPLOYEE" with password "EMPLOYEE"
    And The endpoint for transactions is available for method get
    When I retrieve all transactions
    Then I should receive all transactions

  Scenario: Getting all transactions for a specific account
    Given I am logged in as "EMPLOYEE" with password "EMPLOYEE"
    And The endpoint for "transactions/id" is available for method "GET"
    When I retrieve all transactions
    Then I should receive all transactions

  Scenario: Getting all transactions from own account
    Given I am logged in as "CUSTOMER" with password "CUSTOMER"
    And The endpoint for transactions is available for method GET
    When I retrieve all transactions
    Then I should receive all transactions
