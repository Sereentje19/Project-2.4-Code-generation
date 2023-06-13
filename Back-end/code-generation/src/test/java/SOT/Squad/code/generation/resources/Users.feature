Feature: Crud Users
  I want to create, read, update and delete users from the database

  Scenario: Retrieve all users
    Given The user is logged in with username "thijs" and the password "moerland"
    Given The endpoint for "users" is available with method "GET"
    When I retrieve all users
    Then I should receive all users

  Scenario: Retrieve a single user
    Given the system has a database with users
    When I request to get a single user
    Then I should receive a single user

  Scenario: Retrieve a single user that does not exist
    Given the system has a database with users
    When I request to get a single user that does not exist
    Then I should receive a error

  Scenario: Creating a new user with a valid token
    Given the system has a database with users
    When I request to create a new user with a valid token
    Then I should receive a new user

  Scenario: Creating a new user with invalid token
    Given the system has a database with users
    When I request to create a new user with invalid token
    Then I should receive a error

  Scenario: Creating a new user without a token
    Given the system has a database with users
    When I request to create a new user without a token
    Then I should receive a error

  Scenario: Updating a user with a valid token
    Given the system has a database with users
    When I request to update a user with a valid token
    Then I should receive a updated user

  Scenario: Updating a user with invalid token
    Given the system has a database with users
    When I request to update a user with invalid token
    Then I should receive a error

  Scenario: Updating a user without a token
    Given the system has a database with users
    When I request to update a user without a token
    Then I should receive a error

Scenario: Deleting a user with a valid token
    Given the system has a database with users
    When I request to delete a user with a valid token
    Then I should receive a deleted user

  Scenario: Deleting a user with invalid token
    Given the system has a database with users
    When I request to delete a user with invalid token
    Then I should receive a error

  Scenario: Deleting a user without a token
    Given the system has a database with users
    When I request to delete a user without a token
    Then I should receive a error

  Scenario: Deleting a user with a valid token
    Given the system has a database with users
    When I request to delete a user with a valid token
    Then The user should be disabled

