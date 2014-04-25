package cellularAutomaton.HutterPrize;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

import cellularAutomaton.ExperimentalResults;
import cellularAutomaton.MargolusSimulation;

public class HutterSearchFitness extends FitnessFunction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final int width;
	public final int height;
	public final double initialRandomPercent;
	public int numIterations;

	public static byte[] buffer;

	public static void loadHutterFile(String fn) {

		try {
			File file = new File(fn);
			FileInputStream fis = new FileInputStream(file);
			buffer = new byte[(int) file.length()];
			fis.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HutterSearchFitness(int width, int height, double initialRandomPercent) {
		this.width = width;
		this.height = height;
		this.initialRandomPercent = initialRandomPercent;
	}

	public double evaluate(IChromosome a_subject) {

		// higher fitness is better

		double fitness = 800000000d;
		
		String rule = "";
		for (int i = 0; i < HutterSearch.NUM_RULE_GENES; i++) {
			rule += ((Integer) a_subject.getGene(i).getAllele()).intValue() + ((i != HutterSearch.NUM_RULE_GENES - 1) ? ";" : "");
		}
		numIterations = ((Integer) a_subject.getGene(HutterSearch.NUM_RULE_GENES).getAllele());
		
		System.out.println("Testing individual with rule: "+rule);
		System.out.println("\tand numIterations: "+numIterations);
		System.out.println("Running simulation...");
		MargolusSimulation sim = new MargolusSimulation(width, height, rule, numIterations, false);
		sim.setInitialConstrainedRandom(initialRandomPercent,5);
		ExperimentalResults result = sim.runExperiment();
		System.out.println("Calculating fitness...");
		for(int i=0;i<buffer.length;i++){

			fitness -=  (((buffer[i] & 0X80) != 0) == result.finalBoard.map[i*8]) ? 0 : 1;
			fitness -=  (((buffer[i] & 0X40) != 0) == result.finalBoard.map[i*8+1]) ? 0 : 1;
			fitness -=  (((buffer[i] & 0X20) != 0) == result.finalBoard.map[i*8+2]) ? 0 : 1;
			fitness -=  (((buffer[i] & 0X10) != 0) == result.finalBoard.map[i*8+3]) ? 0 : 1;
			fitness -=  (((buffer[i] & 0X08) != 0) == result.finalBoard.map[i*8+4]) ? 0 : 1;
			fitness -=  (((buffer[i] & 0X04) != 0) == result.finalBoard.map[i*8+5]) ? 0 : 1;
			fitness -=  (((buffer[i] & 0X02) != 0) == result.finalBoard.map[i*8+6]) ? 0 : 1;
			fitness -=  (((buffer[i] & 0X01) != 0) == result.finalBoard.map[i*8+7]) ? 0 : 1;
			
		}

		return fitness;
	}

}
