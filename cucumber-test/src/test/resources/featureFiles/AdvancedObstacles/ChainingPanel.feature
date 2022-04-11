Feature: Chaining Panel
  

  @tag1
  Scenario: Robot becomes chainable with no other chainable robots
    Given a game with an empty board
    And a chaining panel on the board at (5, 5)
    And a robot on the board at (5, 5)
    When the board elements activate
    Then the robot becomes chainable
    And the chaining panel becomes inactive

  Scenario: Chain connection
    Given a game with an empty board
    And a chaining panel on the board at (5, 5)
    And a second chaining panel on the board at (6, 6)
    And a robot on the board at (5, 5)
    And a chainable robot
    When the board elements activate
    Then the robots get chained together
    And the first chaining panel become active again

  Scenario: De-chaining the robots
    Given a game with an empty board
    And two robots chained together
    When one robot reboots
    Then the robots get unchained
    
  Scenario: Robots pulling on eachother
    Given a game with an empty board
    And two robots chained together
    When one robot moves
    Then the second robot is at (5, 5)