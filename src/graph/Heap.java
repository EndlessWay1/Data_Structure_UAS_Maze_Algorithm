package graph;

public class Heap<K extends Comparable<? super K>,V> {
    //Attributes
    private MyArrayList<BTNode<K,V>> arrList;
    private boolean priority;

    //membuat array list dan mengeset apakah heap biasa (heap max)
    //atau heap dengan priority (heap min)
    public Heap(int capacity, boolean priority) {
        arrList = new MyArrayList<BTNode<K,V>>(capacity);
        this.priority = priority;
    }
    
    public MyArrayList<BTNode<K,V>> getArray(){
        return arrList;
    }

    //mengembalikan jumlah elemen di heap
    public int size() {
        return arrList.size();
    }
    //mengembalikan bagian value (data/informasi) 
    //dari node berdasarkan index
    public V getData(int index) {
        return arrList.get(index).getData();
    }
    //mengembalikan bagian value (data/informasi) 
    //dari node berdasarkan value yang diberikan
    public V getData(BTNode<K,V> node) {
        return node.getData();
    }
    //mengembalikan bagian key dari node 
    //berdasarkan index
    public K getKey(int index) {
        return arrList.get(index).getKey();
    }
    //mengembalikan bagian key dari node 
    //berdasarkan value yang diberikan
    public K getKey(BTNode<K,V> node) {
        return node.getKey();
    }
    //menambahkan node <key,value> ke heap
    //tanpa heapify
    public void add(K key, V data) {
        arrList.add(new BTNode<K,V>(key, data));
    }
    //menyisipan node <key,value> ke heap
    //dengan heapify (max atau min)
    public void insert(K key, V data) {
        arrList.add(new BTNode<K,V>(key, data));
        int size = arrList.size();
        for (int i = size / 2 - 1; i >= 0; i = (i+1)/2 - 1) {
            if(priority) heapifyMin(size, i);
            else heapifyMax(size, i);
        }
    }
    //membuat heap
    public void buildHeap() {
        int size = arrList.size();

        // build heapSort (rearrange array)
        for (int i = size / 2 - 1; i >= 0; i--) {
            if(priority) heapifyMin(size, i);
            else heapifyMax(size, i);
        }
    }
    //heapsort
    public void sort() {
        int size = arrList.size();

        // build heapSort (rearrange array)
        buildHeap();

        // one by one extract an element from heapSort
            for (int i = size - 1; i >= 0; i--){
                // swap current root node to rightmost leaf node
                BTNode<K,V> temp = arrList.get(0);
                arrList.set(0, arrList.get(i));
                arrList.set(i, temp);

                // call max or min heapify on the reduced heap
                if(priority) heapifyMin(i, 0);
                else heapifyMax(i, 0);
            }
    }
    // to max heapify a subtree rooted at node i
    void heapifyMax(int size, int i)
    {
        int parent   = i; // initialize parent node
        int left  = 2 * i + 1; // initialize left child node
        int right = 2 * i + 2; // initialize right child node

        // if left child is larger than parent
        if (left < size && arrList.get(left).getKey().compareTo(arrList.get(parent).getKey()) > 0)
            parent = left;

        // if right child is larger than parent
        if (right < size && arrList.get(right).getKey().compareTo(arrList.get(parent).getKey()) > 0)
            parent = right;

        // if parent is not root
        if (parent != i)
        {
            // swap
            BTNode<K,V> temp = arrList.get(i);
            arrList.set(i, arrList.get(parent));
            arrList.set(parent, temp);

            // recursively heapify the affected sub-tree
            heapifyMax(size, parent);
        }
    }
    // to min heapify a subtree rooted at node i
    void heapifyMin(int size, int i) {
        int parent   = i;
        int left  = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < size && arrList.get(left).getKey().compareTo(arrList.get(parent).getKey()) < 0)
            parent = left;

        if (right < size && arrList.get(right).getKey().compareTo(arrList.get(parent).getKey()) < 0)
            parent = right;

