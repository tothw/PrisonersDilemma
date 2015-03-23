
public class PredictionCounter {

	public double correctPredictions;
	public double totalPredictions;
	
	public PredictionCounter(){
		correctPredictions = 0;
		totalPredictions = 0;
	}
	
	public double getAccuracy(){
		return correctPredictions/totalPredictions;
	}
	
}
