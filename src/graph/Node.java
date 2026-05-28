package graph;

public class Node<T> {
    private T data;
    private Node<T> next;

    // constructor
    Node(T value) {
        data = value;
        next = null;
    }

    // setters
    public void setData(T data) {
        this.data = data;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    // getters
    public T getData() {
        return data;
    }

    public Node<T> getNext() {
        return next;
    }
}