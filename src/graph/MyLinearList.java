package graph;

class Node<T> {
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

public class MyLinearList<T> {
    Node<T> head;
    Node<T> tail;
    int length = 0;

    // constructor
    public MyLinearList() {
        head = null;
        tail = null;
        length = 0;
    }

    // Node baru di akhir list (queue)
    public void pushQ(T value) {
        // buat node baru
        Node<T> newNode = new Node<>(value);
        // list kosong
        if (head == null) {
            head = newNode;
            tail = newNode;
        } 
        // List tidak kosong
        else {
            tail.setNext(newNode);
            tail = newNode;
        }
        length++;
    }

    // Node baru di awal list (stack)
    public void pushS(T value) {
        // buat node baru
        Node<T> newNode = new Node<T>(value);
        // List kosong
        if (head == null) {
            head = newNode;
            tail = newNode;
        } 
        // List tidak kosong
        else {
            newNode.setNext(head);
            head = newNode;
        }
        length++;
    }

    // Cetak bagian informasi untuk semua node
    public void cetakList() {
        Node<T> curr = head;
        if(curr == null) {
            System.out.println("List kosong!");
            return;
        } else {
            System.out.print("[ ");
            while(curr != null) {
                System.out.print(curr.getData().toString() + " ");
                curr = curr.getNext();
            }
            System.out.println("]");
        }
    }

    // pop dan return node pertama di queue dan stack
    public Node<T> pop() {
        Node<T> n;
        if(head == null) {
            n = null;
        } else {
            n = head;
            head = head.getNext();
            if (head == null) {
                tail = null;
            }
            length--;
        }
        return n;
    }

    // pop node pertama dan return datanya
    public T remove() {
        Node<T> n = pop();
        if(n == null) return null;
        else return n.getData();
    }

    // true jika node ditemukan, lalu node di-remove
    public boolean remove(T data) {
        Node<T> curr = head;
        Node<T> prev = head;
        boolean deleted = false;

        while(curr != null && !deleted) {
            // node ditemukan
            if(curr.getData().equals(data)) {
                deleted = true;
                length--;
                // update link node sebelum ke node setelah node dihapus
                prev.setNext(curr.getNext());
                // node pertama yang diremove, update head ke node kedua
                if(curr == head) head = head.getNext();
                // jika list hanya memiliki satu node
                if(head == null) tail = null;
            } 
            // jika belum ditemukan node yang akan diremove
            // simpan pointer ke node saat ini, dan update
            // pointer ke node berikutnya
            else {
                prev = curr;
                curr = curr.getNext();
            }
        }
        return deleted;
    }

    // mengecek apakah list kosong atau tidak
    public boolean isEmpty() {
        if(head == null) return true;
        else return false;
    }
}