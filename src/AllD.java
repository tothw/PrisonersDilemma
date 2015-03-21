
public class AllD extends Agent{

	public AllD(int memoryDepth){
		super(memoryDepth);
	}
	
	public String getName(){
		return "AllD (always defect)";
	}

	@Override
	public void establishPremises() {
		for(int i = 0; i<premises.length; ++i){
			premises[i] = 1; //assume defection
		}
	}

	@Override
	public void createPlan() {
		for(int i = 0; i<strategy.length;++i){
			strategy[i] = 1;//always defect
		}
	}
}
