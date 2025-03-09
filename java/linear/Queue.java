package linear;

/**
 * 队列数据结构的Java实现
 * 实现了先进先出(FIFO)的线性表
 */
public class Queue<E> {
    private Array<E> array;  // 使用之前实现的动态数组作为底层数据结构

    /**
     * 构造函数
     * @param capacity 队列的容量
     */
    public Queue(int capacity) {
        array = new Array<>(capacity);
    }

    /**
     * 无参数的构造函数，默认容量为10
     */
    public Queue() {
        array = new Array<>();
    }

    /**
     * 获取队列中的元素个数
     * 时间复杂度 O(1)
     * @return 元素个数
     */
    public int getSize() {
        return array.getSize();
    }

    /**
     * 返回队列是否为空
     * 时间复杂度 O(1)
     * @return 队列是否为空
     */
    public boolean isEmpty() {
        return array.isEmpty();
    }

    /**
     * 获取队列的容量
     * 时间复杂度 O(1)
     * @return 队列的容量
     */
    public int getCapacity() {
        return array.getCapacity();
    }

    /**
     * 入队操作，将元素添加到队列尾部
     * 时间复杂度 O(1) 均摊
     * @param e 待入队元素
     */
    public void enqueue(E e) {
        array.addLast(e);
    }

    /**
     * 出队操作，从队列头部取出元素
     * 时间复杂度 O(n)
     * @return 队首元素
     */
    public E dequeue() {
        return array.removeFirst();
    }

    /**
     * 查看队首元素
     * 时间复杂度 O(1)
     * @return 队首元素
     */
    public E getFront() {
        return array.getFirst();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Queue: front [");
        for (int i = 0; i < array.getSize(); i++) {
            res.append(array.get(i));
            if (i != array.getSize() - 1) {
                res.append(", ");
            }
        }
        res.append("] tail");
        return res.toString();
    }

    /**
     * 使用示例
     */
    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();

        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
            System.out.println(queue);

            if (i % 3 == 2) {
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }
}