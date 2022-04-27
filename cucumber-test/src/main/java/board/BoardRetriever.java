package board;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

import environment_elements.*;
import piece_basics.EnvironmentElement;
import piece_basics.Orientation;
import property_changes.PropertyChangeSupport;

public class BoardRetriever  
{  
	
	
	private HashMap<Character, EnvironmentElement> asciiToEE = new HashMap<>();
	
	private void initialiseHM()
	{
		// Initialising sending and receiving teleporter
		Teleporter T1 = new Teleporter();
		T1.setSending(true);
		Teleporter T2 = new Teleporter();
		T2.setSending(false);
		T1.setReceiving(T2);
		
		asciiToEE.put('W', new Wall());
		asciiToEE.put('P', new Pit());
		asciiToEE.put('O', new OilSpill());
		asciiToEE.put('S', T1);
		asciiToEE.put('T', T2);
		asciiToEE.put('X', new RespawnPoint());
		asciiToEE.put('^', new ConveyorBelt(Orientation.UP));
		asciiToEE.put('>', new ConveyorBelt(Orientation.RIGHT));
		asciiToEE.put('<', new ConveyorBelt(Orientation.LEFT));
		asciiToEE.put('v', new ConveyorBelt(Orientation.DOWN));
		asciiToEE.put('H', new HealthStation());
		asciiToEE.put('L', new Laser());
		asciiToEE.put('A', new Gear(true));
		asciiToEE.put('K', new Gear(false));
		asciiToEE.put('R', new ReversalPanel());
		asciiToEE.put('C', new ChainingPanel());
		asciiToEE.put('1', new Checkpoint(1));
		asciiToEE.put('2', new Checkpoint(2));
		asciiToEE.put('3', new Checkpoint(3));
		asciiToEE.put('4', new Checkpoint(4));
		asciiToEE.put('5', new Checkpoint(5));
		
		asciiToEE.put(' ', null);
		
	}
	
	public BoardRetriever()
	{
		this.initialiseHM();
	}
	
	public Board retrieveBoard(String filename, PropertyChangeSupport pcs)  
	{
		
		
		String path = "boards/" + filename + ".txt";
		int numRows=0;
		int numCols=0;
		File doc = new File(path);
		
		// Finding dimensions of board
		
		BufferedReader obj = null;
		try {
			obj = new BufferedReader(new FileReader(doc));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String str;
		try {
			while ((str = obj.readLine()) != null) {
				numRows++;
				numCols = str.length();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Placing environment elements on the board
		
		Board b = new Board(numRows, numCols, pcs);
		int j = 0;
		try {
			obj = new BufferedReader(new FileReader(doc));
			while (obj.readLine() != null) {
				str = obj.readLine();
				for (int i = 0; i < str.length(); i++)
				{
					if (!(asciiToEE.get(str.charAt(i))==null)) {
						b.initialPlacement(asciiToEE.get(str.charAt(i)), new Position(i,j));
					}
				}
				j++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return b;
		
	}
	
		
		
}  
 