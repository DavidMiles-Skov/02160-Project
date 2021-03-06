package controller;


import game_basics.Board;
import view.BoardCreationView;

public class BoardCreationController {
	
	private ApplicationController application;
	private BoardCreationView view;
	
//	private List<String> elements;
	
	private String selected_element;
	private boolean element_is_selected;
	private Board created_board;
	

	
	
	BoardCreationController(ApplicationController appl){
		application = appl;
		element_is_selected = false;
		
		this.view = new BoardCreationView(this);
	}


	public void display() {
		view.setVisible(true);
	}
	
	public void initiateSetupMenu() {
		
		view.setVisible(false);
		application.initiate();
		
	}
	
	public void saveBoard(Board board) {
		
		// save board.matrix in a text file
		System.out.println("boardGenerated");
		
	}
	
	public boolean elementIsActive() {
		
		return element_is_selected;
		
	}
	
	public void setElementIsActive(String id ) {
			
			element_is_selected = true;
			setSelectedElement(id);
		
		
	}
	
	public String getSelectedElement() {
		return selected_element;
	}
	
	public void setSelectedElement( String id) {
		
		selected_element = id;
		
	}
	
	public void setCreatedBoard(Board b) {
		created_board = b;
	}
	
	public Board getCreatedBoard() {
		return created_board;
	}
	
	
	public void startGame(Board newBoard, int numPlayers) {
		view.setVisible(false);
		application.startCustomGame(numPlayers, newBoard);
	}
	
	
}
