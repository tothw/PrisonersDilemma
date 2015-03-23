// Code Written By: Matthew Kelly
import java.util.*;
// Class to solve for a good chromosome solution to the IPD problem
public class GeneticAlgorithm{
	final int memoryDepth = 3;
	final int generations = 1000;
	final double mutationRate = 0.001;
	final int populationSize = 100;
	GeneticAgent [] population;
	// CONSTRUCTOR
	public GeneticAlgorithm()throws Exception{
		// INIT stuff
		population = new GeneticAgent[populationSize];
		for(int i = 0; i < populationSize; ++i){
			population[i] = new GeneticAgent(memoryDepth);
		}
		Agent [] opponents = { new AllC(memoryDepth), new AllD(memoryDepth), new Rand(memoryDepth), new TFT(memoryDepth),
		                       new TF2T(memoryDepth), new STFT(memoryDepth)};
		// for specified generations, make the population play the game against the specified opponents, and adapt
		for(int j = 0; j < generations; ++j){
			for(int i = 0; i < populationSize; ++i) population[i].reset();
			for(int k = 0; k < opponents.length; ++k) opponents[k].reset();
			for(int i = 0; i < populationSize; ++i){
				for(int k = 0; k < opponents.length; ++k){
					playIPD(population[i], opponents[k]);
				}
			}
			// adapts the chromosomes of the population, by creating children from the fitter half
			survivalOfTheFittest();
			// prints out the top dog of each generation
			System.out.println(population[0].printStrategy() + "  :  " + population[0].totalScore);
		}
	}
	// method to return needed information to Toth in MAIN
	public String returnToToth(){
		return population[0].printStrategy();
	}
	// method stolen from MAIN to track score of IPD between two players, used to calculate fitness
	public void playIPD(Agent player1, Agent player2){
		Game game = new Game(player1, player2);
		int totalIterations = 200;
		for (int i = 1; i <= totalIterations; ++i) {
			game.play();
		}
	}
	// method to sort who lives and who dies
	public void survivalOfTheFittest() throws Exception{
		// sort the population by fitness
		Arrays.sort(population);
		// for the bottom (least fit) half of the population, generate new children
		for(int i = populationSize / 2; i < populationSize; ++i){
			int parent1 = getParentIndex();
			int parent2 = getParentIndex();
			while(parent1 == parent2) parent2 = getParentIndex();
			// reproduce children from 2 different parents
			GeneticAgent [] children = Reproduce(parent1, parent2);
			population[i] = children[0];
			population[++i] = children[1];
		}
	}
	// make children based on parents chromosomes
	public GeneticAgent[] Reproduce(int parent1, int parent2) throws Exception{
		GeneticAgent [] children = new GeneticAgent[2];
		children[0] = new GeneticAgent(memoryDepth);
		children[1] = new GeneticAgent(memoryDepth);
		// strings used to manipulate the chromosomes easier
		String chromosome1 = "";
		String chromosome2 = "";
		// set the chromosomes to be that of the parents, uses strategy and premises
		for(int i = 0; i < children[0].power(memoryDepth); ++i){
			chromosome1 += population[parent1].strategy[i];
			chromosome2 += population[parent2].strategy[i];
		}
		for(int i = 0; i < 2 * memoryDepth; ++i){
			chromosome1 += population[parent1].premises[i];
			chromosome2 += population[parent2].premises[i];
		}
		// random point crossover of the first
		int crossover = (int)(Math.random() * (chromosome1.length() / 2));
		// split up the strings
		String chromo1sub1 = chromosome1.substring(0,crossover);
		String chromo1sub2 = chromosome1.substring(crossover);
		String chromo2sub1 = chromosome2.substring(0, chromosome2.length() - crossover);
		String chromo2sub2 = chromosome2.substring(chromosome2.length() - crossover);
		// crossover and complete the chromosomes
		chromosome1 = chromo2sub2 + chromo1sub2;
		chromosome2 = chromo1sub1 + chromo2sub1;
		// based on the mutationRate, mutate a bit in the chromosome
		if(Math.random() < mutationRate)
			chromosome1 = mutate(chromosome1);
		if(Math.random() < mutationRate)
			chromosome2 = mutate(chromosome2);
		int i;
		char zero = '0';
		// store chromosomes back into strategy and premises
		for(i = 0; i < children[0].power(memoryDepth); ++i){
			children[0].strategy[i] = chromosome1.charAt(i) - zero;
			children[1].strategy[i] = chromosome2.charAt(i) - zero;
		}
		for(int j = 0; i < chromosome1.length(); ++i,++j){
			children[0].premises[j] = chromosome1.charAt(i) - zero;
			children[1].premises[j] = chromosome2.charAt(i) - zero;
		}
		// return the new created children
		return children;
	}
	// mutate a random bit in the chromosome
	public String mutate(String chromosome){
		// choose the random bit
		int mutation = (int)(Math.random() * chromosome.length());
		// store beginning of the chromosome
		String beg = chromosome.substring(0,mutation);
		// store the flip of the bit at the random position
		String flip = (chromosome.charAt(mutation) == 0) ? "1":"0";
		// store the end of the chromosome
		String end = chromosome.substring(mutation + 1);
		// return the chromosome with the flipped bit
		return beg + flip + end;
	}
	// get a random parent index, returns a value from the fitter half
	public int getParentIndex(){
		return (int)(Math.random() * populationSize / 2);
	}
}
// agent that can be sorted by fitness of genetics
class GeneticAgent extends Agent implements Comparable<GeneticAgent>{
	public GeneticAgent(int memoryDepth) throws Exception{
		super(memoryDepth);
	}
	public String getName(){
		return "Genetic Algorithm";
	}
	// establish random premises
	@Override
	public void establishPremises(){
		for(int i = 0; i < 2 * memoryDepth; ++i){
			premises[i] = randomBit();
		}
	}
	// establish random plan
	@Override
	public void createPlan(){
		for(int i = 0; i < power(memoryDepth); ++i){
			strategy[i] = randomBit();
		}
	}
	// returns 1 or 0
	public int randomBit(){
		return (int)(Math.random() + 0.5);
	}
	// compare fitness (score) of agents putting the fitter before the weaker
	public int compareTo(GeneticAgent agent){
		return (new Integer(agent.totalScore).compareTo(new Integer(totalScore)));
	}
}
