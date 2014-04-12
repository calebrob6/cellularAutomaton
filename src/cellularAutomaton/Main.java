package cellularAutomaton;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Main {
	
	public static void main(String[] args) {
		
		if(args.length<4){
			System.out.println("Usage:./thisProgram width height ruleStringIndex numIterations initialRandomPercent outputFn");
			return;
		}
		
		
		int width = Integer.parseInt(args[0]);
		int height = Integer.parseInt(args[1]);
		int ruleIndex = Integer.parseInt(args[2]);
		int numIterations = Integer.parseInt(args[3]);
		Double initialRandomPercent = Double.parseDouble(args[4]);
		
		String resultsFn = args[5];
		
		int numExperiments = 500;
		
		ArrayList<ExperimentalResults> results = new ArrayList<ExperimentalResults>();
		
		for(int i=0;i<numExperiments;i++){
			System.out.println(i+"/"+numExperiments);
			MargolusSimulation sim = new MargolusSimulation(width, height, Rules.ruleSets[ruleIndex], numIterations);
			sim.setInitialRandom(initialRandomPercent);
			ExperimentalResults result = sim.runExperiment();
			
			results.add(result);	
		}
		
		
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(resultsFn, true)));
			for (ExperimentalResults r : results) {
				out.println(r.toDelimitedRow(","));
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		int numExperimentsWithCycles = 0;
		int averageCycleLength = 0;
		for(ExperimentalResults r:results){
			if(r.cycleFound){
				//System.out.println(r.cycleLength);
				numExperimentsWithCycles++;
				averageCycleLength+=r.cycleLength;
			}
		}
		
		System.out.println("Width: "+width);
		System.out.println("Height: "+height);
		System.out.println("Number of iterations: "+numIterations);
		System.out.println("Initial random percent: "+initialRandomPercent);
		System.out.println("--------------------------------------------------");
		System.out.println(numExperimentsWithCycles+"/"+numExperiments);
		if(numExperimentsWithCycles!=0) System.out.println("Average cycle length: " + ((double) averageCycleLength)/((double) numExperimentsWithCycles));
		*/
		
	}


}