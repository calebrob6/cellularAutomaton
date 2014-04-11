package cellularAutomaton;

public class Main {

	public static void main(String[] args) {

		int width = 500;
		int height = 500;

		boolean stopFlickering = false;
		Board currentState = new Board(width, height);
		Rules rules = new Rules();
		rules.setRules(Rules.ruleSets[1]);
		currentState.fillRandomPercent(10);
		currentState.writeMapToImage("initial.png");
		System.out.println(currentState.calculateEntropy());

		int frameAbs = 0;
		int currentFrame = 0;
		int currentOffset = 0;
		boolean backwards = false;

		boolean running = true;

		while (currentFrame < 1000) {
			long startTime = System.currentTimeMillis();

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

					int c1 = (currentState.map[a1] == true) ? 1 : 0;
					int c2 = (currentState.map[a2] == true) ? 1 : 0;
					int c3 = (currentState.map[a3] == true) ? 1 : 0;
					int c4 = (currentState.map[a4] == true) ? 1 : 0;

					Integer[] ruleIndex = new Integer[] { c1, c2, c3, c4 };

					int cellIndex = rules.configRuleToIdx.get("" + c1 + c2 + c3 + c4);

					int transitionIndex = rules.rules[cellIndex];
					String transitionConfigTemp = rules.configIdxToRule.get(transitionIndex);

					currentState.map[a1] = transitionConfigTemp.charAt(0) == '1';
					currentState.map[a2] = transitionConfigTemp.charAt(1) == '1';
					currentState.map[a3] = transitionConfigTemp.charAt(2) == '1';
					currentState.map[a4] = transitionConfigTemp.charAt(3) == '1';

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

			//System.out.println(currentFrame + " " + diff1 + "/" + diff);
		}

		currentState.writeMapToImage("final.png");
		System.out.println(currentState.calculateEntropy());
	}

	private static void updateFrameCount() {
		// TODO Auto-generated method stub

	}
}