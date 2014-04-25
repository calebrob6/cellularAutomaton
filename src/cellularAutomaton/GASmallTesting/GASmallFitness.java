package cellularAutomaton.GASmallTesting;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import cellularAutomaton.ExperimentalResults;
import cellularAutomaton.MargolusSimulation;
import cellularAutomaton.HutterPrize.HutterSearch;

public class GASmallFitness extends FitnessFunction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int width;
	public int height;
	public final double initialRandomPercent;
	public int numIterations;

	public GASmallFitness(double initialRandomPercent) {
		this.initialRandomPercent = initialRandomPercent;
	}

	
	public double evaluate(IChromosome a_subject) {
		
		//higher fitness is better
		
		double fitness = 1.0d;
		String rule = "";
		for(int i=0;i<GASmallTest.NUM_RULE_GENES;i++){
			rule += ((Integer)a_subject.getGene(i).getAllele()).intValue() + ((i!=GASmallTest.NUM_RULE_GENES-1)?";":"");
		}
		width = ((Integer) a_subject.getGene(GASmallTest.NUM_RULE_GENES).getAllele());
		height = ((Integer) a_subject.getGene(GASmallTest.NUM_RULE_GENES+1).getAllele());
		
		//System.out.println("Testing individual with rule: "+rule);
		//System.out.println("\tand width,height: "+width+","+height);
		//System.out.println("Running simulation...");
		
		MargolusSimulation sim = new MargolusSimulation(width, height, rule, (int) Math.pow(2, width*height), true);
		sim.setInitialRandom(initialRandomPercent);
		ExperimentalResults result = sim.runExperiment();
		
		//fitness = ((double)result.cycleLength)/Math.pow(2, width*height);
		fitness = result.cycleLength;
		//System.out.println(fitness);
		
		return fitness;
	}


}
