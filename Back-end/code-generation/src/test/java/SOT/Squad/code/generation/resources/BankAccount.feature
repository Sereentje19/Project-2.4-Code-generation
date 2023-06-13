Feature: Bank Account Rest Controller

  Scenario: Add Bank Account
    Given a bank account with the following details:
      | accountNumber | accountHolderName | balance |
      | 123456789     | John Doe          | 1000.0  |
    When the bank account is added
    Then the response should be a bank account object with the same details

  Scenario: Get Bank Account by ID
    Given a bank account with the following details:
      | id  | accountNumber | accountHolderName | balance |
      | 100 | 123456789     | John Doe          | 1000.0  |
    When I request the bank account with ID 100
    Then the response should be a bank account object with the same details

  Scenario: Get All Bank Accounts
    Given multiple bank accounts in the system
    When I request all bank accounts
    Then the response should be a list of bank account objects

  Scenario: Delete Bank Account
    Given a bank account with the following details:
      | id  | accountNumber | accountHolderName | balance |
      | 100 | 123456789     | John Doe          | 1000.0  |
    When I delete the bank account with ID 100
    Then the bank account should be deleted successfully

  Scenario: Update Bank Account
    Given a bank account with the following details:
      | id  | accountNumber | accountHolderName | balance |
      | 100 | 123456789     | John Doe          | 1000.0  |
    When I update the bank account with ID 100
    Then the response should be the updated bank account object

  Scenario: Get All Name and IBAN
    Given multiple bank accounts in the system
    When I request all name and IBAN
    Then the response should be a list of IbanAndNameDTO objects

