import java.util.Scanner;

class Card
{
	//variables declared
	private int value;
	
	private String type;
	
	private boolean matched = false;
	
	private boolean drawn = false;
	
	Card(int v,char t)
	{
		value = v;
		
		//check char to set type
		if (t == 'h')
		{
			type = "heart";
		}
		else if (t == 's')
		{
			type = "spade";
		}
		else if (t == 'c')
		{
			type = "club";
		}
		else if (t == 'd')
		{
			type = "diamond";
		}
	}
	
	Card()
	{
		//default consturction
	}
	
	void getCard()
	{
		//print out card type and number
		if (value == 1)
		{
			System.out.print("Ace of " + type);
		}
		else if (value == 11)
		{
			System.out.print("Joker of " + type);
		}
		else if (value == 12)
		{
			System.out.print("Queen of " + type);
		}
		else if (value == 13)
		{
			System.out.print("King of " + type);
		}
		else
		{
			System.out.print(value + " of " + type);
		}
		
		/*
		//check if card is drawn to draw other card
		if (drawn)
		{
			System.out.print(" drawn");
		}
		*/
	}
	
	void drawCard()
	{
		drawn = true;
	}
	
	void putCardBack()
	{
		drawn = false;
	}
	
	boolean isCardDrawn()
	{
		return drawn;
	}
	
	int getValue()
	{
		return value;
	}
	
	String getType()
	{
		return type;
	}
	
	void setMatched()
	{
		matched = true;
	}
	
	boolean getMatched()
	{
		return matched;
	}
	
}

class Deck
{
	
	String deckName;
	
	//Used to tell which deck is higher in ties
	private int deckAmount = 0;
	
	//1 for best and so on
	private int deckTier = 10;
	
	void getCards(Card[] deck)
	{
		//print out all cards in deck
		//System.out.print("Cards in " + deckName + " are:\n");
		
		for (int i = 0;i < deck.length; i++)
		{
			deck[i].getCard();
			System.out.println();
		}
	}
	
	void takeFromDeck(int cardNumber,Card[] thisDeck, Card[] otherDeck)
	{
		//Drawn card is taken from deck to not be drawn again
		//generate random number 0 - 51
		int randomCard = (int) Math.floor(Math.random()*52);
		
		if (!otherDeck[randomCard].isCardDrawn())
		{
			//set 1st card 
			thisDeck[cardNumber] = otherDeck[randomCard];
			//set picked card to drawn to prevent from being picked again
			otherDeck[randomCard].drawCard();
		}
		else
		{
			//debug print out if card was taken to give new card
			//System.out.println(randomCard + " card is already drawn");
			
			//otherPile set to randomCard, used to remember initial value
			int otherPile = randomCard;
			
			//check for cards above the random card pulled
			while (randomCard < 52)
			{
				//if picked card is not drawn, draw it
				if (!otherDeck[randomCard].isCardDrawn())
				{
					thisDeck[cardNumber] = otherDeck[randomCard];
					otherDeck[randomCard].drawCard();
					randomCard = 53;
				}
				else
				{
					randomCard++;
				}
				
			}
			
			//randomCard is set to 53 if a card was drawn, if not continue here
			if (randomCard != 53)
			{
				//While otherPile is not equal to 0, check cards under it
				while (otherPile != 0)
				{
					if (!otherDeck[otherPile].isCardDrawn())
					{
						thisDeck[cardNumber] = otherDeck[otherPile];
						otherDeck[otherPile].drawCard();
						otherPile = 0;
					}
					else
					{
						otherPile--;
					}
				}
				
				if (otherPile == 0)
				{
					//all cards drawn, give illegal card of 0 to let me know something went wrong
					thisDeck[cardNumber] = new Card(0,'d');
				}
				
			}
			
			
			
		}
	}
	
	void pickCardFromDeck(int cardNumber, int whichCard, Card[] thisDeck, Card[] otherDeck)
	{
		if (!otherDeck[whichCard].isCardDrawn())
		{
			//set 1st card 
			thisDeck[cardNumber] = otherDeck[whichCard];
			//set picked card to drawn to prevent from being picked again
			otherDeck[whichCard].drawCard();
		}
		else
		{
			
			/*
			System.out.println(whichCard + " card is already drawn");
			thisDeck[cardNumber] = new Card(0,'d');
			*/
			
			int otherPile = whichCard;
			
			while (whichCard < 52)
			{
				
				if (!otherDeck[whichCard].isCardDrawn())
				{
					thisDeck[cardNumber] = otherDeck[whichCard];
					otherDeck[whichCard].drawCard();
					whichCard = 53;
				}
				else
				{
					whichCard++;
				}
				
			}
			
			if (whichCard != 53)
			{
				while (otherPile != 0)
				{
					if (!otherDeck[otherPile].isCardDrawn())
					{
						thisDeck[cardNumber] = otherDeck[otherPile];
						otherDeck[otherPile].drawCard();
						otherPile = 0;
					}
					else
					{
						otherPile--;
					}
				}
				
				if (otherPile == 0)
				{
					//all cards drawn, give illegal card of 0 to let me know something went wrong
					thisDeck[cardNumber] = new Card(0,'d');
				}
				
			}
			
			
		}
	}
	
