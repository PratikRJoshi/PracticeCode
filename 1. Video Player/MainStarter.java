import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

public class MainStarter {
	static byte[] bytesFromFile;
	static byte[] bytesToRead;
	static InputStream is = null;
	static int width, height;
	static BufferedImage img=null;
	static JFrame frame=null;
	static long duration = 0;
	static long startTime = 0;
	static long endTime = 0;
	
	//check why this method doesn't works
	public static void renderImage(double newWidth, double newHeight, byte[] bytes, double widthscalingFactor, double heightscalingFactor) throws InterruptedException {
		BufferedImage img = new BufferedImage((int)newWidth, (int)newHeight, BufferedImage.TYPE_INT_RGB);
		JFrame frame = new JFrame();

		double xNew=0, yNew=0;

		int ind=0;
		for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {

						xNew = widthscalingFactor*x;
						yNew = heightscalingFactor*y;
						byte r = bytes[ind];
						byte g = bytes[ind+height*width];
						byte b = bytes[ind+height*width*2]; 
						int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
						img.setRGB((int)xNew, (int)yNew, pix);

					ind++;
				}
		}
		JLabel label = new JLabel(new ImageIcon(img));
		frame.getContentPane().setPreferredSize(new Dimension((int)newWidth, (int)newHeight));
		frame.getContentPane().add(label, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	public static void fileReading(String fileName) throws IOException {
		File file = new File(fileName);
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		long len = file.length();
		bytesFromFile = new byte[(int) len];

		int offset = 0;
		int numRead = 0;
		while (offset < bytesFromFile.length && (numRead = is.read(bytesFromFile, offset, bytesFromFile.length - offset)) >= 0) {
			offset += numRead;
		}
	}
	private static void aliasingFunction(int h, int w, int[][] originalRedBuffer, int[][] originalGreenBuffer, int[][] originalBlueBuffer, int[][] aliasedRedBuffer, int[][] aliasedGreenBuffer, int[][] aliasedBlueBuffer) {

		int sumOfRedPixels = 0;
		int sumOfBluePixels = 0;
		int sumOfGreenPixels = 0;
		
		for (int p = h - 1; p <= h + 1; p++) {
			for (int q = w - 1; q <= w + 1; q++) {
				sumOfRedPixels = sumOfRedPixels + (originalRedBuffer[p][q] & 0xff);
				sumOfGreenPixels = sumOfGreenPixels + (originalGreenBuffer[p][q] & 0xff);
				sumOfBluePixels = sumOfBluePixels + (originalBlueBuffer[p][q] & 0xff);
			}
		}
		aliasedRedBuffer[h][w] = sumOfRedPixels / 9;
		aliasedBlueBuffer[h][w] = sumOfBluePixels / 9;
		aliasedGreenBuffer[h][w] = sumOfGreenPixels / 9;
	}
	
	public static byte[] aliasingFunctionBody(int height,int width){
		int originalRedBuffer[][] = new int[height][width];
		int originalGreenBuffer[][] = new int[height][width];
		int originalBlueBuffer[][] = new int[height][width];
		int aliasedRedBuffer[][] = new int[height][width];
		int aliasedGreenBuffer[][] = new int[height][width];
		int aliasedBlueBuffer[][] = new int[height][width];
		int dataCounter = 0;
		
		byte[] arrayAfterAliasing= new byte[height*width*3];

		
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				originalRedBuffer[h][w] = bytesToRead[dataCounter];
				originalGreenBuffer[h][w] = bytesToRead[dataCounter + (height * width)];
				originalBlueBuffer[h][w] = bytesToRead[dataCounter + (height * width * 2)];
				dataCounter++;
			}
		}
		dataCounter+=(height*width*2);
		
		for (int h = 1; h < height - 1; h++) {
			for (int w = 1; w < width - 1; w++) {
				aliasingFunction(h, w, originalRedBuffer, originalGreenBuffer, originalBlueBuffer, aliasedRedBuffer, aliasedGreenBuffer, aliasedBlueBuffer);
			}
		}
		
		startTime = System.currentTimeMillis();
		int ind=0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				int r = aliasedRedBuffer[y][x];
				int g = aliasedGreenBuffer[y][x];
				int b = aliasedBlueBuffer[y][x];
				
