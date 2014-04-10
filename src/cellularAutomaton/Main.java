package cellularAutomaton;

public class Main {
	
	

	public static void main(String[] args) {

		int width = 200;
		int height = 200;
		
		boolean stopFlickering = false;
		Board currentState = new Board(200,200);
		
		int currentFrame = 0;
		int currentOffset = 0;
		
		long startTime = System.currentTimeMillis();
			
			if(stopFlickering){
				if(Math.abs(currentFrame%2)==0){
					//currentState = context.getImageData(0, 0, width, height).data;
				}else{
					//currentState = oldData
				}
			}else{
				//currentState = context.getImageData(0, 0, width, height).data;
			}

			long start1 = System.currentTimeMillis(); 
			for(int i=0;i<height;i+=2){
				for(int j=0;j<width;j+=2){
					int x = j+currentOffset;
					int y = i+currentOffset;
					
					int a1 = ((width * ((y)%height)) + ((x)%width)) * 4;
					int a2 = ((width * ((y)%height)) + ((x+1)%width)) * 4;
					int a3 = ((width * ((y+1)%height)) + ((x)%width)) * 4;
					int a4 = ((width * ((y+1)%height)) + ((x+1)%width)) * 4;
					
					boolean c1 = (currentState.map[a1]==true); 
					boolean c2 = (currentState.map[a2]==true);
					boolean c3 = (currentState.map[a3]==true);
					boolean c4 = (currentState.map[a4]==true);

					
					cellIndex = configRuleToIdx[[c1,c2,c3,c4]];

					
					transitionIndex = rules[cellIndex];
					transitionConfig = configIdxToRule[transitionIndex] //this is our transition function
					
					newState.data[a1] = transitionConfig[0]*255;
					newState.data[a2] = transitionConfig[1]*255;
					newState.data[a3] = transitionConfig[2]*255;
					newState.data[a4] = transitionConfig[3]*255;
					
					
					
					newState.data[a1+3] = 255;
					newState.data[a2+3] = 255;
					newState.data[a3+3] = 255;
					newState.data[a4+3] = 255;
					
				}
			}
			long end1 =  System.currentTimeMillis();
			long diff1 = end1 - start1;
			
			
			if(stopFlickering){
				if(Math.abs(currentFrame%2)==1){
					//context.putImageData(newState,0,0);
				}else{
					//oldData = newState.data
				}
			}else{
				//context.putImageData(newState,0,0);
			}
			
			updateFrameCount();
			
			addGraphPoint(countOfPixels()/(height*width))
			
			//Switch offset 0->1 or 1->0
			
			currentOffset = !currentOffset
			
			
			var end =  +new Date();
			var diff = end - start;
			print(currentFrame + " " + diff1 + "/" + diff)
			print(rules);
			if(step){
			
				if(backwards){
					backwards = false;
					reverseRules();
				}
			
				running = false;
				step = false;
			}
			
			
		
		
		

	}

}
