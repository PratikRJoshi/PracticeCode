package zeroOrder;

public class Koko {
    public int minEatingSpeed(int[] piles, int H) {
        int low = 1;
        int high = getHighFromPiles(piles);

        while(low <= high) {
            int rate = low + (high - low) / 2;
            if(checkEatableWithThisRate(piles, H, rate)) {
                high = rate - 1;
            } else {
                low = rate + 1;
            }
        }

        return low;
    }

    private boolean checkEatableWithThisRate(int[] piles, int H, int rate) {
        int hours = 0;
        for(int pile : piles) {
            hours += (int)Math.ceil((double)pile / (double)rate);
        }
        return (hours <= H);
    }

    private int getHighFromPiles(int[] piles) {
        int high = piles[0];
        for(int pile : piles) {
            high = Math.max(high, pile);
        }

        return high;
    }

    public static void main(String[] args) {
//        int[] piles = {3,6,7,11};
        int[] piles = {30,11,23,4,20};
//        int H = 8;
        int H = 6;

        Koko koko = new Koko();
        int minEatingSpeed = koko.minEatingSpeed(piles, H);
        System.out.println(minEatingSpeed);
    }
}