	void getDeckValue(Card[] thisDeck, Card[] otherDeck)
	{
		//value used in case of tie or no pairs
		
		//deckTier set here
		/*
		1 for Royal Flush, 2 for straight flush, 3 for four of a kind
		4 for full house, 5 for flush, 6 for straight, 7 for three of a kind
		8 for two pair, 9 for one pair, 10 for none
		*/
		//deckTier = 0;
		
		Card[] totalCards = new Card[7];
		
		Card temp;
		
		Card[] tempArray = new Card[5];
		
		boolean royal = false;
		boolean royalFound = false;
		
		boolean straight = true;
		boolean straightFound = false;
		
		boolean flush = true;
		boolean flushFound = false;
		
		boolean straightFlush = false;
		
		boolean fourOfAKind = false;
		
		boolean threeOfAKind = false;
		
		boolean fullHouse = false;
		
		boolean twoPair = false;
		
		boolean onePair = false;
		
		int matches = 0;
		
		int pairs = 0;
		
		
		for (int k = 0; k < totalCards.length; k++)
		{
			
			if (k < 2)
			{
				totalCards[k] = thisDeck[k];
			}
			else
			{
				//offest back by 2
				totalCards[k] = otherDeck[k-2];
			}
			
		}
		
		//sort array from lowest to highest
		
		for (int m = 0;m < totalCards.length;m++)
		{
			for (int n = m + 1; n < totalCards.length;n++)
			{
				if (totalCards[m].getValue() > totalCards[n].getValue())
				{
					//Store temporarly in temp and switch
					temp = totalCards[m];
					totalCards[m] = totalCards[n];
					totalCards[n] = temp;
				}
			}
		}
		
		
		//check for straight, in it check for a straight flush, then royal flush
		//get highest flush for 1 - 5, 2 - 6, 3 - 7
		
		for (int i = 0; i < 6; i++)
		{
			//compare all cards to each other
			for (int j = i+1;j < 7;j++)
			{
				if (totalCards[i].getValue() == totalCards[j].getValue())
				{
					
					if (!totalCards[j].getMatched())
					{
						matches++;
						totalCards[j].setMatched();
					}
					
					/*
					System.out.println("match " + totalCards[i].getValue() + " and " +
					totalCards[j].getValue());
					*/
				}
				else
				{
					/*
					System.out.println("No match " + totalCards[i].getValue() + " and " +
					totalCards[j].getValue());
					*/
				}
			}
			
			if (matches > 0)
			{
				pairs++;
				onePair = true;
			}
			
			if (matches == 1)
			{
				if (pairs > 1)
				{
					twoPair = true;
				}
			}
			if (matches == 2)
			{
				//3 of a kind
				threeOfAKind = true;
				
				if (pairs > 1)
				{
					fullHouse = true;
				}
			}
			else if (matches == 3)
			{
				//4 of a kind
				fourOfAKind = true;
			}
			
			
			matches = 0;
			
		}
		
		
		for (int i = 0; i < 3;i++)
		{
			//straight until proven false
			straight = true;
			flush = true;
			
			for (int j = 0;j < 4;j++)
			{
				//check 1 or 9 away incase checking ace and 10
				if (totalCards[j+i].getValue() != totalCards[j+i+1].getValue() - 1)
				{
					if (totalCards[j+i].getValue() == 1 && totalCards[j+i+1].getValue() == 10)
					{
						//might be royal flush
						royal = true;
					}
					else
					{
						straight = false;
					}
				}
				
				if (totalCards[j+i].getType() != totalCards[j+i+1].getType())
				{
					flush = false;
				}
				
			}
			
			
			
			
			if (flush && straight)
			{
				straightFlush = true;
				
				System.out.println("s & f found");
				
				if (royal)
				{
					royalFound = true;
				}
				
				for (int k = 0;k < 5; k++)
				{
					tempArray[k] = totalCards[i+k];
				}
				
				
				
			}
			else if (flush && !straightFlush)
			{
				flushFound = true;
				
				System.out.println("f found");
				
				for (int k = 0;k < 5; k++)
				{
					tempArray[k] = totalCards[i+k];
				}
			}
			else if (straight && !straightFlush)
			{
				straightFound = true;
				
				System.out.println("s found");
				
				for (int k = 0;k < 5; k++)
				{
					tempArray[k] = totalCards[i+k];
				}
					
			}	
		}
		
		if (royalFound)
		{
			deckTier = 1;
		}
		else if (straightFlush)
		{
			deckTier = 2;
		}
		else if (fourOfAKind)
		{
			deckTier = 3;
		}
		else if (fullHouse)
		{
			deckTier = 4;
		}
		else if (flushFound)
		{
			deckTier = 5;
		}
		else if (straightFound)
		{
			deckTier = 6;
		}
		else if (threeOfAKind)
		{
			deckTier = 7;
		}
		else if (twoPair)
		{
			deckTier = 8;
		}
		else if (onePair)
		{
			deckTier = 9;
		}
		
		
		for (int i = 0 ;i < totalCards.length;i++)
		{
			deckAmount += totalCards[i].getValue();
		}
		
	}
	
