
public class AllC extends Agent{

	public AllC(int memoryDepth){
		super(memoryDepth);
	}
	
	public String getName(){
		return "AllC (always cooperate)";
	}

	@Override
	public void establishPremises() {
		for(int i = 0; i<premises.length; ++i){
			premises[i] = 0;//assume cooperation
		}
		
	}

	@Override
	public void createPlan() {
		for(int i = 0; i<strategy.length; ++i){
			strategy[i] = 0;//always cooperates
		}
	}

}
