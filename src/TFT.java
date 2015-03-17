
public class TFT implements Agent{

	int oppChoice;
	
	public TFT(){
		reset();
	}
	
	@Override
	public int makeChoice() {
		return oppChoice;
	}

	@Override
	public void giveResult(Result result) {
		oppChoice = result.getOpponentChoice();
	}
	
	public void reset(){
		oppChoice = 0;
	}
	
	public String getName(){
		return "TFT (tit-for-tat)";
	}
}
