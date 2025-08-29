package zeroOrder;

public class PrintListAlternateRecursion {
    public static void printAlternate(ListNode head){
        if (head == null)
            return;
        System.out.println(head.val);
        if (head.next != null)
            printAlternate(head.next.next);
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        printAlternate(head);
    }
}
