
public class STFT implements Agent {


	int oppChoice;
	
	public STFT(){
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
		oppChoice = 1;
	}
	
	public String getName(){
		return "STFT (suspicious tit-for-tat)";
	}
}
