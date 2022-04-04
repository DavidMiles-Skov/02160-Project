import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import environment_elements.Fire;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SDFire {

	private Context context;
	
	public SDFire(Context context) {
		this.context = context;		
	}
	
	@Given("a fire")
	public void a_fire() {
		Fire f = new Fire();
		context.fire = f;
		context.board.initialPlacement(f, 6, 5);
	    
	}
	@When("the robot steps into the fire")
	public void the_robot_steps_into_the_fire() {
		context.robot.shiftX(1);
		context.fire.performRegisterAction();
	}

	@Then("the fire spreads to a random adjacent cell")
	public void the_fire_spreads_to_a_random_adjacent_cell() {
		assertEquals(context.robot.getHealth(), 2);
		assertTrue(context.board.getEElementAt(context.fire.getP()) instanceof Fire);
	}
	
}
