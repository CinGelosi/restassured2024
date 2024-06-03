Feature: (e2e) Validate users

  @users
  Scenario: Validate that the response of the user list is 200
    Given there are users loaded in the system
    When a user retrieves the user list
    Then the status code is 200

  @users
  Scenario Outline: Validate that the user "<user>" exist
    Given there are users loaded in the system
    When a user retrieves the user list
    Then the status code is 200
    And response includes "<user>"
    Examples:
      |user   |
      |Janet |
      |George  |

  @createUser
  Scenario: Validate the response of the user creation is 201
    Given a user name "morpheus" and a job "leader"
    When the user is created
    Then the status code is 201

  @update
  Scenario: Validate that the job can be updated
    Given The user "pp" with a new position "oo"
    When the user update the position
    Then the status code is 200
    And the body`s response is updated with "pp" and "oo"

  @delete
  Scenario: Validate the user can be deleted
    Given an user
    When the user is deleted
    Then the status code is 204