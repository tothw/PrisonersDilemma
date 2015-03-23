import java.io.File;

import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class Learn extends Agent {

	NaiveBayesUpdateable classifier;
	PredictionCounter predictionCounter;
	
	ArffLoader loader;
	 
	public Learn(int memoryDepth, PredictionCounter predictionCounter) throws Exception {
		super(memoryDepth);
		this.predictionCounter = predictionCounter;
	}

	public int makePrediction(int[] state) throws Exception{
		Instance inst = new DenseInstance(7);
		
		if(memory[0] > 1)
			inst.setValue(0, 1);
		else
			inst.setValue(0, 0);
		if(memory[1] > 1)
			inst.setValue(1, 1);
		else
			inst.setValue(1, 0);
		if(memory[2] > 1)
			inst.setValue(2, 1);
		else
			inst.setValue(2, 0);
		inst.setValue(3, memory[0] % 2);
		inst.setValue(4, memory[1] % 2);
		inst.setValue(5, memory[2] % 2);
		

			Instances structure = loader.getStructure();
			structure.setClassIndex(structure.numAttributes() - 1);
			structure.add(inst);
			structure.setClassIndex(structure.numAttributes() - 1);
			return (int) classifier.classifyInstance(structure.firstInstance());
	}
	
	public int makeChoice() {
		++turn;
		int choice = 0;
		if(turn>30){
			try {
				choice = makePrediction(memory);
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
					return turn%2;
				}
			}
		}
		strategy[computeStrategyIndex()] = choice;
		return choice;
	}

	public void giveResult(Result result){
		learnFrom(result);
		if(turn > 30){
			if(result.situationCode == 0 || result.situationCode == 3)
				predictionCounter.correctPredictions += 1;
			predictionCounter.totalPredictions +=1;
		}
		super.giveResult(result);
	}
	
	public void learnFrom(Result result){
		Instance inst = new DenseInstance(7);
		if(memory[0] > 1)
			inst.setValue(0, 1);
		else
			inst.setValue(0, 0);
		if(memory[1] > 1)
			inst.setValue(1, 1);
		else
			inst.setValue(1, 0);
		if(memory[2] > 1)
			inst.setValue(2, 1);
		else
			inst.setValue(2, 0);
		inst.setValue(3, memory[0] % 2);
		inst.setValue(4, memory[1] % 2);
		inst.setValue(5, memory[2] % 2);
		inst.setValue(6, result.getSituationCode() % 2);
		
		try {
			Instances structure = loader.getStructure();
			structure.setClassIndex(structure.numAttributes() - 1);
			structure.add(inst);
			structure.setClassIndex(structure.numAttributes() - 1);
			classifier.updateClassifier(structure.firstInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// resets Agent state
	public void reset() throws Exception {
		super.reset();
		classifier = new NaiveBayesUpdateable();
		classifier.setOptions(weka.core.Utils.splitOptions(""));
		// Load structure of data
		loader = new ArffLoader();
		loader.setFile(new File("structure.arff"));
		Instances structure = loader.getStructure();
		structure.setClassIndex(structure.numAttributes() - 1);
		classifier.buildClassifier(structure);
		// train nb
		Instance current;
		while ((current = loader.getNextInstance(structure)) != null) {
			classifier.updateClassifier(current);
		}
	}

	@Override
	public void establishPremises() {
		for (int i = 0; i < premises.length; ++i) {
			premises[i] = 0;// assume cooperation
		}
	}

	@Override
	public void createPlan() {
		for (int i = 0; i < strategy.length; ++i) {
			strategy[i] = 0;// always cooperates at start
		}
	}

	@Override
	public String getName() {
		return "Learn (online learning agent)";
	}

}
