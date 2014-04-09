package cellularAutomaton;

public class Board {
	
	public int width = 0;
	public int height = 0;
	public boolean[] map;
	
	private boolean currentOffset = false;
	
	public Board(int width, int height){
		this.width = width;
		this.height = height;
		
		this.map = new boolean[width*height];
		for(int i=0;i<map.length;i++){
			this.map[i] = false;
		}
	}
	

}
