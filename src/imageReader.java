
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;


public class imageReader {

  
   public static void main(String[] args) {
   	

	String fileName = args[0];
   	int width = Integer.parseInt(args[1]);
	int height = Integer.parseInt(args[2]);
	
    BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    try {
	    File file = new File(args[0]);
	    InputStream is = new FileInputStream(file);

	    long len = file.length();
	    byte[] bytes = new byte[(int)len];
	    byte[] redValues = new byte[width*height];
	    byte[] greenValues = new byte[width*height];
	    byte[] blueValues = new byte[width*height]; 
	    
	    System.out.println(bytes.length);
	    
	    int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
    		
    	int ind = 0, i=0;
//    	while(ind+(height*width*2)!=bytes.length){
    		for(int y = 0; y < height; y++){
    			for(int x = 0; x < width; x++){
    				System.out.println();
    				
    				System.out.println("byte["+ind+"] = "+bytes[ind]);
    				redValues[i] = bytes[ind];
    				System.out.println("redValues["+i+"] = "+redValues[i]);

    				System.out.println("byte["+ind+height*width+"] = "+bytes[ind+(height*width)]);
    				greenValues[i] = bytes[ind+height*width];
    				System.out.println("greenValues["+i+"] = "+greenValues[i]);
    				
    				System.out.println("byte["+ind+height*width*2+"] = "+bytes[ind+(height*width*2)]);
    				blueValues[i] = bytes[ind+height*width*2];
    				System.out.println("blueValues["+i+"] = "+blueValues[i]);
				
				
//				byte r = bytes[ind];
//				byte g = bytes[ind+height*width];
//				byte b = bytes[ind+height*width*2]; 
				
//				int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
    				int pix = 0xff000000 | ((redValues[i] & 0xff)<<16) | ((greenValues[i] & 0xff)<<8) | (blueValues[i] & 0xff);
    				img.setRGB(x,y,pix);
    				ind++;
    				i++;
    			}
    		}
//    	}
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    // Use a label to display the image
    JFrame frame = new JFrame();
    JLabel label = new JLabel(new ImageIcon(img));
    frame.getContentPane().add(label, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);

   }
  
}