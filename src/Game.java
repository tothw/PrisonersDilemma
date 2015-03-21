/**
 * 
 * @author tothw
 *
 * Game plays the Iterated Prisoners Dilemma between two Agents
 */
public class Game {
	
	Agent player1;
	Agent player2;
	
	int payoffMatrix[];
	
	int totalScore1;
	int totalScore2;
	
	int choice1;
	int choice2;
	
	int score1;
	int score2;
	
	public Game(Agent player1, Agent player2){
		this.player1 = player1;
		this.player2 = player2;
		
		initializePayoffMatrix();
		
		totalScore1 = 0;
		totalScore2 = 0;
		
		choice1 = -1;
		choice2 = -1;
		score1 = -1;
		score2 = -1;
	}
	
	public int computeSituation(int yourChoice, int oppChoice){
		return yourChoice*2 + oppChoice;
	}
	
	public void initializePayoffMatrix(){
		//First index is p1's choice, second p2's choice
		//Scores given to p1
		payoffMatrix = new int[4];
		payoffMatrix[0] = 3; //both cooperate
		payoffMatrix[1] = 0; //opponent defects, you cooperate
		payoffMatrix[2] = 5; //you defect, opponent cooperates
		payoffMatrix[3] = 1; //both defect
	}
	
	public void play(){
		choice1 = player1.makeChoice();
		choice2 = player2.makeChoice();
		
		int situation1 = computeSituation(choice1, choice2);
		int situation2 = computeSituation(choice2, choice1);
				
		
		score1 = payoffMatrix[situation1];
		score2 = payoffMatrix[situation2];
		
		Result result1 = new Result().setYourScore(score1).setSituationCode(situation1);
		Result result2 = new Result().setYourScore(score2).setSituationCode(situation2);
		
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

