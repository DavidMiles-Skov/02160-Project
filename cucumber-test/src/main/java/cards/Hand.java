package cards;

import java.util.ArrayList;

public class Hand 
{
    private ArrayList<Card> cardList;

    public Hand(){
    }

    public ArrayList<Card> getCardList()
    {
        return cardList;
    }
    
    public void setCardList(ArrayList<Card> cardList)
    {
    	this.cardList = cardList;
    } 
    
}
