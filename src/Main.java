/**
 * 
 * @author tothw
 * 
 *         Main method controls the simulation
 */


public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		//Run a tournament
		int memoryDepth = 3;
		String chromosome = new GeneticAlgorithm().returnToToth();
		Agent agents[] = { new AllC(memoryDepth), new AllD(memoryDepth), new Rand(memoryDepth), new TFT(memoryDepth),
				new TF2T(memoryDepth), new STFT(memoryDepth), new Learn(memoryDepth), new LearnAhead(memoryDepth), new Genetic(memoryDepth, chromosome)};
		Agent opponents[] = { new AllC(memoryDepth), new AllD(memoryDepth), new Rand(memoryDepth), new TFT(memoryDepth),
				new TF2T(memoryDepth), new STFT(memoryDepth), new Learn(memoryDepth), new LearnAhead(memoryDepth), new Genetic(memoryDepth, chromosome)};
		
		int cumulativeScores[]  = new int[agents.length];
		for(int i = 0; i<cumulativeScores.length; ++i){
			cumulativeScores[i] = 0;
		}
		
		
		for (int i = 0; i < agents.length; ++i) {
			for (int j = i+1; j < opponents.length; ++j) {
				playIPD(agents[i], opponents[j]);
				cumulativeScores[i] += agents[i].getTotalScore();
				cumulativeScores[j] += opponents[j].getTotalScore();
			}
		}
		
		//find max
		int max = cumulativeScores[0];
		int winnerIndex = 0;
		for(int i = 1; i < agents.length; ++i){
			int finalScore = cumulativeScores[i];
			if(finalScore > max){
				max = finalScore;
				winnerIndex = i;
			}
		}
		for(int i = 0; i<agents.length; ++i){
			System.out.println("Agent : " + agents[i].getName() + " final score: " + cumulativeScores[i]);
		}
		System.out.println("The winner of the tournament is " + agents[winnerIndex].getName() + " with a final score of " + max + "\n");
	}

	public static void playIPD(Agent player1, Agent player2) throws Exception {

		System.out.println("Player1 will be " + player1.getName());
		System.out.println("With strategy string " + player1.printStrategy());
		System.out.println("Player 2 will be " + player2.getName());
		System.out.println("With strategy string " + player2.printStrategy() + "\n");
		
		player1.reset();
		player2.reset();
		
		Game game = new Game(player1, player2);

		int totalIterations = 200;

		for (int i = 1; i <= totalIterations; ++i) {
			game.play();

			System.out.println("During iteration " + i + ":");
			System.out.println(game.printResults());
		}

	}
}
