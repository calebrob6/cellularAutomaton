package cellularAutomaton;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.MemoryImageSource;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Board {

	public int width = 0;
	public int height = 0;
	public Boolean[] map;

	private boolean currentOffset = false;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;

		this.map = new Boolean[width * height];
		for (int i = 0; i < map.length; i++) {
			this.map[i] = false;
		}
	}

	public void fillRandomPercent(Double percent){
		long size = width * height;
		long numPixels = Math.round(size*(percent/100l));
	
		Random rand = new Random();

		for(int i=0;i<numPixels;i++){
			this.map[(width * rand.nextInt(height)) + (rand.nextInt(width))] = true;
		}
		
	}
	
	public boolean writeMapToImage(String filename) {

		// BufferedImage image = new
		// BufferedImage(width,this.height,BufferedImage.TYPE_BYTE_GRAY);
		// Graphics g = image.getGraphics();

		DirectColorModel cm = (DirectColorModel) ColorModel.getRGBdefault();

		int[] pixels = new int[this.map.length];

		for (int i = 0; i < pixels.length; i++) {
			if (this.map[i]) {
				pixels[i] = 0XFF000000;
			}else{
				pixels[i] = 0XFFFFFFFF;
			}
		}

		MemoryImageSource source = new MemoryImageSource(this.width, this.height, cm, pixels, 0, this.width);
		Image image = Toolkit.getDefaultToolkit().createImage(source);
		BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(image, 0, 0, null);
		bGr.dispose();

		try {
			ImageIO.write((RenderedImage) bimage, "PNG", new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}
	
	public double calculateEntropy(){
		/*
		double returnVal = 0;
		for(int i=0;i<map.length;i++){
			returnVal+=(map[i]) ? Math.log(255) : 0;
		}
		return returnVal;
		*/
		return 0;
	}

	public void fillMiddleSquare(int l) {
		int middleX = width/2;
		int middleY = height/2;
		
		for(int y=middleY-l/2;y<middleY+l/2;y++){
			for(int x=middleX-l/2;x<middleX+l/2;x++){
				this.map[(width * y) + (x)] = true;
			}
		}
	}
	
	public void fillTopLeftSquare(int l) {
		int middleX = l/2;
		int middleY = l/2;
		
		for(int y=middleY-l/2;y<middleY+l/2;y++){
			for(int x=middleX-l/2;x<middleX+l/2;x++){
				this.map[(width * y) + (x)] = true;
			}
		}
	}

	public void clear() {
		for(int i=0;i<this.map.length;i++){
			this.map[i] = false;
		}
	}

}