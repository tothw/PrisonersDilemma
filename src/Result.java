/**
 * 
 * @author tothw
 *
 * Result encapsulates the information an agent is given 
 * after a Prisoner's Dilemma Game
 */
public class Result {
	int opponentChoice;
	int yourScore;
	
	//Setters
	Result setOpponentChoice(int opponentChoice){
		this.opponentChoice = opponentChoice;
		return this;
	}
	
	Result setYourScore(int yourScore){
		this.yourScore = yourScore;
		return this;
	}
	
	//Getters
	int getOpponentChoice(){
		return opponentChoice;
	}
	
	int getYourScore(){
		return yourScore;
	}
}
