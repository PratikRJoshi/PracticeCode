package zeroOrder;

public class DeleteKthNodeRecursion {
    private static ListNode deleteKthNode(ListNode head, int k){
        if (head == null || k == 0)
            return head;

        if (k == 1)
            return head.next;

        int count = 1;

        deleteKthNode(head, k, count);
        return head;
    }

    private static void deleteKthNode(ListNode head, int k, int count) {
        if (count == k - 1){
            if (head.next != null)
                head.next = head.next.next;
            return;
        }

        deleteKthNode(head.next, k, count + 1);
    }

    private static void printList(ListNode head){
        while (head != null){
            System.out.print(head.val + " ");
            head = head.next;
        }

        System.out.println();
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(23);
        head.next.next.next = new ListNode(24);
        head.next.next.next.next = new ListNode(5);

        int k = 1;
//        int k = 4;

        printList(head);

        ListNode node = deleteKthNode(head, k);

        printList(node);
    }
}
