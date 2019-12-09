package ds;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 自定义的有序Map数据结构
 *
 * @param <K>
 * @param <V>
 */
public class SortedMap<K, V> {
    public static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    private List<Entry<K, V>> elements;

    public SortedMap() {
        elements = new LinkedList<>();
    }

    public void put(K key, V value) {
        for (Entry<K, V> entry : this.elements) {
            if (entry.key.equals(key)) {
                entry.value = value;
                return;
            }
        }
        this.elements.add(new Entry<>(key, value));
    }

    public void putAll(SortedMap<K,V> sortedMap){
        this.elements.addAll(sortedMap.elements);
    }

    public void clear(){
        this.elements.clear();
    }

    public Entry<K, V> get(int index) {
        return this.elements.get(index);
    }

    public V getValue(K k) {
        for (Entry<K, V> entry : this.elements) {
            if (entry.key.equals(k)) return entry.value;
        }
        return null;
    }

    public Iterator<Entry<K,V>> iterator(){
        return this.elements.iterator();
    }

    public boolean isEmpty(){
        return this.elements.isEmpty();
    }
}
