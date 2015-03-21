/**
 * 
 * @author tothw
 *
 * Result encapsulates the information an agent is given 
 * after a Prisoner's Dilemma Game
 */
public class Result {
	int situationCode;
	/**
	 * Your choice	Opp Choice	Code
	 * 0			0			0
	 * 0			1			1
	 * 1			0			2
	 * 1			1			3
	 */
	int yourScore;
	
	//Setters
	Result setSituationCode(int situationCode){
		this.situationCode = situationCode;
		return this;
	}
	
	Result setYourScore(int yourScore){
		this.yourScore = yourScore;
		return this;
	}
	
	//Getters
	int getSituationCode(){
		return situationCode;
	}
	
	int getYourScore(){
		return yourScore;
	}
}
