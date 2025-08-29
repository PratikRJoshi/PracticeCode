import java.util.HashMap;
import java.util.Map;

public class LRU {
    Map<Integer, DoublyLinkedListNode> lruCache = new HashMap<Integer, DoublyLinkedListNode>();
    DoublyLinkedListNode head;
    DoublyLinkedListNode end;
    int length;
    int capacity;
    
    public LRU(int capacity) {
        this.capacity = capacity;
        length = 0;
    }
    
    public int get(int key) {
        if(lruCache.containsKey(key)){
        	DoublyLinkedListNode tempNode = lruCache.get(key);
        	removeNode(tempNode);
        	setHead(tempNode);
        	return tempNode.val;
        }
        else{
        	return -1;
        }
    }
    
    public void set(int key, int value) {
        if(lruCache.containsKey(key)){
        	DoublyLinkedListNode currentNode = lruCache.get(key);
        	currentNode.val = value;
        	removeNode(currentNode);
        	setHead(currentNode);
        }
        else{
        	DoublyLinkedListNode newNode = new DoublyLinkedListNode(key, value);
        	if(length<capacity){
        		setHead(newNode);
        		lruCache.put(key, newNode);
        		length++;
        	}
        	else{
        		lruCache.remove(end.key);
        		end = end.pre;
        		if(end!=null)
        			end.next = null;
        		setHead(newNode);
        		lruCache.put(key, newNode);
        	}
        }
    }
    
    public void removeNode(DoublyLinkedListNode node){
    	DoublyLinkedListNode current = node;
    	DoublyLinkedListNode nextNode = current.next;
    	DoublyLinkedListNode prevNode = current.pre;
    	
    	if(prevNode!=null){
    		prevNode.next = nextNode;
    	}
    	else{
    		head = nextNode;
    	}
    	
    	if(nextNode!=null){
    		nextNode.pre = prevNode;
    	}
    	else{
    		end = prevNode;
    	}
    }
    
    public void setHead(DoublyLinkedListNode node){
    	node.next = head;
    	node.pre = null;
    	
    	if(head!=null)
    		head.pre = node;
    	head = node;
    	
    	if(end == null)
    		end = node;
    }
    
}

class DoublyLinkedListNode {
    int val;
    int key;
    DoublyLinkedListNode pre;
    DoublyLinkedListNode next;
    
    public DoublyLinkedListNode(int key, int val){
        this.val = val;
        this.key = key;
    }
}