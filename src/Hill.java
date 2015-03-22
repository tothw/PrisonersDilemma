public class Hill extends Agent{
	
	public Hill(int memoryDepth, String chromosome) throws Exception {
		super(memoryDepth, chromosome);
	}

	@Override
	public void establishPremises() {
		for(int i = strategy.length; i<strategy.length + premises.length; ++i){
			System.out.println(i);
			System.out.println(chromosome);
			premises[i - strategy.length] = (int)(chromosome.charAt(i) - '0');
		}
	}

	@Override
	public void createPlan() {
		for(int i = 0; i<strategy.length; ++i){
			strategy[i] = (int)(chromosome.charAt(i) - '0');
		}
	}

	@Override
	public String getName() {
		return "HillClimbing Algorithm";
	}

	
}