				arrayAfterAliasing[ind]=(byte)(r & 0xff);
				arrayAfterAliasing[ind+(height*width)]=(byte)(g & 0xff);
				arrayAfterAliasing[ind+(height*width*2)]=(byte)(b & 0xff);
				ind++;
			}
		}
		return arrayAfterAliasing;
	}
	public static void main(String[] args) throws IOException, InterruptedException {

			int originalWidth = 352;
			int originalHeight = 288;
			
			double newWidth = 1, newHeight = 1;
			newWidth = (originalWidth)* Double.parseDouble(args[1]);
			newHeight = (originalHeight) * Double.parseDouble(args[2]);
			int framesPerSecond = Integer.parseInt(args[3]);
			int  aliasingParameter = Integer.parseInt(args[4]);
			int image_count = 0;
			
			ArrayList<BufferedImage> listOfOriginalImages = new ArrayList<BufferedImage>();
			
			fileReading(args[0]);
			bytesToRead=new byte[originalHeight*originalWidth*3];
			double startIndexOfByte = 0.0;
//			System.out.println("Width: "+(int)Math.ceil(newWidth)+" Height : "+(int)Math.ceil(newHeight));
			
			byte[] bufferForAliasing = new byte[originalHeight*originalWidth*3];
			
			if(Integer.parseInt(args[4]) == 2){
				String[] seamCarvingArguments = new String[3];
				seamCarvingArguments[0]=args[0];
				seamCarvingArguments[1]=args[1];
				seamCarvingArguments[2]=args[2];
				SeamScalingFile.main(seamCarvingArguments);
				return;
			}
			
			while( image_count < 450 ){
			System.arraycopy((Object)bytesFromFile, (int)startIndexOfByte, (Object)bytesToRead, 0, originalWidth*originalHeight*3);	
			img = new BufferedImage((int)Math.ceil(newWidth), (int)Math.ceil(newHeight), BufferedImage.TYPE_INT_RGB);
			frame = new JFrame();
			if(aliasingParameter == 1){
				bufferForAliasing=aliasingFunctionBody(originalHeight,originalWidth);
				System.arraycopy((Object)bufferForAliasing,0, (Object)bytesToRead, 0, originalWidth*originalHeight*3);
			}
			
			if(Integer.parseInt(args[4]) == 1){
				aspectRatioBody(args, originalWidth, originalHeight, Double.parseDouble(args[1]),Double.parseDouble(args[2]), startIndexOfByte);
			}
			else{
				scaleTester(args[0],Double.parseDouble(args[1]),Double.parseDouble(args[2]), 0, originalWidth, 0, originalHeight, 0, 0, 'x', 0, 0);
			}
			listOfOriginalImages.add(img);
			startIndexOfByte += (originalWidth*originalHeight*3);
//			System.out.println("image no added: "+image_count+"index after image : "+startIndexOfByte);
			image_count++;
			}
			
			JLabel label;
			for( int j=0;j<listOfOriginalImages.size();j++){
				label = new JLabel(new ImageIcon(listOfOriginalImages.get(j)));
				frame.getContentPane().add(label, BorderLayout.CENTER);
				frame.pack();
				frame.setVisible(true);
				Thread.sleep((1000/framesPerSecond)- duration);
				frame.remove(label);
				System.out.println("Image "+j);
			}		
			
			System.out.println("Done");
	}
	
	public static double[] scaleTester(String fileName,double widthscalingFactor,double heightScalingFactor ,int widthStart, int widthEnd,int heightStart, int heighttEnd, double xold , double yold, char dummyVariable, double indexStart, double byteStartIndex) throws IOException{
		
		double ind=0.0;
		
		int x = 0,y=0;
		
		try {
			int width = 352;
			int height = 288;
			
			if(dummyVariable == 'x'){
				ind = widthStart;
			}
			else{
				ind=(int)indexStart;
			}
				fileReading(fileName);
				
			is.close();
			
//			System.out.println("\nScaled height: "+heightScalingFactor*(heighttEnd-heightStart));
//			System.out.println("\nScaled width: "+widthscalingFactor*(widthEnd-widthStart));
			
			double heightCounter = 0.0;
			double innerLoopCounter = 0.0;
			
			startTime = System.currentTimeMillis();
			
				for (y = 0; y < (int)Math.floor(heightScalingFactor * (heighttEnd - heightStart)); y++) {
					double previousIndex = ind;
					innerLoopCounter = 0;
					for (x = 0 ; x < (int)Math.floor(widthscalingFactor * (widthEnd - widthStart)); x++) {
  							
						byte r = bytesToRead[(int)Math.floor(ind)];
  							byte g = bytesToRead[(int)Math.floor(ind+height*width)];
  							byte b = bytesToRead[(int)Math.floor(ind+height*width*2)]; 
  							int pix =(0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff));
  							
  							
  							img.setRGB((int)x + (int)xold, (int)y + (int)yold, pix);
  							
  							ind+= (1/widthscalingFactor);
  							innerLoopCounter+= (1/widthscalingFactor);
  							
					}
					heightCounter+=(1/heightScalingFactor);
					
					if( heightScalingFactor == 1 ){
						ind+=(width-(widthEnd - widthStart));
						heightCounter = 0;
					}
					else if(Math.floor(heightCounter) >= 1){
						ind = indexStart + widthStart + innerLoopCounter;
						ind+=(width-(widthEnd - widthStart));
						ind+=(width*(Math.floor(heightCounter)-1));
					}
					else{
						ind = previousIndex;
					}
				}
				
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(dummyVariable == 'x'){
			double[] returnValue = {x + xold, ind};
			return returnValue;
		}
		else{
			double[] returnValue = {y+yold,ind};
			return returnValue;
		}
	}
	
