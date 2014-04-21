package cellularAutomaton;

import java.util.Arrays;
import java.util.HashMap;

public class MargolusSimulation {
	
	private int width = -1;
	private int height = -1;
	private String ruleString = null;
	private int numIterations = -1;
	
	private Rules rules;
	private Board board;
	private double initialRandomPercent;
	
	public MargolusSimulation(int width, int height, String ruleString, int numIterations){
		
		
		this.width = width;
		this.height = height;
		this.ruleString = ruleString;
		this.numIterations = numIterations;
		
		this.rules = new Rules(ruleString);
		this.board = new Board(width, height);
		
		this.initialRandomPercent = (double) 0;
		
	}
	
	public void setInitialRandom(double percent){
		this.initialRandomPercent = percent;
		this.board.clear();
		this.board.fillRandomPercent(percent);

	}
	
	public void setInitialConstrainedRandom(double percent, int areaDivisor) {
		this.initialRandomPercent = percent;
		this.board.clear();
		this.board.fillRandomConstrainedPercent(percent, areaDivisor);
	}
	
	public ExperimentalResults runExperiment(){
		ExperimentalResults experiment = new ExperimentalResults();
		
		HashMap<Integer, Boolean> cycleMap = new HashMap<Integer,Boolean>();
		int currentFrame = 0;
		int currentOffset = 0;

		boolean cycleFound = false;
		int cycleLength = 0;
		
		long start = System.currentTimeMillis();

		while (currentFrame < numIterations) {
			
			long startTime = System.currentTimeMillis();
			
			
			if(cycleMap.containsKey(Arrays.hashCode(this.board.map))){
				cycleFound = true;
				cycleMap.put(Arrays.hashCode(this.board.map), true);
			}else if(!cycleFound){
				cycleMap.put(Arrays.hashCode(this.board.map), true);
				cycleLength++;
			}
				


			long start1 = System.currentTimeMillis();
			for (int i = 0; i < height; i += 2) {
				for (int j = 0; j < width; j += 2) {
					int x = j + currentOffset;
					int y = i + currentOffset;

					int a1 = ((width * ((y) % height)) + ((x) % width));
					int a2 = ((width * ((y) % height)) + ((x + 1) % width));
					int a3 = ((width * ((y + 1) % height)) + ((x) % width));
					int a4 = ((width * ((y + 1) % height)) + ((x + 1) % width));

					byte c1 = (byte) ((this.board.map[a1] == true) ? 0X08 : 0X00);
					byte c2 = (byte) ((this.board.map[a2] == true) ? 0X04 : 0X00);
					byte c3 = (byte) ((this.board.map[a3] == true) ? 0X02 : 0X00);
					byte c4 = (byte) ((this.board.map[a4] == true) ? 0X01 : 0X00);

					//Integer[] ruleIndex = new Integer[] { c1, c2, c3, c4 };
					//int cellIndex = rules.configRuleToIdx.get("" + c1 + c2 + c3 + c4);
					
					Byte temp = (byte) (0X00 | c1 | c2 | c3 | c4);
					int cellIndex = rules.configRuleToIdx.get(temp);

					int transitionIndex = rules.rules[cellIndex];
					Byte transitionConfigTemp = rules.configIdxToRule.get(transitionIndex);

					this.board.map[a1] = (transitionConfigTemp & 0X08) == 0X08;
					this.board.map[a2] = (transitionConfigTemp & 0X04) == 0X04;
					this.board.map[a3] = (transitionConfigTemp & 0X02) == 0X02;
					this.board.map[a4] = (transitionConfigTemp & 0X01) == 0X01;

				}
			}
			long end1 = System.currentTimeMillis();
			long diff1 = end1 - start1;


			currentOffset = (currentOffset == 0) ? 1 : 0;

			long end = System.currentTimeMillis();
			long diff = end - startTime;

			currentFrame++;
			//System.out.println(currentFrame + " " + diff1 + "/" + diff);
		}
		
		experiment.timeTaken = System.currentTimeMillis() - start;
		experiment.numFrames = currentFrame;
		experiment.cycleLength = cycleLength;
		experiment.cycleFound = cycleFound;
		experiment.initialRandomPercent = initialRandomPercent;
		experiment.finalBoard = this.board;

		
		return experiment;
	}

}
