Feature: Bank Account Rest Controller


  Scenario: Get All Bank Accounts
    Given I am logged in as user "thijs" with password "moerland"
    And The endpoint for "bankaccounts" is available for the method "GET"
    When I retrieve all bank accounts
    Then the response should be a list of bank account objects

  Scenario: Add Bank Account
    Given I am logged in as user "thijs" with password "moerland"
    And The endpoint for "bankaccounts" is available for the method "POST"
    When a new bank account is added
    Then the response should be a bank account object

  Scenario: Get Bank Account by ID
    Given I am logged in as user "thijs" with password "moerland"
    And The endpoint for "bankaccounts" is available for the method "GET"
    When I request the bank account with ID 2
    Then the response should be a bank account object with the ID 2

  Scenario: Update Bank Account
    Given I am logged in as user "serena" with password "kenter"
    And The endpoint for "bankaccounts" is available for the method "PUT"
    When The bank account with ID 2 is put to disabled
    Then the bank account should be put to disabled successfully

  Scenario: Update Bank Account by ID
    Given I am logged in as user "serena" with password "kenter"
    And The endpoint for "bankaccounts" is available for the method "PUT"
    When I update the bank account with ID 2
    Then the response should be the updated bank account object with ID 2

  Scenario: Get All Name and IBAN
    Given I am logged in as user "thijs" with password "moerland"
    And The endpoint for "bankaccounts" is available for the method "GET"
    When I request the ID, iban, name and accountType of all bank accounts
    Then the response should be a list of bank account objects with only the ID, iban, name and accountType

  Scenario: Get Bank Account by IBAN
    Given I am logged in as user "thijs" with password "moerland"
    And The endpoint for "bankaccounts" is available for the method "GET"
    When I request the bank account with IBAN "NL12INHO0123456789"
    Then the response should be a bank account object with the specified IBAN

  Scenario: Get All Bank Accounts By User ID
    Given I am logged in as user "thijs" with password "moerland"
    And The endpoint for "bankaccounts" is available for the method "GET"
    When I request all bank accounts for the user with ID 3
    Then the response should be a list of bank account objects

  Scenario: Get Account Types
    Given I am logged in as user "thijs" with password "moerland"
    And The endpoint for "bankaccounts" is available for the method "GET"
    When I request the account types for the user with ID 3
    Then the response should be a list of account types