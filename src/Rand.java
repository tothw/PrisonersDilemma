import java.util.Random;

public class Rand extends Agent{

	public Rand(int memoryDepth) throws Exception{
		super(memoryDepth);
	}
	
	
	public String getName(){
		return "Rand (choose randomly)";
	}
	
	@Override
	public int makeChoice(){
		int choice = new Random().nextInt(2);
		strategy[computeStrategyIndex()] = choice;
		return choice;
	}
	
	@Override
	public void establishPremises() {
		for(int i = 0; i<premises.length; ++i){
			premises[i] = 0;//doesn't matter
		}
	}

	@Override
	public void createPlan() {
		for(int i = 0; i<strategy.length; ++i){
			strategy[i] = 4;//will generate plan randomly
		}
	}
}
