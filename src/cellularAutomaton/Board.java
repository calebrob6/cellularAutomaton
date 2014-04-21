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


	public Board(int width, int height) {
		this.width = width;
		this.height = height;

		this.map = new Boolean[width * height];
		for (int i = 0; i < map.length; i++) {
			this.map[i] = false;
		}
	}

	public void fillRandomPercent(double percent){
		long size = width * height;
		long numPixels = Math.round(size*(percent/100l));
	
		Random rand = new Random();

		for(int i=0;i<numPixels;i++){
			this.map[(width * rand.nextInt(height)) + (rand.nextInt(width))] = true;
		}
	}
	

	public void fillRandomConstrainedPercent(double percent,int areaDivisor){
		long size = width * height;
		long numPixels = Math.round(size*(percent/100l));
	
		Random rand = new Random();

		for(int i=0;i<numPixels;i++){
			this.map[(width * rand.nextInt(height/areaDivisor)) + (rand.nextInt(width/areaDivisor))] = true;
		}
	}
	
	public void writeMapToImage(String fn){
		BufferedImage image = getImageMap();
		
		try {
			ImageIO.write((RenderedImage) image, "PNG", new File(fn));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public BufferedImage getImageMap() {

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


		return bimage;
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
<<<<<<< HEAD
	
	public byte[] toByteArray(){
		byte[] resultFile = new byte[map.length/8];
		for(int i=0;i<resultFile.length;i++){
			byte rB = 0x00;
			rB |= (map[i*8] ? 1 : 0) << 7;
			rB |= (map[i*8+1] ? 1 : 0) << 6;
			rB |= (map[i*8+2] ? 1 : 0) << 5;
			rB |= (map[i*8+3] ? 1 : 0) << 4;
			rB |= (map[i*8+4] ? 1 : 0) << 3;
			rB |= (map[i*8+5] ? 1 : 0) << 2;
			rB |= (map[i*8+6] ? 1 : 0) << 1;
			rB |= (map[i*8+7] ? 1 : 0);
			resultFile[i] = rB;
		}
		return resultFile;
	}
=======
>>>>>>> e03bf1ff7499434082d4a90bc30392f25de5ccd5

}