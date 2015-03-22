
public class STFT extends Agent {

	public STFT(int memoryDepth) throws Exception{
		super(memoryDepth);
	}
	
	public String getName(){
		return "STFT (suspicious tit-for-tat)";
	}

	@Override
	public void establishPremises() {
		for(int i = 0; i<premises.length; ++i){
			premises[i] = 1;//assume we start with defecting
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
