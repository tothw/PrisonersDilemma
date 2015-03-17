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

		Agent agents[] = { new AllC(), new AllD(), new Rand(), new TFT(),
				new TF2T(), new STFT() };

		for (int i = 0; i < agents.length; ++i) {
			for (int j = 0; j < agents.length; ++j) {
				playIPD(agents[i], agents[j]);
			}
		}
	}

	public static void playIPD(Agent player1, Agent player2) {

		System.out.println("Player1 will be " + player1.getName());
		System.out.println("Player 2 will be " + player2.getName());
		
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
