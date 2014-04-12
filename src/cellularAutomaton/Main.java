package cellularAutomaton;


public class Main {
	
	public static void main(String[] args) {

		
		
		if(args.length<4){
			System.out.println("Usage:./thisProgram width height 'ruleString'");
			return;
		}
		
		
		int width = Integer.parseInt(args[0]);
		int height = Integer.parseInt(args[1]);
		int ruleIndex = Integer.parseInt(args[2]);
		int numCycles = Integer.parseInt(args[3]);
		
		MargolusSimulation sim = new MargolusSimulation(width, height, Rules.ruleSets[ruleIndex], numCycles);
		sim.setInitialRandom(1);
		ExperimentalResults results = sim.runExperiment();
		System.out.println(results);
		
		
	}


}