private static double aspectRatioBody(String[] args, int w_old, int h_old, double scaleInX, double scaleInY, double byteStartIndex) throws IOException {
		
		double newWidth = Math.ceil(scaleInX*w_old);
		double newHeight = Math.ceil(scaleInY*h_old);
		
		double aspectRatioParam = (scaleInY/scaleInX);
		
		int fixedPercentageOfScreen = 70;
		int randomVariable = 0;
		double w_new_middle = 0;
		double newXScaling = 0;
		double newYScaling = 0;
		double xScalingRemainder = 0;
		double yScalingRemainder = 0;
		double newHeightMiddle = 0;
		int leftWidthStart = 0;
		int leftWidthEnd = 0;
		int middleWidthStart = 0; 
		int middleWidthEnd = 0;
		int rightWidthStart = 0;
		int rightWidthEnd = 0;
		int topHeightStart = 0;
		int topHeightEnd = 0;
		int middleHeightStart = 0;
		int middleHeightEnd = 0;
		int bottomHeightStart = 0;
		int bottomHeightEnd = 0;

		if(aspectRatioParam<1){
			randomVariable = (int)(((double)((double)fixedPercentageOfScreen/100))*( w_old / 2 ));
			w_new_middle = Math.ceil((2*randomVariable)*newHeight/h_old);
			newXScaling = w_new_middle / (double)(2*randomVariable);
			newYScaling = scaleInY;
			
			leftWidthStart = 0;
			leftWidthEnd = w_old - ((w_old-(2*randomVariable))/2) - (2*randomVariable);
			
			middleWidthStart = leftWidthEnd;
			middleWidthEnd = ((w_old-(2*randomVariable))/2) + (2*randomVariable);
			
			rightWidthStart = middleWidthEnd;
			rightWidthEnd = w_old;
			
			xScalingRemainder = ((newWidth-w_new_middle)/2)/((w_old-(2*randomVariable))/2);
			yScalingRemainder = scaleInY;

			try {
				double[] sameReturnValueAsBefore;
				sameReturnValueAsBefore = scaleTester(args[0] , xScalingRemainder, yScalingRemainder, leftWidthStart, leftWidthEnd, 0, h_old ,0, 0, 'x', 0, byteStartIndex);
				sameReturnValueAsBefore = scaleTester(args[0] , newXScaling, newYScaling, middleWidthStart, middleWidthEnd, 0, h_old, sameReturnValueAsBefore[0], 0, 'x', 0, byteStartIndex);
				scaleTester(args[0] , xScalingRemainder, yScalingRemainder, rightWidthStart, rightWidthEnd, 0, h_old, sameReturnValueAsBefore[0], 0, 'x', 0, byteStartIndex);
				
				return sameReturnValueAsBefore[1];

			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}else if( aspectRatioParam > 1 ){
			randomVariable = (int) (((double)fixedPercentageOfScreen/100)*( h_old / 2 ));
			
			newHeightMiddle = Math.ceil((2*randomVariable)*newWidth/w_old);
			newYScaling = (newHeightMiddle / (2*randomVariable));
			newXScaling = scaleInX;
			
			topHeightStart = 0;
			topHeightEnd = h_old - ((h_old-(2*randomVariable))/2) - (2*randomVariable);
			
			
			middleHeightStart = topHeightEnd;
			middleHeightEnd = ((h_old-(2*randomVariable))/2) + (2*randomVariable);
			
			bottomHeightStart = middleHeightEnd;
			bottomHeightEnd = h_old;
			
			yScalingRemainder = ((newHeight-newHeightMiddle)/2)/((h_old-(2*randomVariable))/2);
			xScalingRemainder = scaleInX;
			double[] retval;
			retval = scaleTester(args[0], xScalingRemainder, yScalingRemainder, 0, w_old, topHeightStart, topHeightEnd, 0, 0, 'y', 0, byteStartIndex);
			
			retval = scaleTester(args[0], newXScaling, newYScaling, 0, w_old, middleHeightStart, middleHeightEnd, 0, retval[0], 'y', retval[1], byteStartIndex);
			
			retval = scaleTester(args[0], xScalingRemainder, yScalingRemainder, 0, w_old, bottomHeightStart, bottomHeightEnd, 0, retval[0],'y', retval[1], byteStartIndex);
			
			return retval[1];
		}
		 
		else{
			double[] retval;
			retval = scaleTester(args[0], Double.parseDouble(args[1]),Double.parseDouble(args[2]), 0, w_old, 0, h_old, 0, 0, 'x', 0, byteStartIndex);
			return retval[1];
		}
		return 0;
	}

}
