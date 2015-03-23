
public class HillAlgorithm{
	final int memoryDepth = 3;
	HillAgent  population;
	public HillAlgorithm()throws Exception{

		population = new HillAgent(memoryDepth);
		Agent [] opponents = { new AllC(memoryDepth), new AllD(memoryDepth), new Rand(memoryDepth), new TFT(memoryDepth),
			new TF2T(memoryDepth), new STFT(memoryDepth)};
		for(int j = 0; j < 10; ++j){
			population.reset();
			for(int k = 0; k < opponents.length; ++k) opponents[k].reset();
			for(int k = 0; k < opponents.length; ++k){
				playIPD(population, opponents[k]);
			}
			goToNextState();
			System.out.println(population.printStrategy() + "  :  " + population.totalScore);
		}
	}

	public String returnToToth(){
		return population.printStrategy();
	}
	public void goToNextState() throws Exception
	{	
		for(char c : population.printStrategy().toCharArray())
		{	
			HillAgent newPop = new HillAgent(memoryDepth);
			newPop.strategy = population.strategy;
			for(int i : newPop.strategy)
				if(newPop.totalScore > population.totalScore)
				{
					population = newPop;
					break;
				}
			
		}
	//		population.strategy = population.strategy;
	}
	public void playIPD(Agent player1, Agent player2){
                              System.out.println("Player1 will be " + player1.getName());
                              System.out.println("With strategy string " + player1.printStrategy());
                              System.out.println("Player 2 will be " + player2.getName());
                              System.out.println("With strategy string " + player2.printStrategy() + "\n");

                //player1.reset();
                //player2.reset();

                Game game = new Game(player1, player2);

                int totalIterations = 200;

                for (int i = 1; i <= totalIterations; ++i) {
                        game.play();

                        //System.out.println("During iteration " + i + ":");
                        //System.out.println(game.printResults());
                }
        
	}
	class HillAgent extends Agent implements Comparable<HillAgent>{
		public HillAgent(int memoryDepth) throws Exception{
			super(memoryDepth);
		}
		public String getName(){
			return "HillClimbing Algorithm";
		}
		@Override
			public void establishPremises(){
				for(int i = 0; i < 2 * memoryDepth; ++i){
					premises[i] = randomBit();
				}
			}
		@Override
			public void createPlan(){
				for(int i = 0; i < power(memoryDepth); ++i){
					strategy[i] = randomBit();
				}
			}
		public int randomBit(){
			return (int)(Math.random() + 0.5);
		}
		
		public int compareTo(HillAgent agent){
			return (new Integer(agent.totalScore).compareTo(new Integer(totalScore)));
		}		
	}
}
