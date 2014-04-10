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
	public boolean[] map;

	private boolean currentOffset = false;

	public Board(int width, int height) {
		this.width = width;
		this.height = height;

		this.map = new boolean[width * height];
		for (int i = 0; i < map.length; i++) {
			this.map[i] = false;
		}
	}

	public Board(int width, int height, int numRandomOn) {
		this.width = width;
		this.height = height;

		Random rand = new Random();

		this.map = new boolean[width * height];
		for (int i = 0; i < map.length; i++) {

			boolean decision = false;
			if (rand.nextBoolean() == true && numRandomOn > 0) {
				decision = true;
				numRandomOn--;
			}

			this.map[i] = decision;
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

		MemoryImageSource source = new MemoryImageSource(this.width,
				this.height, cm, pixels, 0, this.width);
		Image image = Toolkit.getDefaultToolkit().createImage(source);

		BufferedImage bimage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
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

}
