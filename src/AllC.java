
public class AllC implements Agent{

	@Override
	public int makeChoice() {
		return 0;
	}

	@Override
	public void giveResult(Result result){
		
	}
	
	public void reset(){
		
	}
	
	public String getName(){
		return "AllC (always cooperate)";
	}

}