	String getDeckTier()
	{
		if (deckTier == 1)
		{
			return "A Royal Flush";
		}
		else if (deckTier == 2)
		{
			return "A Straight Flush";
		}
		else if (deckTier == 3)
		{
			return "A Four of a Kind";
		}
		else if (deckTier == 4)
		{
			return "A Full House";
		}
		else if (deckTier == 5)
		{
			return "A Flush";
		}
		else if (deckTier == 6)
		{
			return "A Straight";
		}
		else if (deckTier == 7)
		{
			return "A Three of a Kind";
		}
		else if (deckTier == 8)
		{
			return "A Two Pair";
		}
		else if (deckTier == 9)
		{
			return "A One Pair";
		}
		else 
		{
			return "Not any combination";
		}
		
	}
	
	int getDeckTierAmount()
	{
		return deckTier;
	}
	
	int getDeckAmount()
	{
		return deckAmount;
	}
	
}

class TotalDeck extends Deck
{
	Card[] deck = new Card[52];
	
	char cardSymbol;
	
	TotalDeck()
	{
		
		deckName = "Total Deck";
		
		//create 52 cards, 4 of each type for a deck.
		for (int i = 0;i < 4;i++)
		{
			//run loop for each card type
			if (i == 0)
			{
				cardSymbol = 'h';
			}
			else if (i == 1)
			{
				cardSymbol = 's';
			}
			else if (i == 2)
			{
				cardSymbol = 'c';
			}
			else if (i == 3)
			{
				cardSymbol = 'd';
			}
				
			for (int j = 0;j < 13; j++)
			{
				deck[(i*13)+j] = new Card(j+1,cardSymbol);
			}
		}
	}
}

class CommunityDeck extends Deck
{
	
	//5 cards in community deck
	Card[] deck = new Card[5];
	
	CommunityDeck()
	{
		
		deckName = "Community Deck";
		
		/*
		//outdated, use other method to avoid re-picking same cards
		for (int i = 0;i < deck.length; i++)
		{
			
			int randomCard = (int) Math.floor(Math.random()*52);
			
			deck[i] = otherDeck[randomCard];
			otherDeck[randomCard].drawCard();
			
		}
		*/
	}
	
	void getCards(Card[] deck, int amount)
	{
		//print out specific number of cards, reveal more as rounds go on
		
		if (amount > deck.length)
		{
			//avoid accidentally setting higher than actual amount
			amount = deck.length;
		}
		
		for (int i = 0;i < amount; i++)
		{
			deck[i].getCard();
			System.out.println();
		}
	}
	
}

class Player extends Deck
{
	private int cash;
	
	private int betting;
	
	Card[] cards = new Card[2];
	
	Player(String myName, int myCash)
	{
		deckName = myName;
		cash = myCash;
	}
	
	void giveStartingCards(Card whichDeck[])
	{
		cards[0] = whichDeck[(int) Math.floor(Math.random()*52)];
		cards[1] = whichDeck[(int) Math.floor(Math.random()*52)];
	}
	
	
	int getCash()
	{
		return cash;
	}
	
	void setCash(int c)
	{
		cash = c;
	}
	
	int getBet()
	{
		return betting;
	}
	
	void setBet(int b)
	{
		betting = b;
	}
	
