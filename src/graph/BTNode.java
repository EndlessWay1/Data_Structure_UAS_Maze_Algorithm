package graph;

public class BTNode<K, V> {
    private K key;
    private V data;

    public BTNode(K key, V data) {
        this.key = key;
        this.data = data;
    }

    public K getKey() {
        return key;
    }

    public V getData() {
        return data;
    }

    public String toString() {
        return "(" + key.toString() + ": " + data.toString() + ")";
    }
}