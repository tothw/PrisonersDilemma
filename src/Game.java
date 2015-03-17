/**
 * 
 * @author tothw
 *
 * Game plays the Iterated Prisoners Dilemma between two Agents
 */
public class Game {
	
	Agent player1;
	Agent player2;
	
	int payoffMatrix1[][];
	int payoffMatrix2[][];
	
	int totalScore1;
	int totalScore2;
	
	int choice1;
	int choice2;
	
	int score1;
	int score2;
	
	public Game(Agent player1, Agent player2){
		this.player1 = player1;
		this.player2 = player2;
		
		initializePayoffMatrix1();
		initializePayoffMatrix2();
		
		totalScore1 = 0;
		totalScore2 = 0;
		
		choice1 = -1;
		choice2 = -1;
		score1 = -1;
		score2 = -1;
	}
	
	public void initializePayoffMatrix1(){
		//First index is p1's choice, second p2's choice
		//Scores given to p1
		payoffMatrix1 = new int[2][2];
		payoffMatrix1[0][0] = 3;
		payoffMatrix1[0][1] = 0;
		payoffMatrix1[1][0] = 5;
		payoffMatrix1[1][1] = 1;
	}
	
	public void initializePayoffMatrix2(){
		//Scores given to p2
		payoffMatrix2 = new int[2][2];
		payoffMatrix2[0][0] = 3;
		payoffMatrix2[0][1] = 5;
		payoffMatrix2[1][0] = 0;
		payoffMatrix2[1][1] = 1;
	}
	
	public void play(){
		choice1 = player1.makeChoice();
		choice2 = player2.makeChoice();
		
		score1 = payoffMatrix1[choice1][choice2];
		score2 = payoffMatrix2[choice1][choice2];
		
		Result result1 = new Result().setYourScore(score1).setOpponentChoice(choice2);
		Result result2 = new Result().setYourScore(score2).setOpponentChoice(choice1);
		
		player1.giveResult(result1);
		player2.giveResult(result2);
		
		totalScore1+=score1;
		totalScore2+=score2;
	}
	
	public String printResults(){
		return "Player 1 chose " + choice1 + "\n"
		+ "Player 2 chose " + choice2 + "\n"
		+ "Player 1 received a score of " + score1 + "\n"
		+ "Player 2 received a score of " + score2 + "\n"
		+ "Player 1 has a total score of " + totalScore1 + "\n"
		+ "Player 2 has a total score of " + totalScore2 + "\n";
	}
}

