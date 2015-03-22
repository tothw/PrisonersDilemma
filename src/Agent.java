/**
 *  
 * @author tothw
 *
 * Agents play the Iterated Prisoners Dilemma Game
 */

public abstract class Agent {
	
	int totalScore;
	int turn;
	int memory[];
	/**
	 * Memory stores what happened i+1 moves ago
	 * Your choice	Opp Choice	Code
	 * 0			0			0
	 * 0			1			1
	 * 1			0			2
	 * 1			1			3
	 **/
	int memoryDepth;
	
	int strategy[];
	//Strategy is an array of size 4^memoryDepth encoding what to do in each possible situation

	int premises[]; 
	//Premises is an array encoding what to do during the start of the game
	
	public Agent(int memoryDepth){
		this.memoryDepth = memoryDepth;
		turn = 0;
		premises = new int[2*memoryDepth];
		establishPremises();
		reset();
		strategy = new int[power(memoryDepth)];
		createPlan();
	}
	
	private int power(int memoryDepth){
		int retval = 1;
		for(int i = 0; i<memoryDepth; ++i){
			retval *= 4;
		}
		return retval;
	}
	
	//initial premises here
	public abstract void establishPremises();
	//initialize strategy here
	public abstract void createPlan();
	
	public int computeStrategyIndex(){
		int index = 0;
		for(int i = 0; i<memory.length; ++i){
			index = index + memory[i]*power(i);
		}
		return index;
	}
	public int[] invertStrategyIndex(int index){
		int retval[] = new int[memoryDepth];
		for(int i = 0; i<memoryDepth; ++i){
			int place = index % 4;
			index = index / 4;
			retval[i] = place;
		}
		return retval;
	}

	
	//0 encodes cooperate, 1 encodes defect
	public int makeChoice(){
		++turn;
		return strategy[computeStrategyIndex()];
	}
	
	//passes Result to Agent
	public void giveResult(Result result){
		totalScore += result.getYourScore();
		for(int i = memoryDepth-1; i>0; --i){
			memory[i] = memory[i-1];
		}
		memory[0] = result.getSituationCode();
	}
	//resets Agent state
	public void reset(){
		memory = new int[memoryDepth];
		for(int i = 0; i<memoryDepth; ++i){
			memory[i] = 2*premises[2*i] + premises[2*i+1];
		}
		totalScore = 0;
	}
	//prints strategy name
	public abstract String getName();
	
	public String printStrategy(){
		StringBuilder sb = new StringBuilder(strategy.length + premises.length);
		for(int i = 0; i<strategy.length; ++i){
			sb.append(strategy[i]);
		}
		for(int i = 0; i<premises.length; ++i){
			sb.append(premises[i]);
		}
		return sb.toString();
	}
	
	public int getTotalScore(){
		return totalScore;
	}
}
