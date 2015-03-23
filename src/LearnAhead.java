public class LearnAhead extends Learn{

	public LearnAhead(int memoryDepth) throws Exception {
		super(memoryDepth);
	}
	
	int lookAhead() throws Exception{
		int m = makePrediction(memory);
		int d = 2 + m;
	
		int state[] = new int[3];
		state[1] = memory[0];
		state[2] = memory[1]; 
	
		//If I cooperate on next move
		state[0] = m;
		int m0 = makePrediction(state);
		if(m0 == 0) return 0;
		//If I defect on next move
		state[0] = d;
		int m1 = makePrediction(state);
		if(m1 == 0) return 1;
		return m;
	
		
	}
	
	
	public int makeChoice() {
		++turn;
		int choice = 0;
		if(turn>30){
			try {
				choice = lookAhead();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			if(turn <=  10){
				choice = 0;
			}else{
				if(turn <= 20){
					choice = 1;
				}else{
					//alternate
					if(memory[0] > 1)
						choice = 0;
					else
						choice = 1;
				}
			}
		}
		strategy[computeStrategyIndex()] = choice;
		return choice;
	}

	public String getName(){
		return "LearnAhead (learning agent with 1 move lookahead)";
	}
	
}
