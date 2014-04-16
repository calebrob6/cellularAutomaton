package cellularAutomaton.GATesting;

import java.io.File;
import java.io.FileNotFoundException;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.Population;
import org.jgap.UnsupportedRepresentationException;
import org.jgap.audit.EvolutionMonitor;
import org.jgap.data.DataTreeBuilder;
import org.jgap.data.IDataCreators;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.jgap.xml.XMLDocumentBuilder;
import org.jgap.xml.XMLManager;
import org.w3c.dom.Document;

public class GATest {

	public static void main(String[] args) {

		try {
			makeChangeForAmount(251, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static final int MAX_ALLOWED_EVOLUTIONS = 50;

	public static EvolutionMonitor m_monitor;

	public static void makeChangeForAmount(int a_targetChangeAmount, boolean a_doMonitor) throws Exception {

		Configuration conf = new DefaultConfiguration();
		conf.setPreservFittestIndividual(true);
		conf.setKeepPopulationSizeConstant(false);
		FitnessFunction myFunc = new GAFitness(a_targetChangeAmount);
		conf.setFitnessFunction(myFunc);
		if (a_doMonitor) {
			m_monitor = new EvolutionMonitor();
			conf.setMonitor(m_monitor);
		}
		
		// Now we need to tell the Configuration object how we want our
		// Chromosomes to be setup. We do that by actually creating a
		// sample Chromosome and then setting it on the Configuration
		// object. As mentioned earlier, we want our Chromosomes to each
		// have four genes, one for each of the coin types. We want the
		// values (alleles) of those genes to be integers, which represent
		// how many coins of that type we have. We therefore use the
		// IntegerGene class to represent each of the genes. That class
		// also lets us specify a lower and upper bound, which we set
		// to sensible values for each coin type.
		// --------------------------------------------------------------
		
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
		// Save progress to file. A new run of this example will then be able to
		// resume where it stopped before! --> this is completely optional.
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
			System.out.println(((Integer)bestSolutionSoFar.getGene(i).getAllele()).intValue() + ";");
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
