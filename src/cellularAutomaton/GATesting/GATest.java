package cellularAutomaton.GATesting;

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

public class GATest {

	public static void main(String[] args) {

		try {
			
			if(args.length<4){
				System.out.println("Usage:./program width height maxIterations initialRandomPercent");
				return;
			}
			
			int maxIterations = Integer.parseInt(args[2]);
			int width = Integer.parseInt(args[0]);
			int height = Integer.parseInt(args[1]);
			double initialRandomPercent = Double.parseDouble(args[3]);
			
			runExperiment(true, maxIterations, width, height, initialRandomPercent);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static final int MAX_ALLOWED_EVOLUTIONS = 50;

	public static final int NUM_RULE_GENES = 16;

	public static EvolutionMonitor m_monitor;

	public static void runExperiment(boolean a_doMonitor, int maxIterations, int width, int height, double initialRandomPercent) throws Exception {

		Configuration conf = new DefaultConfiguration();
		conf.setPreservFittestIndividual(true);
		conf.setKeepPopulationSizeConstant(false);
		FitnessFunction myFunc = new GAFitness(maxIterations, width, height, initialRandomPercent);
		conf.setFitnessFunction(myFunc);
		if (a_doMonitor) {
			m_monitor = new EvolutionMonitor();
			conf.setMonitor(m_monitor);
		}
		
		
		Gene[] sampleGenes = new Gene[16];
		for(int i=0;i<16;i++){
			sampleGenes[i] = new IntegerGene(conf,0,15);
		}
		//sampleGenes[16] = new IntegerGene(conf, 0, 10000); // Number of iterations
		//sampleGenes[17] = new IntegerGene(conf, 0, 50); // width
		//sampleGenes[18] = new IntegerGene(conf, 0, 50); // height
		
		IChromosome sampleChromosome = new Chromosome(conf, sampleGenes);
		
		conf.setSampleChromosome(sampleChromosome);
		conf.setPopulationSize(20);
		
		Genotype population = Genotype.randomInitialGenotype(conf);
		
		/*
		try {
			Document doc = XMLManager.readFile(new File("JGAPExample32.xml"));
			population = XMLManager.getGenotypeFromDocument(conf, doc);
		} catch (UnsupportedRepresentationException uex) {
			population = Genotype.randomInitialGenotype(conf);
		} catch (FileNotFoundException fex) {
			population = Genotype.randomInitialGenotype(conf);
		}
		*/
		

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
			System.out.println((i+1)+"/"+MAX_ALLOWED_EVOLUTIONS);
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
		
		/*
		DataTreeBuilder builder = DataTreeBuilder.getInstance();
		IDataCreators doc2 = builder.representGenotypeAsDocument(population);
		XMLDocumentBuilder docbuilder = new XMLDocumentBuilder();
		Document xmlDoc = (Document) docbuilder.buildDocument(doc2);
		XMLManager.writeFile(xmlDoc, new File("JGAPExample26.xml"));
		*/
		
		
		IChromosome bestSolutionSoFar = population.getFittestChromosome();
		double v1 = bestSolutionSoFar.getFitnessValue();
		System.out.println("The best solution has a fitness value of " + v1);
		//bestSolutionSoFar.setFitnessValueDirectly(-1);
		System.out.println("Rule string: ");
		for(int i=0;i<16;i++){
			System.out.print(((Integer)bestSolutionSoFar.getGene(i).getAllele()).intValue() + ";");
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
