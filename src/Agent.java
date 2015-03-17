/**
 *  
 * @author tothw
 *
 * Agents play the Iterated Prisoners Dilemma Game
 */

public interface Agent {

	//0 encodes cooperate 1 encodes defect
	public int makeChoice();
	//passes Result to Agent
	public void giveResult(Result result);
	//resets Agent state
	public void reset();
	//prints strategy name
	public String getName();
}
