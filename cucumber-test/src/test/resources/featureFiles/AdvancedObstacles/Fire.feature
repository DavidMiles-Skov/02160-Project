Feature: Fire

  Scenario: Fire damages robot
    Given a game with an empty board
    And a fire on the board at (5, 5)
    And a robot on the board at (5, 5)
    When the board elements activate
    Then the robot loses life points
    
  Scenario: Fire Spreading
    Given a game with an empty board
    And a fire on the board at (5, 5)
    When the board elements activate
    Then the fire spreads to a random adjacent cell