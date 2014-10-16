import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.swing.*;

public class CompressionMain {

	public static void main(String[] args) {

		String fileName = args[0];
		int width = Integer.parseInt(args[1]);
		int height = Integer.parseInt(args[2]);
		int imageBuffer[][] = new int[width][height];
//		int[][] quantizationTable = { { 16, 11, 10, 16, 24, 40, 51, 61 },
//				{ 12, 12, 14, 19, 26, 58, 60, 55 },
//				{ 14, 13, 16, 24, 40, 57, 69, 56 },
//				{ 14, 17, 22, 29, 51, 87, 80, 62 },
//				{ 18, 22, 37, 56, 68, 109, 103, 77 },
//				{ 24, 35, 55, 64, 81, 104, 113, 92 },
//				{ 49, 64, 78, 87, 103, 121, 120, 101 },
//				{ 72, 92, 95, 98, 112, 100, 103, 99 } };

		int cosineTransform[][] = new int[width][height];

		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		try {
			File file = new File(fileName);
			InputStream is = new FileInputStream(file);

			long len = file.length();
			byte[] bytes = new byte[(int) len];

			fileRead(bytes, is);
			
			is.close();
			
			imageBuffer = createImageBuffer(width, height, bytes, imageBuffer, img);
			
			setImage(img);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int applyCosineTransform(int channel, int xPosition, int yPosition){
		int transformedChannel = channel;
		
		
		return transformedChannel;
	}
	
	public static void fileRead(byte[] bytes, InputStream is) throws IOException{
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
	}
	
	public static int[][] createImageBuffer(int width, int height, byte[] bytes, int[][] imageBuffer, BufferedImage img){
		int ind = 0;
		for (int y = 0; y < height; y++) {

			for (int x = 0; x < width; x++) {

				// byte a = 0;
				int r = bytes[ind];
				int g = bytes[ind + height * width];
				int b = bytes[ind + height * width * 2];

				int newR = applyCosineTransform(r, x, y);
				int newG = applyCosineTransform(g, x, y);
				int newB = applyCosineTransform(b, x, y);
				
//				int pix = 0xff000000 | ((r & 0xff) << 16)
//						| ((g & 0xff) << 8) | (b & 0xff);
				
				int pix = 0xff000000 | ((newR & 0xff) << 16)
						| ((newG & 0xff) << 8) | (newB & 0xff);
				
				imageBuffer[x][y] = pix;
				// img.setRGB(x,y,pix);
				img.setRGB(x, y, imageBuffer[x][y]);
				ind++;
			}
		}
		return imageBuffer;
	}
	
	public static void setImage(BufferedImage img) {

		// Use a label to display the image
		JFrame frame = new JFrame();
		JLabel label = new JLabel(new ImageIcon(img));
		frame.getContentPane().add(label, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

}