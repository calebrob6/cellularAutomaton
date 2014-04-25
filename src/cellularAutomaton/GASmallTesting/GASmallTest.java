package cellularAutomaton.GASmallTesting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

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

import cellularAutomaton.ExperimentalResults;

public class GASmallTest {

	public static void main(String[] args) {

		
		try {
			
			if(args.length<4){
				System.out.println("Usage:./thisProgram width height cWidth cHeight outputFn");
				return;
			}
			
			
			int width = Integer.parseInt(args[0]);
			int height = Integer.parseInt(args[1]);
			int cWidth = Integer.parseInt(args[2]);
			int cHeight = Integer.parseInt(args[3]);
			String outFn = args[4];
			
			Random rand = new Random();
			int maxCycle = 0;
			String maxRule = "";
			for(long i=0;i<1000000000;i++){
				if(i%10000==0){
					System.out.println(i);
				}
				String result = "";
				for(int j=0;j<16;j++){
					result+= Integer.toString(rand.nextInt(16))+((j!=15)?";":"");
				}
				SmallMargolusSimulation sim = new SmallMargolusSimulation(width, height, cWidth, cHeight, result);
				ExperimentalResults results = sim.runExperiment();
				if(results.cycleLength>maxCycle){
					maxCycle = results.cycleLength;
					maxRule = result;
					System.out.println(maxCycle+ "  -  "+maxRule);
					try {
					    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outFn, true)));
					    out.println(maxCycle+ "  -  "+maxRule);
					    out.close();
					} catch (IOException e) {
					    e.printStackTrace();
					}
				}
			}
			
			
			//runExperiment(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static final int MAX_ALLOWED_EVOLUTIONS = 50;

	public static final int NUM_RULE_GENES = 16;

	public static EvolutionMonitor m_monitor;

	public static void runExperiment(boolean a_doMonitor) throws Exception {

		Configuration conf = new DefaultConfiguration();
		conf.setPreservFittestIndividual(true);
		conf.setKeepPopulationSizeConstant(false);
		FitnessFunction myFunc = new GASmallFitness();
		conf.setFitnessFunction(myFunc);
		if (a_doMonitor) {
			m_monitor = new EvolutionMonitor();
			conf.setMonitor(m_monitor);
		}
		
		
		Gene[] sampleGenes = new Gene[NUM_RULE_GENES];
		for(int i=0;i<NUM_RULE_GENES;i++){
			sampleGenes[i] = new IntegerGene(conf,0,15);
		}

		
		IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
		
		conf.setSampleChromosome(sampleChromosome);
		conf.setPopulationSize(100);
		
		Genotype population = Genotype.randomInitialGenotype(conf);
		double initialBestFitness = population.getFittestChromosome().getFitnessValue();
		
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
