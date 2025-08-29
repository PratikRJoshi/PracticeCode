package zeroOrder;

public class ExcelSheetColNumber {
    public int titleToNumber(String s) {
        if(s == null || s.length() == 0)
            return 0;

        int power = 0;
        int result = 0;

        for(int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            result += (int)Math.pow(26, power) * (c - 'A' + 1);
            power++;
        }

        return result;
    }

    public static void main(String[] args) {
        String input = "A";
        ExcelSheetColNumber excelSheetColNumber = new ExcelSheetColNumber();
        int titleToNumber = excelSheetColNumber.titleToNumber(input);
        System.out.println(titleToNumber);
    }
}
