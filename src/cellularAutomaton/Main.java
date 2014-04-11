package cellularAutomaton;

public class Main {

	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		
		if(args.length<3){
			System.out.println("Usage:./thisProgram width height 'ruleString'");
			return;
		}
		
		int width = Integer.parseInt(args[0]);
		int height = Integer.parseInt(args[1]);
		
		
		boolean useRuleString = false;
		int ruleStringIndex = -1;
		String ruleString = "";
		
		//find -c
		for(int i=0;i<args.length;i++){
			if(args[i].equals("-c")){
				useRuleString = true;
				ruleString = args[i+1];
				break;
			}
		}
		
		if(!useRuleString){
			ruleStringIndex = Integer.parseInt(args[2]);
		}
		
		boolean stopFlickering = false;
		Board currentState = new Board(width, height);
		Rules rules = new Rules();
		
		if(useRuleString){
			rules.setRules(ruleString);
		}else{
			rules.setRules(Rules.ruleSets[ruleStringIndex]);
		}
		
		
		currentState.fillRandomPercent(1);
		currentState.fillMiddleSquare(10);
		
		currentState.writeMapToImage("initial.png");
		//System.out.println(currentState.calculateEntropy());

		int frameAbs = 0;
		int currentFrame = 0;
		int currentOffset = 0;
		boolean backwards = false;

		boolean running = true;

		while (currentFrame < 1000) {
			long startTime = System.currentTimeMillis();
			
			if(currentFrame%100==0){
				currentState.writeMapToImage("state"+currentFrame+".png");
			}

			if (stopFlickering) {
				if (Math.abs(currentFrame % 2) == 0) {
					// currentState = context.getImageData(0, 0, width,
					// height).data;
				} else {
					// currentState = oldData
				}
			} else {
				// currentState = context.getImageData(0, 0, width,
				// height).data;
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

					byte c1 = (byte) ((currentState.map[a1] == true) ? 0X08 : 0X00);
					byte c2 = (byte) ((currentState.map[a2] == true) ? 0X04 : 0X00);
					byte c3 = (byte) ((currentState.map[a3] == true) ? 0X02 : 0X00);
					byte c4 = (byte) ((currentState.map[a4] == true) ? 0X01 : 0X00);

					//Integer[] ruleIndex = new Integer[] { c1, c2, c3, c4 };
					//int cellIndex = rules.configRuleToIdx.get("" + c1 + c2 + c3 + c4);
					
					Byte temp = (byte) (0X00 | c1 | c2 | c3 | c4);
					int cellIndex = rules.configRuleToIdx.get(temp);

					int transitionIndex = rules.rules[cellIndex];
					Byte transitionConfigTemp = rules.configIdxToRule.get(transitionIndex);

					currentState.map[a1] = (transitionConfigTemp & 0X08) == 0X08;
					currentState.map[a2] = (transitionConfigTemp & 0X04) == 0X04;
					currentState.map[a3] = (transitionConfigTemp & 0X02) == 0X02;
					currentState.map[a4] = (transitionConfigTemp & 0X01) == 0X01;

				}
			}
			long end1 = System.currentTimeMillis();
			long diff1 = end1 - start1;

			if (stopFlickering) {
				if (Math.abs(currentFrame % 2) == 1) {
					// context.putImageData(newState,0,0);
				} else {
					// oldData = newState.data
				}
			} else {
				// context.putImageData(newState,0,0);
			}

			if (backwards) {
				currentFrame--;
			} else {
				currentFrame++;
			}
			frameAbs++;

			currentOffset = (currentOffset == 0) ? 1 : 0;

			long end = System.currentTimeMillis();
			long diff = end - startTime;

			System.out.println(currentFrame + " " + diff1 + "/" + diff);
		}

		currentState.writeMapToImage("final.png");
		//System.out.println(currentState.calculateEntropy());
		System.out.println(String.format("Done in %d seconds",System.currentTimeMillis()-start));
	}

	private static void updateFrameCount() {
		// TODO Auto-generated method stub

	}
}