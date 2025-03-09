package linear;

/**
 * 数组数据结构的Java实现
 * 实现了动态数组，可以自动扩容
 */
public class Array<E> {
    private E[] data;      // 存储数据的数组
    private int size;      // 数组中元素的个数
    private static final int DEFAULT_CAPACITY = 10;  // 默认容量

    /**
     * 构造函数，传入数组的容量capacity构造Array
     * @param capacity 数组容量
     */
    @SuppressWarnings("unchecked")
    public Array(int capacity) {
        data = (E[]) new Object[capacity];
        size = 0;
    }

    /**
     * 无参数的构造函数，默认数组的容量capacity=10
     */
    public Array() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 获取数组中的元素个数
     * @return 元素个数
     */
    public int getSize() {
        return size;
    }

    /**
     * 获取数组的容量
     * @return 数组容量
     */
    public int getCapacity() {
        return data.length;
    }

    /**
     * 返回数组是否为空
     * @return 数组是否为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 在index索引的位置插入一个新元素e
     * 时间复杂度 O(n)
     * @param index 索引位置
     * @param e 待插入元素
     */
    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Require index >= 0 and index <= size.");
        }

        if (size == data.length) {
            resize(2 * data.length);
        }

        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }

        data[index] = e;
        size++;
    }

    /**
     * 向所有元素后添加一个新元素
     * 时间复杂度 O(1)
     * @param e 待添加元素
     */
    public void addLast(E e) {
        add(size, e);
    }

    /**
     * 在所有元素前添加一个新元素
     * 时间复杂度 O(n)
     * @param e 待添加元素
     */
    public void addFirst(E e) {
        add(0, e);
    }

    /**
     * 获取index索引位置的元素
     * 时间复杂度 O(1)
     * @param index 索引位置
     * @return 元素值
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Get failed. Index is illegal.");
        }
        return data[index];
    }

    /**
     * 获取第一个元素
     * 时间复杂度 O(1)
     * @return 第一个元素
     */
    public E getFirst() {
        return get(0);
    }

    /**
     * 获取最后一个元素
     * 时间复杂度 O(1)
     * @return 最后一个元素
     */
    public E getLast() {
        return get(size - 1);
    }

    /**
     * 修改index索引位置的元素为e
     * 时间复杂度 O(1)
     * @param index 索引位置
     * @param e 新元素值
     */
    public void set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Set failed. Index is illegal.");
        }
        data[index] = e;
    }

    /**
     * 查找数组中是否有元素e
     * 时间复杂度 O(n)
     * @param e 待查找元素
     * @return 是否包含元素e
     */
    public boolean contains(E e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找数组中元素e所在的索引，如果不存在元素e，则返回-1
     * 时间复杂度 O(n)
     * @param e 待查找元素
     * @return 元素索引或-1
     */
    public int find(E e) {
        for (int i = 0; i < size; i++) {
            if (data[i].equals(e)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 从数组中删除index位置的元素, 返回删除的元素
     * 时间复杂度 O(n)
     * @param index 索引位置
     * @return 被删除的元素
     */
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Remove failed. Index is illegal.");
        }

        E ret = data[index];
        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }
        size--;
        data[size] = null;  // loitering objects != memory leak

        // 当数组元素数量为容量的1/4时，缩容为原来的1/2
        if (size == data.length / 4 && data.length / 2 != 0) {
            resize(data.length / 2);
        }
        return ret;
    }

    /**
     * 从数组中删除第一个元素, 返回删除的元素
     * 时间复杂度 O(n)
     * @return 被删除的元素
     */
    public E removeFirst() {
        return remove(0);
    }

    /**
     * 从数组中删除最后一个元素, 返回删除的元素
     * 时间复杂度 O(1)
     * @return 被删除的元素
     */
    public E removeLast() {
        return remove(size - 1);
    }

    /**
     * 从数组中删除元素e
     * 时间复杂度 O(n)
     * @param e 待删除元素
     */
    public void removeElement(E e) {
        int index = find(e);
        if (index != -1) {
            remove(index);
        }
    }

    /**
     * 将数组空间的容量变成newCapacity大小
     * 时间复杂度 O(n)
     * @param newCapacity 新容量大小
     */
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array: size = %d, capacity = %d\n", size, data.length));
        res.append('[');
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if (i != size - 1) {
                res.append(", ");
            }
        }
        res.append(']');
        return res.toString();
    }

    /**
     * 使用示例
     */
    public static void main(String[] args) {
        Array<Integer> arr = new Array<>();
        for (int i = 0; i < 10; i++) {
            arr.addLast(i);
        }
        System.out.println(arr);

        arr.add(1, 100);
        System.out.println(arr);

        arr.addFirst(-1);
        System.out.println(arr);

        arr.remove(2);
        System.out.println(arr);

        arr.removeElement(4);
        System.out.println(arr);

        arr.removeFirst();
        System.out.println(arr);
    }
}