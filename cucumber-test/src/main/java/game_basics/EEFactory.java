package game_basics;
import environment_elements.ChainingPanel;
import environment_elements.Checkpoint;
import environment_elements.ConveyorBelt;
import environment_elements.FinalCheckpoint;
import environment_elements.Gear;
import environment_elements.HealthStation;
import environment_elements.Laser;
import environment_elements.OilSpill;
import environment_elements.Pit;
import environment_elements.RespawnPoint;
import environment_elements.ReversalPanel;
import environment_elements.Teleporter;
import environment_elements.Wall;
import piece_basics.EnvironmentElement;
import piece_basics.Orientation;

/*
 * Factory Method Pattern 
 * Client: BoardFactory
 * Product: EnvironmentElement
 * 
 * maps Characters -> EnvironmentElements for generating a board class from a text file.
 *  
 */

public class EEFactory {
	
	/*
	 * Teleporters have to be instantiated
	 * -  we restricted the number of possible teleporters to two 
	 * - (one receiving and one sending)
	 * 
	 */
	
	private Teleporter t1;
	private Teleporter t2;
	
	public EEFactory() {
		
		t2 = new Teleporter(false);
		
		t1 = new Teleporter(true);
		
		t1.setReceiving(t2);
	}
	public EnvironmentElement getEE(Character ID)
	{		
		if (ID == 'W')
		{
			return new Wall();
		}
		else if (ID == 'P')
		{
			return new Pit();
		}
		else if (ID == 'O')
		{
			return new OilSpill();
		}
		else if (ID == 'S')
		{
			return t1;
		}
		else if (ID == 'T')
		{
			return t2;
		}
		else if (ID == '^')
		{
			return new ConveyorBelt(Orientation.UP);
		}
		else if (ID == '>')
		{
			return new ConveyorBelt(Orientation.RIGHT);
		}
		else if (ID == '<')
		{
			return new ConveyorBelt(Orientation.LEFT);
		}
		else if (ID == 'v')
		{
			return new ConveyorBelt(Orientation.DOWN);
		}
		else if (ID == 'H')
		{
			return new HealthStation();
		}
		else if (ID == 'L')
		{
			return new Laser(); 
		}
		else if (ID == 'A')
		{
			return new Gear(true);
		}
		else if (ID == 'K')
		{
			return new Gear(false);
		}
		else if (ID == 'R')
		{
			return new ReversalPanel();
		}
		else if (ID == 'C')
		{
			return new ChainingPanel();
		}
		else if (ID == '1')
		{
			return new Checkpoint(1);
		}
		else if (ID == '2') 
		{
			return new Checkpoint(2);
		}
		else if (ID == '3')
		{
			return new Checkpoint(3);
		}
		else if (ID == '4')
		{
			return new Checkpoint(4);
		}
		else if (ID == '5')
		{
			return new FinalCheckpoint(5);
		}
		else 
		{
			return null;
		}
	}
}
