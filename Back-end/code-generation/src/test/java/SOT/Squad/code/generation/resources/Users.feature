Feature: Crud Users
  I want to create, read, update and delete users from the database

  Scenario: Retrieve all users
    Given The user is logged in with username "thijs" and the password "moerland"
    Given The endpoint for "users" is available for method "GET"
    When I retrieve all users
    Then I should receive all users

  Scenario: Retrieve a single user
    Given The user is logged in with username "thijs" and the password "moerland"
    Given The endpoint for "users" is available for method "GET"
    When I request to get a single user with an id of "1"
    Then I should receive a single user with an id of "1"

  Scenario: Retrieve a single user that does not exist
    Given The user is logged in with username "thijs" and the password "moerland"
    Given The endpoint for "users" is available for method "GET"
    When I request to get a single user that does not exist
    Then I should receive a error

  Scenario: Creating a new user
    Given The user is logged in with username "thijs" and the password "moerland"
    Given The endpoint for "users" is available for method "GET"
    When I request to create a new user with valid informations
    Then I should receive a new user


    #    done

  Scenario: Creating a new user that already exists
    Given The user is logged in with username "thijs" and the password "moerland"
    Given The endpoint for "users" is available for method "GET"
    When I request to create a new user that already exists
    Then I should receive a error

  Scenario: Updating a user with a valid token
    Given the system has a database with users
    When I request to update a user with a valid token
#  httpHeaders.add("Content-Type", "application/json ");
#
#  User newUser = new User(1, "thijs", "moerland", "Thijs", "Moerland", 064567, "Moerland8", "123street", 53, "2131GB", "hoofddorp",null,true, List.of(Role.CUSTOMER), "5781",2000,300);
#
#  ResponseEntity<User> responseEntity = restTemplate.exchange("http://localhost:8080/users/" + arg0,
#  HttpMethod.PUT,
#  new HttpEntity<>(newUser, httpHeaders),
#  User.class);
#
#  User responseUser = responseEntity.getBody();
#  Assert.assertEquals(responseUser.getId(), parseInt(arg0));
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
    Given The user is logged in with username "thijs" and the password "moerland"
    Given The endpoint for "users" is available for method "GET"
    When I request to delete a user with an id of "1"
    Then I should receive a deleted user

  Scenario: Deleting a user with invalid token
    Given the system has a database with users
    When I request to delete a user with invalid token
    Then I should receive a error

  Scenario: Deleting a user without a token
    Given the system has a database with users
    When I request to delete a user without a token
    Then I should receive a error
