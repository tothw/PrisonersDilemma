import java.util.Random;

public class Rand implements Agent{

	@Override
	public int makeChoice() {
		return new Random().nextInt(2);
	}

	public void giveResult(Result result){
	}

	public void reset(){
		
	}
	
	public String getName(){
		return "Rand (choose randomly)";
	}
}
