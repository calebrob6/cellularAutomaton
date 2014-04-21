package cellularAutomaton.GATesting;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import cellularAutomaton.ExperimentalResults;
import cellularAutomaton.MargolusSimulation;

public class GAFitness extends FitnessFunction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final int width;
	public final int height;
	public final double initialRandomPercent;
	public final int numIterations;

	public GAFitness(int maxIterations, int width, int height, double initialRandomPercent) {
		this.width = width;
		this.height = height;
		this.initialRandomPercent = initialRandomPercent;
		this.numIterations = maxIterations;
	}

	
	public double evaluate(IChromosome a_subject) {
		
		//higher fitness is better
		
		double fitness = 1.0d;
		String rule = "";
		for(int i=0;i<GATest.NUM_RULE_GENES;i++){
			rule += ((Integer)a_subject.getGene(i).getAllele()).intValue() + ((i!=GATest.NUM_RULE_GENES-1)?";":"");
		}
		
		MargolusSimulation sim = new MargolusSimulation(width, height, rule, numIterations);
<<<<<<< HEAD
		sim.setInitialConstrainedRandom(initialRandomPercent,4);
=======
		sim.setInitialConstrainedRandom(initialRandomPercent);
>>>>>>> e03bf1ff7499434082d4a90bc30392f25de5ccd5
		ExperimentalResults result = sim.runExperiment();
		
		fitness = result.cycleLength;
		
		return Math.max(1.0d, fitness);
	}


}
