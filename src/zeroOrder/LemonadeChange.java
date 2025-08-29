package zeroOrder;

public class LemonadeChange {
    public boolean lemonadeChange(int[] bills) {
        int count5 = 0, count10 = 0;
        for (int bill : bills) {
            if (bill == 5) {
                count5++;
            } else if (bill == 10) {
                count10++;
                count5--;
            } else if (bill == 20) {
                if (count10 > 0) {
                    count10--;
                    count5--;
                } else {
                    count5 -= 3;
                }
            }
            if (count5 < 0)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
//        int[] bills = {5,5,5,10,5,5,10,20,20,20};
//        int[] bills = {5,5,5,10,20};
        int[] bills = {5,5,10,10,20};
        LemonadeChange lemonadeChange = new LemonadeChange();
        boolean canGiveChangeToAll = lemonadeChange.lemonadeChange(bills);
        System.out.println(canGiveChangeToAll);
    }
}
