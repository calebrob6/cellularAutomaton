package cellularAutomaton.GASmallTesting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.Population;
import org.jgap.audit.EvolutionMonitor;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

import cellularAutomaton.HutterPrize.HutterSearch;
import cellularAutomaton.HutterPrize.HutterSearchFitness;

public class GASmallTest {

	public static void main(String[] args) {

		try {
			
			if(args.length<3){
				System.out.println("Usage:./program width height initialRandomPercent");
				return;
			}
			int width = Integer.parseInt(args[0]);
			double initialRandomPercent = Double.parseDouble(args[0]);
			runExperiment(true, initialRandomPercent);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static final int MAX_ALLOWED_EVOLUTIONS = 100;

	public static final int NUM_RULE_GENES = 16;

	public static EvolutionMonitor m_monitor;

	public static void runExperiment(boolean a_doMonitor, double initialRandomPercent) throws Exception {

		Configuration conf = new DefaultConfiguration();
		conf.setPreservFittestIndividual(true);
		conf.setKeepPopulationSizeConstant(false);
		FitnessFunction myFunc = new GASmallFitness(initialRandomPercent);
		conf.setFitnessFunction(myFunc);
		if (a_doMonitor) {
			m_monitor = new EvolutionMonitor();
			conf.setMonitor(m_monitor);
		}
		
		
		Gene[] sampleGenes = new Gene[NUM_RULE_GENES+2];
		for(int i=0;i<NUM_RULE_GENES;i++){
			sampleGenes[i] = new IntegerGene(conf,0,15);
		}
		sampleGenes[NUM_RULE_GENES] = new IntegerGene(conf, 5, 15); // width
		sampleGenes[NUM_RULE_GENES+1] = new IntegerGene(conf, 5, 15); // height
		
		IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
		
		conf.setSampleChromosome(sampleChromosome);
		conf.setPopulationSize(50);
		
		Genotype population = Genotype.randomInitialGenotype(conf);
		System.out.println("About to get the fittest individual");
		double initialBestFitness = population.getFittestChromosome().getFitnessValue();
		System.out.println("Initial best fitness " + initialBestFitness);
		
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
			System.out.println((i+1)+"/"+MAX_ALLOWED_EVOLUTIONS);
			System.out.println("\t"+population.getFittestChromosome().getFitnessValue());
			if (!uniqueChromosomes(population.getPopulation())) {
				throw new RuntimeException("Invalid state in generation " + i);
			}
			if (m_monitor != null) {
				population.evolve(m_monitor);
			} else {
				population.evolve();
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Total evolution time: " + (endTime - startTime) + " ms");	
		
		IChromosome bestSolutionSoFar = population.getFittestChromosome();
		double finalBestFitness = bestSolutionSoFar.getFitnessValue();
		System.out.println("The best solution has a fitness value of " + finalBestFitness);
		System.out.println("Rule string: ");
		String resultString = "";
		for(int i=0;i<NUM_RULE_GENES;i++){
			System.out.print(((Integer)bestSolutionSoFar.getGene(i).getAllele()).intValue() + ";");
			resultString += ((Integer)bestSolutionSoFar.getGene(i).getAllele()).intValue() + ((i!=NUM_RULE_GENES-1)?";":"");
		}
		int gWidth = ((Integer) bestSolutionSoFar.getGene(NUM_RULE_GENES).getAllele());
		int gHeight = ((Integer) bestSolutionSoFar.getGene(NUM_RULE_GENES+1).getAllele());
		System.out.println("On a board size of: "+gWidth+ ", "+gHeight);
		
		try {
			File file = new File(System.currentTimeMillis()+"results.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(initialBestFitness+"\n");
			bw.write(finalBestFitness+"\n");
			bw.write(resultString+"\n");
			bw.write(gWidth+":"+gHeight);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static boolean uniqueChromosomes(Population a_pop) {
		for (int i = 0; i < a_pop.size() - 1; i++) {
			IChromosome c = a_pop.getChromosome(i);
			for (int j = i + 1; j < a_pop.size(); j++) {
				IChromosome c2 = a_pop.getChromosome(j);
				if (c == c2) {
					return false;
				}
			}
		}
		return true;
	}

}
