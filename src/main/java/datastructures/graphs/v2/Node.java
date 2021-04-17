package datastructures.graphs.v2;

public class Node<E> {

    private E element;
    private Node next;
    private Node prev;

    public Node(E element, Node next, Node prev) {
        this.element = element;
        this.next = next;
        this.prev = prev;
    }

    public E getElement() {
        return element;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setElement(E element) {
        this.element = element;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    @Override
    public String toString() {
        return "Node{" + "element=" + element + ", next=" + next + ", prev=" + prev + '}';
    }

}
