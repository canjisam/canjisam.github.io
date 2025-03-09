package nonlinear;

import java.util.Arrays;

/**
 * 堆数据结构的Java实现
 * 这里实现的是最大堆，即父节点的值总是大于或等于其子节点的值
 */
public class Heap<E extends Comparable<E>> {
    private Array<E> data;  // 使用动态数组存储堆中的元素

    /**
     * 构造函数
     * @param capacity 堆的容量
     */
    public Heap(int capacity) {
        data = new Array<>(capacity);
    }

    /**
     * 无参数的构造函数，默认容量为10
     */
    public Heap() {
        data = new Array<>();
    }

    /**
     * 使用已有数组构建堆
     * 时间复杂度 O(n)
     * @param arr 已有数组
     */
    public Heap(E[] arr) {
        data = new Array<>(arr.length);
        for (E e : arr) {
            data.addLast(e);
        }

        // 从最后一个非叶子节点开始进行下沉操作
        for (int i = parent(data.getSize() - 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * 返回堆中的元素个数
     * 时间复杂度 O(1)
     * @return 元素个数
     */
    public int size() {
        return data.getSize();
    }

    /**
     * 返回堆是否为空
     * 时间复杂度 O(1)
     * @return 堆是否为空
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * 返回完全二叉树的数组表示中，一个索引所表示的元素的父亲节点的索引
     * @param index 当前索引
     * @return 父节点索引
     */
    private int parent(int index) {
        if (index == 0) {
            throw new IllegalArgumentException("index-0 doesn't have parent.");
        }
        return (index - 1) / 2;
    }

    /**
     * 返回完全二叉树的数组表示中，一个索引所表示的元素的左孩子节点的索引
     * @param index 当前索引
     * @return 左孩子索引
     */
    private int leftChild(int index) {
        return index * 2 + 1;
    }

    /**
     * 返回完全二叉树的数组表示中，一个索引所表示的元素的右孩子节点的索引
     * @param index 当前索引
     * @return 右孩子索引
     */
    private int rightChild(int index) {
        return index * 2 + 2;
    }

    /**
     * 向堆中添加元素
     * 时间复杂度 O(log n)
     * @param e 待添加元素
     */
    public void add(E e) {
        data.addLast(e);
        siftUp(data.getSize() - 1);
    }

    /**
     * 上浮操作，将索引为k的元素上浮到合适位置
     * @param k 元素索引
     */
    private void siftUp(int k) {
        // 当k大于0且父节点小于当前节点时，交换位置
        while (k > 0 && data.get(parent(k)).compareTo(data.get(k)) < 0) {
            data.swap(k, parent(k));
            k = parent(k);
        }
    }

    /**
     * 查看堆中的最大元素
     * 时间复杂度 O(1)
     * @return 最大元素
     */
    public E findMax() {
        if (data.getSize() == 0) {
            throw new IllegalArgumentException("Heap is empty.");
        }
        return data.get(0);
    }

    /**
     * 取出堆中最大元素
     * 时间复杂度 O(log n)
     * @return 最大元素
     */
    public E extractMax() {
        E ret = findMax();

        // 将最后一个元素放到堆顶，然后下沉
        data.swap(0, data.getSize() - 1);
        data.removeLast();
        siftDown(0);

        return ret;
    }

    /**
     * 下沉操作，将索引为k的元素下沉到合适位置
     * @param k 元素索引
     */
    private void siftDown(int k) {
        // 当左孩子索引小于数组大小时
        while (leftChild(k) < data.getSize()) {
            int j = leftChild(k);
            // 如果右孩子存在且右孩子大于左孩子，则j指向右孩子
            if (j + 1 < data.getSize() && data.get(j + 1).compareTo(data.get(j)) > 0) {
                j++;
            }
            // 如果当前元素大于等于左右孩子中的最大值，则停止下沉
            if (data.get(k).compareTo(data.get(j)) >= 0) {
                break;
            }
            // 否则交换位置，继续下沉
            data.swap(k, j);
            k = j;
        }
    }

    /**
     * 取出堆中的最大元素，并且替换成元素e
     * 时间复杂度 O(log n)
     * @param e 新元素
     * @return 原堆顶元素
     */
    public E replace(E e) {
        E ret = findMax();
        data.set(0, e);
        siftDown(0);
        return ret;
    }

    /**
     * 辅助类：动态数组
     */
    private class Array<T> {
        private T[] data;
        private int size;
        private static final int DEFAULT_CAPACITY = 10;

        @SuppressWarnings("unchecked")
        public Array(int capacity) {
            data = (T[]) new Object[capacity];
            size = 0;
        }

        public Array() {
            this(DEFAULT_CAPACITY);
        }

        public int getSize() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public void addLast(T e) {
            if (size == data.length) {
                resize(2 * data.length);
            }
            data[size++] = e;
        }

        public T get(int index) {
            if (index < 0 || index >= size) {
                throw new IllegalArgumentException("Get failed. Index is illegal.");
            }
            return data[index];
        }

        public void set(int index, T e) {
            if (index < 0 || index >= size) {
                throw new IllegalArgumentException("Set failed. Index is illegal.");
            }
            data[index] = e;
        }

        public T removeLast() {
            if (size == 0) {
                throw new IllegalArgumentException("RemoveLast failed. Array is empty.");
            }
            T ret = data[--size];
            data[size] = null;
            if (size == data.length / 4 && data.length / 2 != 0) {
                resize(data.length / 2);
            }
            return ret;
        }

        public void swap(int i, int j) {
            if (i < 0 || i >= size || j < 0 || j >= size) {
                throw new IllegalArgumentException("Swap failed. Index is illegal.");
            }
            T temp = data[i];
            data[i] = data[j];
            data[j] = temp;
        }

        @SuppressWarnings("unchecked")
        private void resize(int newCapacity) {
            T[] newData = (T[]) new Object[newCapacity];
            for (int i = 0; i < size; i++) {
                newData[i] = data[i];
            }
            data = newData;
        }
    }

    /**
     * 使用示例
     */
    public static void main(String[] args) {
        // 创建堆
        Heap<Integer> maxHeap = new Heap<>();
        int[] nums = {62, 41, 30, 28, 16, 22, 13, 19, 17, 15};
        for (int num : nums) {
            maxHeap.add(num);
        }

        // 取出元素，将按照从大到小的顺序输出
        int[] sortedNums = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            sortedNums[i] = maxHeap.extractMax();
        }
        System.out.println(Arrays.toString(sortedNums));

        // 使用heapify创建堆
        Integer[] nums2 = {62, 41, 30, 28, 16, 22, 13, 19, 17, 15};
        Heap<Integer> maxHeap2 = new Heap<>(nums2);
        int[] sortedNums2 = new int[nums2.length];
        for (int i = 0; i < nums2.length; i++) {
            sortedNums2[i] = maxHeap2.extractMax();
        }
        System.out.println(Arrays.toString(sortedNums2));
    }
}