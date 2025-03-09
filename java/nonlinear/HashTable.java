package nonlinear;

import java.util.TreeMap;

/**
 * 哈希表数据结构的Java实现
 * 使用链地址法解决哈希冲突
 */
public class HashTable<K, V> {
    // 哈希表节点类
    private class Node {
        public K key;
        public V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private static final int[] CAPACITY = {
        53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593,
        49157, 98317, 196613, 393241, 786433, 1572869, 3145739, 6291469,
        12582917, 25165843, 50331653, 100663319, 201326611, 402653189, 805306457, 1610612741
    };
    private static final int UPPER_TOL = 10;  // 上界
    private static final int LOWER_TOL = 2;   // 下界
    private int capacityIndex = 0;            // 容量索引

    private TreeMap<K, V>[] hashtable;
    private int size;
    private int M;  // 哈希表大小

    /**
     * 构造函数
     */
    @SuppressWarnings("unchecked")
    public HashTable() {
        this.M = CAPACITY[capacityIndex];
        size = 0;
        hashtable = new TreeMap[M];
        for (int i = 0; i < M; i++) {
            hashtable[i] = new TreeMap<>();
        }
    }

    /**
     * 哈希函数
     * @param key 键
     * @return 哈希值
     */
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    /**
     * 获取哈希表中的元素个数
     * 时间复杂度 O(1)
     * @return 元素个数
     */
    public int getSize() {
        return size;
    }

    /**
     * 向哈希表中添加元素
     * 时间复杂度 O(1)
     * @param key 键
     * @param value 值
     */
    public void add(K key, V value) {
        TreeMap<K, V> map = hashtable[hash(key)];
        if (map.containsKey(key)) {
            map.put(key, value);
        } else {
            map.put(key, value);
            size++;

            // 动态扩容
            if (size >= UPPER_TOL * M && capacityIndex + 1 < CAPACITY.length) {
                capacityIndex++;
                resize(CAPACITY[capacityIndex]);
            }
        }
    }

    /**
     * 从哈希表中删除元素
     * 时间复杂度 O(1)
     * @param key 键
     * @return 被删除的值
     */
    public V remove(K key) {
        TreeMap<K, V> map = hashtable[hash(key)];
        V ret = null;
        if (map.containsKey(key)) {
            ret = map.remove(key);
            size--;

            // 动态缩容
            if (size < LOWER_TOL * M && capacityIndex - 1 >= 0) {
                capacityIndex--;
                resize(CAPACITY[capacityIndex]);
            }
        }
        return ret;
    }

    /**
     * 更新哈希表中的元素
     * 时间复杂度 O(1)
     * @param key 键
     * @param value 新值
     */
    public void set(K key, V value) {
        TreeMap<K, V> map = hashtable[hash(key)];
        if (!map.containsKey(key)) {
            throw new IllegalArgumentException(key + " doesn't exist!");
        }
        map.put(key, value);
    }

    /**
     * 查询哈希表中是否包含某个键
     * 时间复杂度 O(1)
     * @param key 键
     * @return 是否包含
     */
    public boolean contains(K key) {
        return hashtable[hash(key)].containsKey(key);
    }

    /**
     * 获取哈希表中键对应的值
     * 时间复杂度 O(1)
     * @param key 键
     * @return 值
     */
    public V get(K key) {
        return hashtable[hash(key)].get(key);
    }

    /**
     * 重新设置哈希表大小
     * 时间复杂度 O(n)
     * @param newM 新大小
     */
    @SuppressWarnings("unchecked")
    private void resize(int newM) {
        TreeMap<K, V>[] newHashTable = new TreeMap[newM];
        for (int i = 0; i < newM; i++) {
            newHashTable[i] = new TreeMap<>();
        }

        int oldM = M;
        this.M = newM;
        for (int i = 0; i < oldM; i++) {
            TreeMap<K, V> map = hashtable[i];
            for (K key : map.keySet()) {
                newHashTable[hash(key)].put(key, map.get(key));
            }
        }

        this.hashtable = newHashTable;
    }

    /**
     * 使用示例
     */
    public static void main(String[] args) {
        // 创建哈希表
        HashTable<String, Integer> hashTable = new HashTable<>();

        // 添加元素
        hashTable.add("apple", 10);
        hashTable.add("banana", 20);
        hashTable.add("orange", 30);

        // 查询元素
        System.out.println("apple: " + hashTable.get("apple"));
        System.out.println("banana: " + hashTable.get("banana"));
        System.out.println("orange: " + hashTable.get("orange"));

        // 更新元素
        hashTable.set("apple", 15);
        System.out.println("apple after update: " + hashTable.get("apple"));

        // 删除元素
        hashTable.remove("banana");
        System.out.println("contains banana: " + hashTable.contains("banana"));
        System.out.println("size: " + hashTable.getSize());
    }
}