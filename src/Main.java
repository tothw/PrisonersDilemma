/**
 * 
 * @author tothw
 * 
 *         Main method controls the simulation
 */

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int memoryDepth = 3;
		Agent agents[] = { new AllC(memoryDepth), new AllD(memoryDepth), new Rand(memoryDepth), new TFT(memoryDepth),
				new TF2T(memoryDepth), new STFT(memoryDepth) };

		for (int i = 0; i < agents.length; ++i) {
			for (int j = i; j < agents.length; ++j) {
				playIPD(agents[i], agents[j]);
			}
		}
	}

	public static void playIPD(Agent player1, Agent player2) {

		System.out.println("Player1 will be " + player1.getName());
		System.out.println("With strategy string " + player1.printStrategy());
		System.out.println("Player 2 will be " + player2.getName());
		System.out.println("With strategy string " + player2.printStrategy() + "\n");
		
		player1.reset();
		player2.reset();
		
		Game game = new Game(player1, player2);

		int totalIterations = 10;

		for (int i = 1; i <= totalIterations; ++i) {
			game.play();

			System.out.println("During iteration " + i + ":");
			System.out.println(game.printResults());
		}

	}
}
