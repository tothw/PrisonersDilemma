
public class AllD implements Agent{

	@Override
	public int makeChoice() {
		return 1;
	}

	@Override
	public void giveResult(Result result){
		
	}

	public void reset(){
		
	}
	
	public String getName(){
		return "AllD (always defect)";
	}
}
