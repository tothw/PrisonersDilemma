import java.util.*;
public class HillAlgorithm{
	final int memoryDepth = 3;
	HillAgent [] population;
	public HillAlgorithm()throws Exception{
		/*
		   population = new GeneticAgent[populationSize];
		   for(int i = 0; i < populationSize; ++i){
		   population[i] = new GeneticAgent(memoryDepth);
		   }
		   Agent [] opponents = { new AllC(memoryDepth), new AllD(memoryDepth), new Rand(memoryDepth), new TFT(memoryDepth),
		   new TF2T(memoryDepth), new STFT(memoryDepth)};
		   for(int j = 0; j < generations; ++j){
		   for(int i = 0; i < populationSize; ++i) population[i].reset();
		   for(int k = 0; k < opponents.length; ++k) opponents[k].reset();
		   for(int i = 0; i < populationSize; ++i){
		   for(int k = 0; k < opponents.length; ++k){
		   playIPD(population[i], opponents[k]);
		   }
		   }
		   survivalOfTheFittest();
		   System.out.println(population[0].printStrategy() + "  :  " + population[0].totalScore);
		 */	
	}
	}

	public String returnToToth(){
		return population[0].printStrategy();
	}

	class HillAgent extends Agent implements Comparable<GeneticAgent>{
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
