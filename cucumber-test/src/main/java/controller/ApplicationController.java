package controller;

import board.Game;
import view.InitiateView;

//import application.controller.ApplicationController;
//import application.controller.LoginController;

public class ApplicationController {
	private InitiateController initiateController;
	private Game game;

	private void initiate() {
		initiateController = new InitiateController(this);
		initiateController.display();
		
	}
	
	public void startGame(int playerCount, String difficulty) {
		game = new Game();
		game.setDifficulty(difficulty);
//		game.setPlayers(playerCount);
		
//		game.getPlayers().length
		System.out.println("Game started with " + playerCount + " players and difficulty " + game.getDifficulty());

		
	}
	
	public static void main(String[] args) {
		ApplicationController app = new ApplicationController();
		app.initiate();
	}

	

	
}