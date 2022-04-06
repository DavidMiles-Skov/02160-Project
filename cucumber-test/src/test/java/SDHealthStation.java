import static org.junit.Assert.assertEquals;

import board.Board;
import environment_elements.HealthStation;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import piece_basics.Robot;

public class SDHealthStation {
	private Context context;
	
	public SDHealthStation(Context context) {
		this.context = context;
	}
	
	@Given("a health station on the board")
	public void a_health_station_on_the_board() {
	    Board board = context.board;
	    HealthStation hs = new HealthStation();
	    board.initialPlacement(hs, 6, 5);
	    context.healthStation = hs;
	}
	@Given("the robot does not have full health")
	public void the_robot_does_not_have_full_health() {
	    context.robot.takeDamage();
	    context.robot.takeDamage();
	}
	@When("the robot moves into the health station during a register")
	public void the_robot_moves_into_the_health_station_during_a_register() {
		context.robot.shiftX(1);
	}
	@Then("the robot gains one HP at the end of the register")
	public void the_robot_gains_one_hp_at_the_end_of_the_register() {
	    Robot r = context.robot;
		int oldHealth = r.getHealth();
		context.healthStation.performRegisterAction();
		assertEquals(oldHealth + 1, r.getHealth());
	}
	
	@Given("the robot has full health")
	public void the_robot_has_full_health() {}
	@Then("the robot remains at full health at the end of the register")
	public void the_robot_remains_at_full_health_at_the_end_of_the_register() {
		Robot r = context.robot;
		int oldHealth = r.getHealth();
		context.healthStation.performRegisterAction();
		assertEquals(oldHealth, r.getHealth());
	}
}
