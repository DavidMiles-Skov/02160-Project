package player;
import piece_basics.Robot;

import java.util.ArrayList;

import cards.*;

public class Player {
	private Robot myRobot;
	private Hand hand;
	
	public void setRobot(Robot r)
	{
		this.myRobot = r;
	}
	
	public Robot getRobot() {
		return myRobot;
	}
	
	public void setHand(Hand hand)
	{
		this.hand = hand;
	}
	public Hand getHand()
	{
		return hand;
	}
	
	
	public void setProgram(ArrayList<Card> p)
	{
		// will get player to select 5 cards according to execution priority
		this.myRobot.setProgram(p);
	}
	
	public Program getProgram()
	{
		return myRobot.getProgram();
	}
	
	
	
}
