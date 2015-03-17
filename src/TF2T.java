
public class TF2T implements Agent {

	int oppLastChoice;
	int oppSecondLastChoice;
	
	public TF2T(){
		reset();
	}
	
	@Override
	public int makeChoice() {
		if(oppLastChoice == 1 && oppSecondLastChoice ==1)
			return 1;
		return 0;
	}

	@Override
	public void giveResult(Result result) {
		oppSecondLastChoice = oppLastChoice;
		oppLastChoice = result.getOpponentChoice();
	}
	
	public void reset(){
		oppLastChoice = 0;
		oppSecondLastChoice = 0;
	}
	
	public String getName(){
		return "TF2T (tit-for-2-tats)";
	}
}