        if (parent != i) {
            BTNode<K,V> temp = arrList.get(i);
            arrList.set(i, arrList.get(parent));
            arrList.set(parent, temp);

            heapifyMin(size, parent);
        }
    }

    //mengembalikan root node (tidak menghapus)
    public BTNode<K,V> first() {
        if(arrList.isEmpty()) return null;
        return arrList.get(0);
    }
    //mengembalikan root node dan menghapusnya dari heap
    public BTNode<K,V> removeFirst() {
        int n = arrList.size() - 1;

        // move current root to end
        BTNode<K,V> temp = arrList.get(0);
        arrList.set(0, arrList.get(n));
        arrList.set (n, temp);

        // call max or min heapify on the reduced heapSort
        if(priority) heapifyMin(n, 0);
        else heapifyMax(n, 0);

        //delete min dan kurangi ukuran heap
        arrList.remove(n);

        return temp;
    }
    
    /* A utility function to print array of size n */
    public void display()
    {
        int n = arrList.size();
        for(int i = 0; i < n; i++)
            System.out.println(arrList.get(i).getData());
    }
}

/*
 * Generic Array List: Creating Our own version of Java's ArrayList
 * Generic Array List ini unchecked (weak typing) generic data type
 */

class MyArrayList <T> {
    /*
     * Prinsip Encapsulation dari OOP:
     * 1. semua variabel dari class HARUS private
     *    (hanya dapat diakses oleh classnya sendiri)
     * 2. akses (set dan get) variabel melalui public method
     *    yang dibuat di class ini.
     */
    private Object[] thelist;
    private int n;
    private int max_size;

    //Constructor
    public MyArrayList(int max_size) {
        thelist = new Object[max_size];
        n = 0;
        this.max_size = max_size;
    }
    //get reference to thelist
    public Object[] getThelist() {
        return thelist;
    }
    //mengembalikan ukuran maksimum array
    public int maxSize() {
        return max_size;
    }
    //mengembalikan jumlah elemen array saat ini
    public int size() {
        return n;
    }
    //mengembalikan true jika array list sudah penuh
    private boolean isFull() {
        if(n == max_size) return true;
        else return false;
    }
    //mengembalikam true jika array list masih kosong
    public boolean isEmpty() {
        if(n == 0) return true;
        else return false;
    }
    //menambahkan data dengan ke posisi akhir di array list
    public void add(T value) {
        if(!isFull()) {
            thelist[n] = value;
            n = n + 1;
        }
        else {
            System.out.println("List sudah penuh!");
        }
    }
    //menyisipkan data ke posisi index tertentu di array list
    public void add(int index, T value) {
        if(index >= 0 && !isFull()) {
            n = n + 1;
            int i = n;
            do {
                thelist[i] = thelist[i-1];
                i = i - 1;
            }while(i > index);
            thelist[index] = value;
        }
        else {
            System.out.println("List sudah penuh!");
        }
    }

    public void remove(int index) {
        if(index >= 0 && !isEmpty()) {
            for(int i = index; i < n-1; i++) 
                thelist[i] = thelist[i+1];
            thelist[n-1] = null;
            n = n - 1;
        }
    }

    public T get(int i) {
        @SuppressWarnings("unchecked")
        final T e = (T) thelist[i];
        return e;
    }

    public void set(int index, T value) {
        thelist[index] = value;
    }

    public void clear() {
        if(!isEmpty()) {
            for(int i = 0; i < n; i++) 
                thelist[i] = null;    
            n = 0;
        }
    }

    public void cetakList() {
        //jika list kosong, tampilkan pesan list kosong
		if(isEmpty()) System.out.println("List kosong!");
        // jika list tidak kosong, maka cetak elemen pada list
		else {
            System.out.print("[ ");
            for(int i = 0; i < n; i++) {
				System.out.print(thelist[i].toString() + " ");
			}
            System.out.println("]");
		}
    }
}

