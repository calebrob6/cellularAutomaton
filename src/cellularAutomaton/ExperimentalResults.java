package cellularAutomaton;

public class ExperimentalResults {
	
	public int cycleLength;
	public long timeTaken;
	
	public int numFrames;
	public boolean cycleFound;
	public double initialRandomPercent;
	
	public ExperimentalResults(){
		
	}
	
	@Override
	public String toString(){
		String retString = "";
		retString += "Cycle found: "+cycleFound+"\n";
		if(cycleFound){
			retString += "Length of cycle: "+cycleLength+"\n";
		}
		retString += "Number of frames: "+numFrames+"\n";
		retString += "Time taken(seconds): "+timeTaken/1000l+"\n";
		retString += "Average time per frame(seconds): "+((double)(timeTaken/1000l))/((double) numFrames)+"\n";
		retString += "Initital Random Percent: "+initialRandomPercent+"%\n";
		return retString;
	}
	
	public String toDelimitedRow(String separator){
		String retString = "";
		retString+=numFrames+separator+timeTaken+separator+cycleFound+separator+(cycleFound?cycleLength:"")+separator+initialRandomPercent;
		return retString;
	}

}
