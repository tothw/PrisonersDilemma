import java.util.*;
public class GeneticAlgorithm{
	final int memoryDepth = 3;
	final int generations = 1000;
	final double mutationRate = 0.001;
	final int populationSize = 100;
	GeneticAgent [] population;

	public GeneticAlgorithm()throws Exception{
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
			//System.out.println(population[0].printStrategy() + "  :  " + population[0].totalScore);
		}
	}
	public String returnToToth(){
		return population[0].printStrategy();
	}
	public void playIPD(Agent player1, Agent player2){
		//		System.out.println("Player1 will be " + player1.getName());
		//		System.out.println("With strategy string " + player1.printStrategy());
		//		System.out.println("Player 2 will be " + player2.getName());
		//		System.out.println("With strategy string " + player2.printStrategy() + "\n");

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
	public void survivalOfTheFittest() throws Exception{
		Arrays.sort(population);
		for(int i = populationSize / 2; i < populationSize; ++i){
			int parent1 = getParentIndex();
			int parent2 = getParentIndex();
			while(parent1 == parent2) parent2 = getParentIndex();
			GeneticAgent [] children = Reproduce(parent1, parent2);
			population[i] = children[0];
			population[++i] = children[1];
		}
	}
	public GeneticAgent[] Reproduce(int parent1, int parent2) throws Exception{
		GeneticAgent [] children = new GeneticAgent[2];
		children[0] = new GeneticAgent(memoryDepth);
		children[1] = new GeneticAgent(memoryDepth);

		String chromosome1 = "";
		String chromosome2 = "";
		for(int i = 0; i < children[0].power(memoryDepth); ++i){
			chromosome1 += population[parent1].strategy[i];
			chromosome2 += population[parent2].strategy[i];
		}
		for(int i = 0; i < 2 * memoryDepth; ++i){
			chromosome1 += population[parent1].premises[i];
			chromosome2 += population[parent2].premises[i];
		}
		int crossover = (int)(Math.random() * (chromosome1.length() / 2));
		String chromo1sub1 = chromosome1.substring(0,crossover);
		String chromo1sub2 = chromosome1.substring(crossover);
		String chromo2sub1 = chromosome2.substring(0, chromosome2.length() - crossover);
		String chromo2sub2 = chromosome2.substring(chromosome2.length() - crossover);
		chromosome1 = chromo2sub2 + chromo1sub2;
		chromosome2 = chromo1sub1 + chromo2sub1;
		if(Math.random() < mutationRate)
			chromosome1 = mutate(chromosome1);
		if(Math.random() < mutationRate)
			chromosome2 = mutate(chromosome2);
		int i;
		char zero = '0';
		for(i = 0; i < children[0].power(memoryDepth); ++i){
			children[0].strategy[i] = chromosome1.charAt(i) - zero;
			children[1].strategy[i] = chromosome2.charAt(i) - zero;
		}
		for(int j = 0; i < chromosome1.length(); ++i,++j){
			children[0].premises[j] = chromosome1.charAt(i) - zero;
			children[1].premises[j] = chromosome2.charAt(i) - zero;
		}
		return children;
	}
	public String mutate(String chromosome){
		int mutation = (int)(Math.random() * chromosome.length());
		String beg = chromosome.substring(0,mutation);
		String flip = (chromosome.charAt(mutation) == 0) ? "1":"0";
		String end = chromosome.substring(mutation + 1);
		return beg + flip + end;
	}
	public int getParentIndex(){
		return (int)(Math.random() * populationSize / 2);
	}
}
class GeneticAgent extends Agent implements Comparable<GeneticAgent>{
	public GeneticAgent(int memoryDepth) throws Exception{
		super(memoryDepth);
	}
	public String getName(){
		return "Genetic Algorithm";
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
	public int compareTo(GeneticAgent agent){
		return (new Integer(agent.totalScore).compareTo(new Integer(totalScore)));
	}
}
