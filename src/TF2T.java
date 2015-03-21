
public class TF2T extends Agent {
	
	public TF2T(int memoryDepth){
		super(memoryDepth);
	}
	
	public String getName(){
		return "TF2T (tit-for-2-tats)";
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
			if(situations[0] % 2 == 1 & situations[1] % 2 == 1) 
				strategy[i] = 1;
			else
				strategy[i] = 0;
		}
	}
}
