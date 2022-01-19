import java.util.StringJoiner;
import java.util.ArrayList;
/*
 * Emily Miner
 */
public class IDLList<E> {

	private static class Node<E> {
        /**
         * The reference to the data.
         */
        private E data;
        /**
         * The reference to the next node.
         */
        private Node<E> next = null;
        private Node<E> prev = null;

        /**
         * Creates a new node with a null next and previous field
         *
         * @param elem The data stored
         */
        public Node(E elem) {
            data = elem;
        }

        /**
         * Construct a node with the given values of next and prev
         *
         * @param dataItem The data stored
         * @param next
         * @param prev
         */
        public Node(E elem, Node<E> prev, Node<E> next) {
            data = elem;
            this.next = next;
            this.prev = prev;
        }

    }
    /**
     * A reference to the head of the list
     */
    private Node<E> head;
    /**
     * A reference to the tail of the list
     */
    private Node<E> tail;
    /**
     * The size of the list
     */
    private int size;
    /**
     * Makes an ArrayList for indices
     */
    private ArrayList<Node<E>> indices;
    /**
     * Constructor
     */
    public IDLList() {
    	head = null;
    	tail = null;
    	size = 0;
    	indices = new ArrayList<Node<E>>();
    }
    
    /**
     * Adds element to the head (ie it becomes the first
     * element of the list).
     * 
     * @param elem
     * @return True
     */
    public boolean add (E elem) {
       if (head == null) {
    	   head = new Node<>(elem, null, null);
    	   tail = head;
       } else {
    	   Node<E> savedHead = head;
    	   head = new Node<>(elem, head, null);
    	   head.prev = head;
    	   head.next = savedHead;
       }
       indices.add(0, head);
       size++;
        
       return true;
    }
    /**
     * Adds a new item at position index. 
     * The new item is inserted between the one at position index-1
     * and the one formerly at position index.
     *
     * @param index The index where the new item is to be inserted
     * @param elem The element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public boolean add(int index, E elem) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        } 
        if (index == 0) {
            add(elem);
        } else {
            Node<E> nodeData = indices.get(index);
            Node<E> newNodeData = new Node<>(elem, nodeData, nodeData.prev);
            nodeData.prev.next = newNodeData;
            nodeData.prev = newNodeData;
            size++;
            indices.add(index,newNodeData);
        }
        
    	return true;
    }
    /**
     * Adds elem as new tail.
     * 
     * @param elem
     * @return
     */
    public boolean append (E elem) {
        add(size, elem);
        return true;
    }
    /**
     * Returns the object at position index from the head.
     * Indexing starts at 0.
     * 
     * @param index
     * @throws IndexOutOfBoundsException if the index is out of range
     * @return
     */
    public E get (int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
    	return indices.get(index).data;
    }
    /**
     * Returns the object at the head.
     * 
     * @throws IllegalStateException If head is null 
     * @return
     */
    public E getHead() {
        if (head == null) {
            throw new IllegalStateException();
        }
        return head.data;
    }
    /**
     * Returns the object at the tail.
     * 
     * @throws IllegalStateException If tail is null 
     * @return
     */
    public E getLast() {
        if (tail == null) {
            throw new IllegalStateException();
        }
        return tail.data;
    }
    /**
     * Returns the list size.
     * 
     * @return
     */
    public int size() {
    	return size;
    }
    /**
     * Removes and returns the element at the head.
     * 
     * @throws IllegalStateException If head is null 
     * @return
     */
    public E remove() {
        if (head == null) {
            throw new IllegalStateException();
        }
        if (size == 1) {
            Node<E> savedHead = head;
            head = null;
            tail = null;
            indices.clear();
            size = 0;
            return savedHead.data;
        }
        Node<E> savedHead = head;
        head = head.next;
        indices.remove(0);
        size--;
        return savedHead.data;
    }
    /**
     * Removes and returns the element at the tail.
     * 
     * @throws IllegalStateException If head is null 
     * @return
     */
    public E removeLast() {
        if (head == null) {
            throw new IllegalStateException();
        }
        if (size == 1) {
            Node<E> savedTail = tail;
            head = null;
            tail = null;
            indices.clear();
            size = 0;
            return savedTail.data;
        }
        Node<E> savedTail = tail;
        tail.prev.next = tail.next;
        tail.next.prev = tail.prev;
        indices.remove(size-1);
        size--;
        return savedTail.data;  	
    }
    /**
     * Removes and returns the element at the index index.
     * 
     * @param index
     * @throws IllegalStateException If element does not exist. 
     * @return
     */
    public E removeAt (int index) {
        if (index < 0 || index >= size) {
            throw new IllegalStateException();
        }
        if (index == 0) {
        	return remove();
        } else if (index == size-1) {
        	return removeLast();
        }
        Node<E> saved = indices.get(index);
        indices.remove(index);
        saved.prev.next = saved.next;
        saved.next.prev = saved.prev;
        size--;
        return saved.data;
    }
    /**
     * Removes the first occurrence of elem in the list and returns true.
     * Returns false if elem was not in the list.
     * 
     * @param elem
     * @return
     */
    public boolean remove (E elem) {
    	if (head == null) {
    		return false;
    	}
    	Node<E> savedHead = head;
    	for (int i = 0; savedHead != null && i < size; i++) {
    		if(savedHead.data == elem) {
    			removeAt(i);
    			return true;
    		}
    		savedHead = savedHead.next;
    	}
    	return false;
    }
    /**
     * Presents a string representation of the list.
     * 
     * @return A String representation of the list
     */
    @Override
    public String toString() {
		StringBuilder sb = new StringBuilder();
        Node<E> p = head;
        sb.append("[");
        while (p != null) {
            if (p.next == null) {
            	sb.append(p.data.toString()+"]");
            } else {
        	sb.append(p.data.toString()+",");
            }
            p = p.next;
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {

    }
}
