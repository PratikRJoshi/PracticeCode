package zeroOrder;

public class ConstructRectangle {
    public static int[] constructRectangle(int area) {
        int[] result = new int[2];

        int sqrt = (int)Math.sqrt(area);
        if(sqrt * sqrt == area){
            result[0] = sqrt;
            result[1] = sqrt;
            return result;
        }

        int min = Integer.MAX_VALUE;
        for(int i = 1; i <= area; i++){
            if(area % i == 0){
                int remainder = area / i;
                if(remainder - i <= min && min >= 0){
                    min = remainder - i;
                    result[0] = (remainder < i ? i : remainder);
                    result[1] = (remainder < i ? remainder : i);
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int area = 10;
        int[] ints = constructRectangle(area);
        System.out.println(ints[0]);
        System.out.println(ints[1]);
    }
}