	//over ride method 
	void getCards()
	{	
		for (int i = 0;i < cards.length; i++)
		{
			cards[i].getCard();
			System.out.println();
		}
	}
	
}


class Texas
{
	public static void main(String args[])
	throws java.io.IOException
	{
		//get single character input
		char singleInput = 'a';
		//handle extra input
		char otherInput = 'a';
		
		int betAmount = 0;
		
		int round = 0;
		
		boolean gameEnded = false;
		
		int pool = 0;
		
		int gameCase = 0;
		
		
		String playerName;
		
		//Scanner for player input
		Scanner input = new Scanner(System.in);
		
		TotalDeck deck = new TotalDeck();
		//Community deck draws 5 cards from the total deck
		CommunityDeck cDeck = new CommunityDeck();
		
		
		System.out.println("Welcome to Texas hold em, enter your name");
		
		playerName = input.nextLine();
		
		System.out.print("Hello " + playerName + ", start game? y\\n\n");
		
		do
		{
			singleInput = (char) System.in.read();
			
			do
			{
				otherInput = (char) System.in.read();
			}
			while(otherInput != '\n');
			
			if (singleInput == 'y')
			{
				System.out.println("Starting\n");
			}
			else if (singleInput != '\n')
			{
				System.out.println("Enter y when you are ready.");
			}
		}
		while(singleInput != 'y');
		
		//Make Player
		Player p1 = new Player(playerName, 1000);
		
		Player p2 = new Player("bot", 1000);
		
		//Take from the total deck to players deck
		p1.takeFromDeck(0,p1.cards, deck.deck);
		p1.takeFromDeck(1,p1.cards, deck.deck);
		
		//bot cards
		p2.takeFromDeck(0,p2.cards, deck.deck);
		p2.takeFromDeck(1,p2.cards, deck.deck);
		
		//give 5 cards to comunnity deck
		for (int i = 0; i < cDeck.deck.length; i++)
		{
			cDeck.takeFromDeck(i,cDeck.deck, deck.deck);
		}
		
		
		round++;
		
		System.out.print("How much do you put in the pot?\nYour current cash: " + p1.getCash() + "\n");
		
		do
		{
			//check if input is int and not string
			try
			{
				betAmount = input.nextInt();
				if (betAmount <= 0)
				{
					System.out.println("Need to bet something");
				}
			}
			catch (Exception e)
			{
				System.out.println("That's a letter. Betting mimimum of 1.");
				betAmount = 1;
			}
			
		}
		while(betAmount <= 0);
		
		do
		{
			if (betAmount >= p1.getCash())
			{
				if (betAmount == p1.getCash())
				{
					System.out.print("You bet " + betAmount + ", you need something left to bet for next round.\n");
				}
				else
				{
					System.out.print("You bet " + betAmount + ", you don't have that much, reenter amount.\n");
				}
				
				//check for int instead of string again
				do
				{
					try
					{
						betAmount = input.nextInt();
						if (betAmount <= 0)
						{
							System.out.println("Need to bet something");
						}
					}
					catch (Exception e)
					{
						System.out.println("That's a letter. Betting mimimum of 1.");
						betAmount = 1;
					}
					
				}
				while(betAmount <= 0);
			}
		}
		while (betAmount >= p1.getCash());
		
		System.out.println("You bet " + betAmount);
		
		p1.setBet(betAmount);
		
		
		System.out.println("The bot put " + betAmount + " into the pool too");
		
		p2.setBet(betAmount);
		
		pool = p1.getBet() + p2.getBet();
		
		//loose the amount you bet for each player
		p1.setCash(p1.getCash() - p1.getBet());
		p2.setCash(p2.getCash() - p2.getBet());
		
		System.out.println("Total pool: " + pool);
		
		System.out.println("Your cards");
		p1.getCards();
		
		System.out.println("\nBot has 2 cards\n");
		
		betAmount = 0;
		
		singleInput = 'a';
		
		//Repeat for each round
		
		while (round < 4 && !gameEnded)
		{
			
			System.out.println("Raise, call or fold? Enter r for raise, c for call, f for fold.");
			
			do
			{
				singleInput = (char) System.in.read();
				
				do
				{
					otherInput = (char) System.in.read();
				}
				while(otherInput != '\n');
				
				if (singleInput == 'r')
				{
					System.out.println("Raising\n");
				}
				else if (singleInput == 'c')
				{
					System.out.println("Calling\n");
				}
				else if (singleInput == 'f')
				{
					System.out.println("Folding\n");
				}
				else
				{
					System.out.println("Enter one of the options.");
				}
			}
			while(singleInput != 'r' && singleInput != 'c' && singleInput != 'f');
			
			switch (singleInput)
			{
				case 'r':
				
				if (p1.getCash() > 0)
				{
					
					System.out.print("How much do you put in the pot?\nYour current cash: " + p1.getCash() + "\n");
			
					do
					{
						//check if input is int and not string
						try
						{
							betAmount = input.nextInt();
							if (betAmount <= 0)
							{
								System.out.println("Need to bet something");
							}
						}
						catch (Exception e)
						{
							System.out.println("That's a letter. Betting mimimum of 1.");
							betAmount = 1;
						}
						
					}
					while(betAmount <= 0);
					
					do
					{
						if (betAmount > p1.getCash())
						{
							
							System.out.print("You bet " + betAmount + ", you don't have that much, reenter amount.\n");
							
							//check for int instead of string again
							do
							{
								try
								{
									betAmount = input.nextInt();
									if (betAmount <= 0)
									{
										System.out.println("Need to bet something");
									}
								}
								catch (Exception e)
								{
									System.out.println("That's a letter. Betting mimimum of 1.");
									betAmount = 1;
								}
								
							}
							while(betAmount <= 0);
						}
					}
					while (betAmount > p1.getCash());
					
					
					
					if (betAmount == p1.getCash())
					{
						System.out.println("You bet " + betAmount + ", going all in!");
					}
					else
					{
						System.out.println("You bet " + betAmount);
					}
					
					p1.setBet(betAmount);
					
					System.out.println("The bot put " + betAmount + " into the pool too");
					
					p2.setBet(betAmount);
					
					pool += p1.getBet() + p2.getBet();
					
					//loose the amount you bet for each player
					p1.setCash(p1.getCash() - p1.getBet());
					p2.setCash(p2.getCash() - p2.getBet());
					
					System.out.println("Total pool: " + pool);
				}
				else
				{
					System.out.println("No cash left to bet, automatically calling.");
					System.out.println("Bot calls too");
				}
				
				break;
				case 'c':
				System.out.println("Bot calls too");
				break;
				case 'f':
				System.out.println("You fold and lost " + p1.getBet());
				gameEnded = true;
				gameCase = 2;
				break;
			}
			
			if (!gameEnded)
			{
				if (round == 1)
				{
					System.out.println("The Flop");
				}
				else if (round == 2)
				{
					System.out.println("The Turn");
				}
				else if (round == 3)
				{
					System.out.println("The River");
				}
				
				System.out.println("\nYour cards");
				p1.getCards();
				
				System.out.println("\nCommunity cards are");
				//show 3 for 1st round, 4 for 2nd and 5 for 3rd
				cDeck.getCards(cDeck.deck, round+2);
				round++;
			}
		}
		
		//if game has not ended, go to show down
		if (!gameEnded)
		{
			System.out.println("\nShow down");
			
			
			/*
			System.out.println("Testing");
			
			p1.cards[0] = new Card(1,'h');
			p1.cards[1] = new Card(2,'d');
			
			cDeck.deck[0] = new Card(1,'d');
			cDeck.deck[1] = new Card(2,'h');
			cDeck.deck[2] = new Card(3,'d');
			cDeck.deck[3] = new Card(4,'d');
			cDeck.deck[4] = new Card(6,'h');
			*/
			
			System.out.println("\nYour cards");
			p1.getCards();
			
			System.out.println("\nBots cards");
			p2.getCards();
			
			p1.getDeckValue(p1.cards, cDeck.deck);
			p2.getDeckValue(p2.cards, cDeck.deck);
			
			System.out.println("");
			
			
			System.out.println(playerName + "'s tier is " + p1.getDeckTier());
			System.out.println("The bot's tier is " + p2.getDeckTier());
			
			if (p1.getDeckTierAmount() < p2.getDeckTierAmount())
			{
				System.out.println("You win");
				gameCase = 1;
			}
			else if (p1.getDeckTierAmount() == p2.getDeckTierAmount())
			{
				System.out.println("You tied in combinations");
				
				if (p1.getDeckAmount() > p2.getDeckAmount())
				{
					System.out.println("You have higher cards\nYou win");
					gameCase = 1;
				}
				else if (p1.getDeckAmount() == p2.getDeckAmount())
				{
					System.out.println("Tied in amount");
					gameCase = 3;
				}
				else
				{
					System.out.println("Bot has higher cards\nYou loose");
					gameCase = 2;
				}
				
			}
			else
			{
				System.out.println("You loose");
			}
		}
		
		System.out.println("End of game.");
		
	}
}