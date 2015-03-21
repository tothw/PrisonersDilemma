// Code Written By: Matthew Kelly
// UWin id: kelly112
import java.util.*;
// NOTE 0 is COOPERATE and 1 is DEFECT
public class GeneticAlgorithm {
	// players is the population of each generation, each Player has a chromosome(64-bit strategy of how it plays IPD)
	private Player [] players;
	// opponent is the chromosome of the opponent each player in the population will play
	private String opponent;
	// this is the chance that a random bit will be flipped in a childs chromosome during reproduction
	private final double mutationRate = 0.001;
	// this is the matrix that denotes the payoff of the game, dependant on both players
	private int payoff1[][];

	public GeneticAlgorithm() {
		init();
		// for 1000 generations
		for(int i=0; i < 1000; i++) {
			// reset the calculated fitness of the population
			for(int j = 0; j < 100; j++)
				players[j].setFitness(0);
			// generate an opponent to be played against
			generateNewOpponent();
			// calculate the fitness of the new generation, based on the current opponent
			calculateFitness();
			// create a new population of the 'fittest'
			survivalOfTheFittest();
		}
		// print out the fittest player
		System.out.println(players[0]);
	}
	// initialize things (what else would init do?)
	private void init() {
		// initialize the population
		players = new Player[100];
		// for each player in the population
		for(int i = 0; i < 100; i++){
			// initialize each player
			players[i] = new Player();
			// generate a chromosome for each player
			for(int j = 0; j < 64; j++){
				players[i].setChromosome(players[i].getChromosome()  + (int)(Math.random() + 0.5));
			}
		}
		// initialize the payoff matrix based on IPD
		payoff1 = new int[2][2];
		payoff1[0][0] = 3;
		payoff1[0][1] = 0;
		payoff1[1][0] = 5;
		payoff1[1][1] = 1;

	}

	// generate the chromosome of the opponent, currently this is done with random bits
	private void generateNewOpponent(){
		opponent = "";
		for(int i = 0; i < 64; i++)
			opponent += (int)(Math.random() + 0.5);

	}
	// calculate the fitness of each player in the generation against the opponent
	private void calculateFitness() {
		char zero = '0';
		for(int i = 0; i < 100; i++){
			for(int j = 0; j < 64; j++){
				char p = players[i].getChromosome().charAt(j);
				char o = opponent.charAt(j);

				int p1 = p - zero;
				int p2 = o - zero;
				players[i].setFitness(players[i].getFitness() + payoff1[p1][p2]);
			}
		}
	}
	/* sorts the population based on fitness, greatest appearing first in the list
	 * chooses 2 random, different parents from the fitter half, and uses them to create
	 * new children for the less fit half
	 */
	private void survivalOfTheFittest() {
		Arrays.sort(players);
		// for the less fit (last) half of the population, generate new children in pairs
		for(int i = 50; i < 100; i+=2){
			int parent1 = (int)(Math.random() * 50);
			int parent2 = (int)(Math.random() * 50);
			while(parent1 == parent2) parent2 = (int)(Math.random() * 50);
			Player[] children = Reproduce(parent1, parent2);
			players[i] = children[0];
			players[i+1] = children[1];
		}
	}
	// method to generate 2 new children based on 2 parents
	private Player[] Reproduce(int parent1, int parent2) {
		// initialize 2 new players to be returned (the children)
		Player[] children = new Player[2];
		children[0] = new Player();
		children[1] = new Player();
		// set each child chromosome to a parents' chromosome
		String kid1 = players[parent1].getChromosome();
		String kid2 = players[parent2].getChromosome();
		// select a random crossover point, allowing crossover up to half of the chromosome
		int crossover = (int)(Math.random() * 32);
		// based on the crossover separate the front of the first and the back of the last as the crossover strings
		String kid1sub1 = kid1.substring(0, crossover);
		String kid1sub2 = kid1.substring(crossover);
		String kid2sub1 = kid2.substring(0, kid2.length() - crossover);
		String kid2sub2 = kid2.substring(kid2.length() - crossover);
		// crossover the back of the second with the back of the first
		kid1 = kid2sub2 + kid1sub2;
		// crossover the front of the first with the front of the second
		kid2 = kid1sub1 + kid2sub1;
		// which bit to flip
		int mutation;
		// string before the flipped bit
		String beg;
		// flipped bit
		String flip;
		// string after the flipped bit
		String end;
		// based on the mutationRate, mutate child1
		if(Math.random() < mutationRate){
			// choose a random bit to mutate
			mutation = (int)(Math.random() * 64);
			// store the beginning of the string
			beg = kid1.substring(0, mutation);
			// store the flipped bit (mutation)
			if(kid1.charAt(mutation)=='0')
				flip = "1";
			else
				flip = "0";
			// store the end of the string
			end = kid1.substring(mutation+1);
			// set child1 to be with the mutation
			kid1 = beg + flip + end;
		}
		// same as above but for child2
		if(Math.random() < mutationRate){
			mutation = (int)(Math.random() * 64);
			beg = kid2.substring(0, mutation);
			if(kid2.charAt(mutation)=='0')
				flip = "1";
			else
				flip = "0";
			end = kid2.substring(mutation+1);
			kid2 = beg + flip + end;
		}
		// after mutation set the 2 children's chromosomes
		children[0].setChromosome(kid1);
		children[1].setChromosome(kid2);
		// return the children created by the parents
		return children;
	}
	// class to store each player's chromosome and fitness, used to sort based on fitness
	private class Player implements Comparable<Player> {
		private String chromosome;
		private int fitness;
		public Player(){
			chromosome = "";
			fitness = 0;
		}
		// getters and setters for chromosome and fitness
		public void setChromosome(String chromo){
			chromosome = chromo;
		}
		public void setFitness(int fit){
			fitness = fit;
		}
		public String getChromosome(){
			return chromosome;
		}
		public int getFitness(){
			return fitness;
		}
		// when sorting players, sort by fittest first
		public int compareTo(Player player){
			return (new Integer(player.getFitness())).compareTo(new Integer(fitness));
		}
		public String toString() {
			return "Chromosome: " + chromosome + ", Fitness: " + fitness;
		}
	}
}
