
public class ZigZagOrderMatrix {
	public int[][] Zig_Zag(final int size){
		int[][] data = new int[size][size];
		int i = 1;
		int j = 1;
		int count = 0;
		for (int element = 0; element < size * size; element++){
			if(count<=5){
				data[i - 1][j - 1] = element;
			}
			else{
				data[i - 1][j - 1] = 0;
			}
			count++;
			
				if ((i + j) % 2 == 0){
					// Even stripes
					if (j < size)
						j++;
					else
						i+= 2;
					if (i > 1)
						i--;
				}
				else{
					// Odd stripes
					if (i < size)
						i++;
					else
						j+= 2;
					if (j > 1)
						j--;
				}
			
		}
		return data;
	}
	
	public static void main(String args[]){
		ZigZagOrderMatrix z = new ZigZagOrderMatrix();
		int input = 3;
		int[][] result = z.Zig_Zag(input);
		for(int i=0;i<result.length;i++){
			for(int j=0;j<result[0].length;j++){
				System.out.print(result[i][j]+"\t");
			}
			System.out.println();
		}
	}
}
