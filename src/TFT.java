
public class TFT extends Agent{
	
	public TFT(int memoryDepth) throws Exception{
		super(memoryDepth);
	}
	
	public String getName(){
		return "TFT (tit-for-tat)";
	}

	@Override
	public void establishPremises() {
		for(int i = 0; i<premises.length; ++i){
			premises[i] = 0;//assume we start by cooperating
		}
	}

	@Override
	public void createPlan() {
		for(int i = 0; i<strategy.length; ++i){
			int situations[] = invertStrategyIndex(i);
			strategy[i] = situations[0] % 2;
		}
	}
}
