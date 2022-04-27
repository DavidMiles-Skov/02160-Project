package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import board.Board;
import board.BoardRetriever;

import board.Position;
import controller.BoardCreationController;
import controller.MasterController;
import environment_elements.ChainingPanel;
import environment_elements.Checkpoint;
import environment_elements.ConveyorBelt;
import environment_elements.Fire;
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
import property_changes.PropertyChangeSupport;
import utils.GridBagLayoutUtils;

public class BoardCreationView extends JFrame{
	
	
	
		
	private static final long serialVersionUID = 5L;
	
	private BoardCreationController controller;
	private Board newBoard;
	
	
//	private BoardPanel boardPanel;
	private BoardCreationPanel boardPanel;
	private ElementSelectionPanel elements;
	private JButton cancelButton;
	private JButton createButton;
	
	private HashMap<EnvironmentElement, Character> EEToascii = new HashMap<>();
	

	
	private PropertyChangeSupport brd;
	
	// for testing
	
	public BoardCreationView(BoardCreationController boardCreationController) {
		
		initialiseHM();
		
		
		setController(boardCreationController);
		initGUI();
	}
	
	
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("RoboRally Board Creator");
		
		setLayout(new GridBagLayout());
		
		brd = new PropertyChangeSupport();
		
		boardPanel = new BoardCreationPanel(12, 12, controller, new Board(12, 12, brd));
		elements = new ElementSelectionPanel(controller);
		
		cancelButton = new JButton("Cancel");
		createButton = new JButton("Create");
		
		cancelButton.addActionListener(e -> {
			controller.initiateSetupMenu();
		}); 
		
		createButton.addActionListener(e -> {
			//call method in controller that given a board of cells creates a text file
			
			try {
				newBoard = controller.getCreatedBoard();
				createTextFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}); 
		
		addElements();
		pack();
		
		setVisible(true);
		
		// maximize the window
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}
	
	private void addElements() {
		setLayout(new GridBagLayout());
		add(boardPanel, GridBagLayoutUtils.constraint(0, 0, 10));
		add(elements, GridBagLayoutUtils.constraint(1, 0, 10));
		
		add(cancelButton, GridBagLayoutUtils.constraint(1, 1, 0));
		add(createButton, GridBagLayoutUtils.constraint(1, 2, 0));
		revalidate();
		repaint();
	}
	
	private void setController(BoardCreationController boardCreationController) {
		controller = boardCreationController;
	}
	
	
	private void initialiseHM()
	{
		// Initialising sending and receiving teleporter
		Teleporter T1 = new Teleporter();
		T1.setSending(true);
		Teleporter T2 = new Teleporter();
		T2.setSending(false);
		T1.setReceiving(T2);
		
		EEToascii.put( new Wall(), 'W');
		EEToascii.put( new Pit(), 'P');
		EEToascii.put( new OilSpill(), 'O');
		EEToascii.put( T1, 'S');
		EEToascii.put( T2, 'T');
		EEToascii.put( new RespawnPoint(), 'X');
		EEToascii.put( new ConveyorBelt(Orientation.UP), '^');
		EEToascii.put( new ConveyorBelt(Orientation.RIGHT), '>');
		EEToascii.put( new ConveyorBelt(Orientation.LEFT), '<');
		EEToascii.put( new ConveyorBelt(Orientation.DOWN), 'v');
		EEToascii.put( new HealthStation(), 'H');
		EEToascii.put( new Laser(), 'L');
		EEToascii.put( new Gear(true), 'A');
		EEToascii.put( new Gear(false), 'K');
		EEToascii.put( new ReversalPanel(), 'R');
		EEToascii.put( new ChainingPanel(), 'C');
		EEToascii.put( new Checkpoint(1), '1');
		EEToascii.put( new Checkpoint(2), '2');
		EEToascii.put( new Checkpoint(3), '3');
		EEToascii.put(new Checkpoint(4), '4');
		EEToascii.put( new Checkpoint(5), '5');
		EEToascii.put(null, ' ');
		
	}
	
	private void createTextFile() throws IOException {
		
 
		
		//create new file
//		final File parentDir = new File("boards");
//		parentDir.mkdir();
//		final String name = "newBoard";
//		final String fileName = name + ".txt";
//		final File file = new File(parentDir, fileName);
//		file.createNewFile();
		
		
		PrintWriter writer = new PrintWriter("boards/newBoard.txt", "UTF-8");
//		writer.println("The first line");
//		writer.println("The second line");
//		writer.close();
		
		
		
		for(int i = 0; i < newBoard.getNumRows(); i++) {
			for(int j = 0; j < newBoard.getNumColumns(); j++) {
				
				Character mappedEE;
				EnvironmentElement env;
				
				Position currentPos = new Position(j,i);
				
				if (newBoard.hasEElementAt(currentPos)) {
					
					System.out.println("EE FOUND!");
					
					env = newBoard.getEElementAt(currentPos);	
					
					mappedEE = assignChar(env);
//					mappedEE = 'O';
					System.out.println("MAPPED TO " + mappedEE);
				} else {
					mappedEE = ' ';
				}
				
//				mappedEE = EEToascii.get(env);
				writer.print(mappedEE);
			}
			writer.print("\n");
		}
		
		writer.close();
		
	}
	
	private char assignChar(EnvironmentElement e) {
		
		if (e instanceof ChainingPanel) {
			return 'C';
		} else if (e instanceof ConveyorBelt) {
			
			ConveyorBelt conv = (ConveyorBelt) (e);
			
			if ( conv.getOrientation() == Orientation.UP ) {
				return '^';
			} else if ( conv.getOrientation() == Orientation.RIGHT ) {
				return '>';
			} else if ( conv.getOrientation() == Orientation.LEFT ) {
				return '<';
			} else if ( conv.getOrientation() == Orientation.DOWN ) {
				return 'v';
			} else {
				throw new RuntimeException("Error generating character for ConveyorBelt provided.");
			}
			
			
		} else if (e instanceof Teleporter) {
			
			Teleporter tel = (Teleporter) (e);
			
			if (tel.IsSending()) {
				return 'S';
			} else if (!tel.IsSending())  {
				return 'T';
			} else {
				throw new RuntimeException("Error generating character for Teleporter provided.");
			}

		} else if (e instanceof Laser) {
			return 'L';
		} else if (e instanceof OilSpill) {
			return 'O';
		} else if (e instanceof ReversalPanel) {
			return 'R';
		} else if (e instanceof RespawnPoint) {
			return 'X';
		} else if (e instanceof Checkpoint) {
			
			Checkpoint cp = (Checkpoint) (e);
			
			if (cp.getNumber()==1) {
				return '1';
			} else if (cp.getNumber()==2)  {
				return '2';
			} else if (cp.getNumber()==3)  {
				return '3';
			} else if (cp.getNumber()==4)  {
				return '4';
			} else {
				throw new RuntimeException("Error generating character for Checkpoint provided.");
			}

		} else {
			throw new RuntimeException("Invalid input. Please enter a valid Environment Element.");
		}

	}
	
	
}
