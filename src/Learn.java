import java.io.File;

import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.trees.HoeffdingTree;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class Learn extends Agent {

	NaiveBayesUpdateable classifier;

	public Learn(int memoryDepth) throws Exception {
		super(memoryDepth);
	}

	public int makeChoice() {
		++turn;
		int choice = 0;
		if(turn>30){
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
			
			
			try {
				ArffLoader loader = new ArffLoader();
				loader.setFile(new File("structure.arff"));
				Instances structure = loader.getStructure();
				structure.setClassIndex(structure.numAttributes() - 1);
				structure.add(inst);
				structure.setClassIndex(structure.numAttributes() - 1);
				choice = (int) classifier.classifyInstance(structure.firstInstance());
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

	public void giveResult(Result result){
		learnFrom(result);
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
			ArffLoader loader = new ArffLoader();
			loader.setFile(new File("structure.arff"));
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
		ArffLoader loader = new ArffLoader();
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
