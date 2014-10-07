import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*************************************************************************
 *  Compilation:  javac ResizeDemo.java
 *  Execution:    java ResizeDemo input.png columnsToRemove rowsToRemove
 *  Dependencies: SeamCarver.java Picture.java System.out.java Stopwatch.java
 *                
 *
 *  Read image from file specified as command line argument. Use SeamCarver
 *  to remove number of rows and columns specified as command line arguments.
 *  Display the image and print time elapsed.
 *
 *************************************************************************/

public class SeamScalingFile {
	static byte[] bytes;
	static InputStream is;
	static int framesPerSecond = 30;
	static long endTime, startTime, duration = 0;
    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayList<BufferedImage> originalSetOfImages = new ArrayList<BufferedImage>();
    	ArrayList<BufferedImage> seamCarvedSetOfImages = new ArrayList<BufferedImage>();
        
    	if (args.length != 3) {
            System.out.println("Usage:\njava ResizeDemo [image filename] [num columns to remove] [num rows to remove]");
            return;
        }
        
    	double widthscalingFactor=1, heightscalingFactor=1;
    	double newWidth = 1, newHeight = 1;

//        Picture picture = new Picture(args[0]);
    	originalSetOfImages = fileReading(args[0], originalSetOfImages);
//        BufferedImage img = fileReading(args[0], originalSetoFImages);
    	int originalImageCounter = 0;
    	int newImageCounter = 0;
//    	long startTime = System.currentTimeMillis();
    	System.out.println("Starting time = "+startTime);
    	
    	for(int imageIndex = 0; imageIndex < originalSetOfImages.size(); imageIndex++){
    		Picture picture = new Picture(originalSetOfImages.get(imageIndex));
		
    		if (Double.parseDouble(args[1]) != 0 && Double.parseDouble(args[2]) != 0 && Double.parseDouble(args[1]) <= 1 && Double.parseDouble(args[2]) <= 1) {
    			widthscalingFactor = Double.parseDouble(args[1]);
    			heightscalingFactor = Double.parseDouble(args[2]);
    			newWidth = picture.width() * widthscalingFactor;
    			newHeight = picture.height() * heightscalingFactor;	
    		}
		
    		int removeColumns = Math.abs((int)(newWidth - picture.width()));
        	int removeRows = Math.abs((int)(newHeight - picture.height())); 

        	System.out.printf("Original image "+(++originalImageCounter)+" = %d X %d image\n", picture.width(), picture.height());
        	SeamCarver sc = new SeamCarver(picture);

        	for (int i = 0; i < removeRows; i++) {
        		int[] horizontalSeam = sc.findHorizontalSeam();
        		sc.removeHorizontalSeam(horizontalSeam);
        	}

        	for (int i = 0; i < removeColumns; i++) {
        		int[] verticalSeam = sc.findVerticalSeam();
        		sc.removeVerticalSeam(verticalSeam);
        	}

        	System.out.printf("New image "+(++newImageCounter)+" = %d columns X %d rows\n", sc.width(), sc.height());
        	BufferedImage newPic = (sc.picture().getBufferedImage());
        	seamCarvedSetOfImages.add(newPic);

    	}
//    	long endTime = System.currentTimeMillis();
//    	long duration = (endTime-startTime);
    	
    	System.out.println("Ended at "+endTime);
    	System.out.println("Runtime = "+((duration)/1000)/60 +" minutes");

    	JFrame frame = new JFrame();
    	JLabel label = new JLabel();
    	
    	frame.getContentPane().setPreferredSize(new Dimension((int)newWidth, (int)newHeight));
    	
    	for(int seamCarveIndex = 0; seamCarveIndex < seamCarvedSetOfImages.size(); seamCarveIndex++){
			label = new JLabel(new ImageIcon(seamCarvedSetOfImages.get(seamCarveIndex)));
			frame.getContentPane().add(label, BorderLayout.CENTER);
			frame.pack();
			frame.setVisible(true);
			System.out.println("Displayed "+(seamCarveIndex+1)+" image");
			Thread.sleep((1000/framesPerSecond)- duration);
//			Thread.sleep(100);
			frame.remove(label);
		}
    	
    	/*for(int seamCarveIndex = 0; seamCarveIndex < seamCarvedSetOfImages.size(); seamCarveIndex++)
    		seamCarvedSetOfImages.get(seamCarveIndex).picture().show();*/    
    }
    
    public static ArrayList<BufferedImage> fileReading(String fileName, ArrayList<BufferedImage> originalSetoFImages) throws IOException, InterruptedException {    	
    	int width = 352;
		int height = 288;
		
	    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    int framesPerSecond = 30;
	    JFrame frame = new JFrame();
		JLabel label = null;
	    try {
		    File file = new File(fileName);
		    is = new FileInputStream(file);

		    long len = file.length();
		    bytes = new byte[(int)len];
		    
		    int offset = 0;
	        int numRead = 0;
	        while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
	            offset += numRead;
	        }
	    
	        
	        
	    	int ind = 0;
	    	while(ind+(height*width*2)<=bytes.length){
	    		startTime = System.currentTimeMillis();	
	    		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	    		for(int y = 0; y < height; y++){
	    			for(int x = 0; x < width; x++){
			 
	    				byte r = bytes[ind];
	    				byte g = bytes[ind+height*width];
	    				byte b = bytes[ind+height*width*2]; 
					
	    				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
	    				
	    				img.setRGB(x,y,pix);
	    				ind++;
	    			}
	    		}
	    	
	    	endTime = System.currentTimeMillis();
	    	duration = endTime - startTime;
	    	ind+=width*height*2;
			originalSetoFImages.add(img);
//			img.flush();
			System.out.println("Size of original list = "+originalSetoFImages.size());
			
	    	}
	    	
//	    	frame.getContentPane().setPreferredSize(new Dimension((int)width, (int)height));
//	    	
//	    	for(int i=0;i<originalSetoFImages.size();i++){
//				label = new JLabel(new ImageIcon(originalSetoFImages.get(i)));
//				frame.getContentPane().add(label, BorderLayout.CENTER);
//				frame.pack();
//				frame.setVisible(true);
////				System.out.println("Done");
//				Thread.sleep((1000/framesPerSecond)- duration);
//				frame.remove(label);
//			}
	    	
	    	
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    return originalSetoFImages;
	}

    
}