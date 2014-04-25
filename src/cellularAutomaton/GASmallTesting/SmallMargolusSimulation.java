package cellularAutomaton.GASmallTesting;

import java.util.Arrays;
import java.util.HashMap;

import cellularAutomaton.Board;
import cellularAutomaton.ExperimentalResults;
import cellularAutomaton.Rules;

public class SmallMargolusSimulation {
	
	private int width = -1;
	private int height = -1;
	private String ruleString = null;
	private int numIterations = -1;
	
	private Rules rules;
	private Board board;
	
	private int cWidth;
	private int cHeight;
	
	/*
	 * unit testing right here dowork
	public static void main(String[] args) {
		SmallMargolusSimulation test = new SmallMargolusSimulation(10,10,4,4,"0;0;0;0;0;0;0;0;0;0;0;0;0;0;0;0");
		test.setCBoardState(65535);
		test.board.writeMapToImage("test.png");
	}
	*/
	
	public SmallMargolusSimulation(int width, int height, int cWidth, int cHeight, String ruleString){
		
		
		this.width = width;
		this.height = height;
		this.ruleString = ruleString;
		
		this.cWidth = cWidth;
		this.cHeight = cHeight;
		
		this.rules = new Rules(ruleString);
		this.board = new Board(width, height);
		
		this.numIterations = (int) Math.pow(2, height*width+1);
		
		this.board.clear();
	}
	
	private void setCBoardState(int stateNum){
		String stateVal = String.format("%"+this.cWidth*this.cHeight+"s", Integer.toBinaryString(stateNum)).replace(" ", "0");
		
		this.board.clear();
		int currentPos = 0;
		for(int i=0;i<this.cHeight;i++){
			for(int j=0;j<this.cWidth;j++){
				this.board.map[((width * ((i))) + ((j)))] = (stateVal.charAt(currentPos)=='1') ? true : false; 
				currentPos++;
			}
		}
	}
	
	public ExperimentalResults runExperiment(){
		ExperimentalResults experiment = new ExperimentalResults();

		
		HashMap<Integer, Boolean> cycleMap = new HashMap<Integer,Boolean>();
		int currentFrame = 0;
		int currentOffset = 0;

		
		int currentStateNum = 0;
		boolean cycleFound = false;
		int cycleLength = 0;
		
		long start = System.currentTimeMillis();

		
		while (currentFrame < numIterations && !cycleFound) {
			long startTime = System.currentTimeMillis();
			

			if (cycleMap.containsKey(Arrays.hashCode(this.board.map))) {
				
				if(currentStateNum == Math.pow(2, this.cWidth*this.cHeight)-1){
					cycleFound = true;
				}else{
					currentStateNum++;
					setCBoardState(currentStateNum);
					cycleMap.put(Arrays.hashCode(this.board.map), true); //better way to do this incase the state we are resetting to has been found
				}
			} else if (!cycleFound) {
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
		}
		
		
		experiment.timeTaken = System.currentTimeMillis() - start;
		experiment.numFrames = currentFrame;
		experiment.cycleLength = cycleLength;
		experiment.cycleFound = cycleFound;
		experiment.finalBoard = this.board;

		
		return experiment;
	}

}
