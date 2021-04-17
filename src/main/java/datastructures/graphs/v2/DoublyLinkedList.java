package datastructures.graphs.v2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

public class DoublyLinkedList<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DoublyLinkedList() {
        size = 0;
    }
    
    public List<E> getListOfElements() {
        return getListOfNodes()
                .stream()
                .map(x -> x.getElement())
                .collect(Collectors.toList());
    }
    
    public List<Node<E>> getListOfNodes() {
        final List<Node<E>> list = new ArrayList<>();
        
        final Iterator<Node<E>> it = forwardIterator();
        while (it.hasNext()) {
            final Node<E> node = it.next();
            list.add(node);
        }
        
        return list;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Move node to the front of the list
     * 
     * Time: O(1)
     * 
     * @param node 
     */
    public void placeFirst(Node node) {
        if (node == head || node.equals(head)) {
            return;
        }
        
        if (tail != null && tail == node) {
            tail = node.getPrev();
        }
        
        if (node.getPrev() != null) {
            node.getPrev().setNext(node.getNext());
        }

        if (node.getNext() != null) {
            node.getNext().setPrev(node.getPrev());
        }
        
        node.setNext(head);
        node.setPrev(null);
        
        if (head != null) {
            head.setPrev(node);
        }
        
        head = node;
        
        if (tail == null) {
            tail = node;
        }
    }

    public void addFirst(E element) {
        final Node<E> tmp = new Node<>(element, head, null);
        if (head != null) {
            head.setPrev(tmp);
        }
        head = tmp;
        if (tail == null) {
            tail = tmp;
        }
        size++;
    }

    public void addLast(E element) {
        final Node<E> tmp = new Node<>(element, null, tail);
        if (tail != null) {
            tail.setNext(tmp);
        }
        tail = tmp;
        if (head == null) {
            head = tmp;
        }
        size++;
    }
    
    // Takes 2 * O(d_k(v)) time where v is the node of this adjacency list
    // and d_k(v) is the degree of v in G_k
    public void moveToFront(Set<E> dataToBeMoved, int dk) {
        if (dk > size) {
            throw new IllegalArgumentException();
        }
        
        final List<Node> nodesToMove = new ArrayList<>();
        
        final Iterator<Node<E>> it = forwardIterator();
        int i = 0;
        while (it.hasNext() && i < dk) {
            ++i;
            final Node<E> node = it.next();
            if (dataToBeMoved.contains(node.getElement())) {
                nodesToMove.add(node);
            }
        }
        
        for (Node node : nodesToMove) {
            placeFirst(node);
        }
    }

    /* Given a node as prev_node, insert a new node after the given node */
    // previousNode <-> newNode <-> previousNode.getNext()
    public void insertAfter(Node previousNode, E elm) {
        if (previousNode == null) {
            throw new IllegalArgumentException();
        }

        final Node newNode = new Node(elm, previousNode.getNext(), previousNode);

        previousNode.setNext(newNode);

        if (newNode.getNext() != null) {
            newNode.getNext().setPrev(newNode);
        }
    }
    
    public Iterator<Node<E>> forwardIterator() {
        return new ForwardIterator();
    }
    
    private class ForwardIterator implements Iterator<Node<E>> {
        
        private Node current;
        private boolean started;
        
        private ForwardIterator() {
            this.current = null;
            this.started = false;
        }

        @Override
        public boolean hasNext() {
            if (!started) {
                started = true;
                current = head;
                return current != null;
            }
            
            if (current == null) {
                return false;
            }
            
            current = current.getNext();
            
            return current != null;
        }

        @Override
        public Node next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            
            return current;
        }
        
    }

    public void iterateForward() {
        Node current = head;
        while (current != null) {
            current = current.getNext();
        }
    }

    public void iterateBackward() {
        Node tmp = tail;
        while (tmp != null) {
            tmp = tmp.getPrev();
        }
    }

    public E removeFirst() {
        if (size == 0) {
            throw new IllegalStateException();
        }
        final Node<E> tmp = head;
        head = head.getNext();
        head.setPrev(null);
        size--;
        return tmp.getElement();
    }

    public E removeLast() {
        if (size == 0) {
            throw new IllegalStateException();
        }
        final Node<E> tmp = tail;
        tail = tail.getPrev();
        tail.setNext(null);
        size--;
        return tmp.getElement();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        
        final Iterator<Node<E>> it = forwardIterator();
        while (it.hasNext()) {
            final Node<E> node = it.next();
            sb.append(node.getElement());
            sb.append(" -> ");
        }
        
        return "DoublyLinkedList{" + sb.toString() + '}';
    }

}
