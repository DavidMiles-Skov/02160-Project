package controller;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

//import application.controller.ApplicationController;
//import application.controller.LoginController;

public class ApplicationController {
	private InitiateController initiateController;
	private MasterController masterController;

	private void initiate() {
		initiateController = new InitiateController(this);
		initiateController.display();
		
	}
	
	public void startGame(int playerCount, String difficulty) {
		//System.out.println("Game started with " + playerCount + " players.");
		masterController = new MasterController(this, playerCount, difficulty);
		masterController.display();
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// do nothing, use default look and feel
		}
		
		ApplicationController app = new ApplicationController();
		app.initiate();
	}

	

	
}