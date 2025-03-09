package linear;

/**
 * 链表数据结构的Java实现
 * 实现了单向链表的基本操作
 */
public class LinkedList<E> {
    // 节点类，私有内部类
    private class Node {
        public E data;       // 节点数据
        public Node next;    // 指向下一个节点的引用

        public Node(E data, Node next) {
            this.data = data;
            this.next = next;
        }

        public Node(E data) {
            this(data, null);
        }

        public Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    private Node dummyHead;  // 虚拟头节点
    private int size;        // 链表中元素的个数

    /**
     * 构造函数
     */
    public LinkedList() {
        dummyHead = new Node();
        size = 0;
    }

    /**
     * 获取链表中的元素个数
     * 时间复杂度 O(1)
     * @return 元素个数
     */
    public int getSize() {
        return size;
    }

    /**
     * 返回链表是否为空
     * 时间复杂度 O(1)
     * @return 链表是否为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 在链表的index位置添加新的元素e
     * 时间复杂度 O(n)
     * @param index 索引位置
     * @param e 待添加元素
     */
    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Add failed. Illegal index.");
        }

        Node prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        prev.next = new Node(e, prev.next);
        size++;
    }

    /**
     * 在链表头添加新的元素e
     * 时间复杂度 O(1)
     * @param e 待添加元素
     */
    public void addFirst(E e) {
        add(0, e);
    }

    /**
     * 在链表末尾添加新的元素e
     * 时间复杂度 O(n)
     * @param e 待添加元素
     */
    public void addLast(E e) {
        add(size, e);
    }

    /**
     * 获得链表的第index个位置的元素
     * 时间复杂度 O(n)
     * @param index 索引位置
     * @return 元素值
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Get failed. Illegal index.");
        }

        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.data;
    }

    /**
     * 获得链表的第一个元素
     * 时间复杂度 O(1)
     * @return 第一个元素
     */
    public E getFirst() {
        return get(0);
    }

    /**
     * 获得链表的最后一个元素
     * 时间复杂度 O(n)
     * @return 最后一个元素
     */
    public E getLast() {
        return get(size - 1);
    }

    /**
     * 修改链表的第index个位置的元素为e
     * 时间复杂度 O(n)
     * @param index 索引位置
     * @param e 新元素值
     */
    public void set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Set failed. Illegal index.");
        }

        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        cur.data = e;
    }

    /**
     * 查找链表中是否有元素e
     * 时间复杂度 O(n)
     * @param e 待查找元素
     * @return 是否包含元素e
     */
    public boolean contains(E e) {
        Node cur = dummyHead.next;
        while (cur != null) {
            if (cur.data.equals(e)) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    /**
     * 从链表中删除index位置的元素，返回删除的元素
     * 时间复杂度 O(n)
     * @param index 索引位置
     * @return 被删除的元素
     */
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Remove failed. Index is illegal.");
        }

        Node prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        Node retNode = prev.next;
        prev.next = retNode.next;
        retNode.next = null;
        size--;

        return retNode.data;
    }

    /**
     * 从链表中删除第一个元素，返回删除的元素
     * 时间复杂度 O(1)
     * @return 被删除的元素
     */
    public E removeFirst() {
        return remove(0);
    }

    /**
     * 从链表中删除最后一个元素，返回删除的元素
     * 时间复杂度 O(n)
     * @return 被删除的元素
     */
    public E removeLast() {
        return remove(size - 1);
    }

    /**
     * 从链表中删除元素e
     * 时间复杂度 O(n)
     * @param e 待删除元素
     */
    public void removeElement(E e) {
        Node prev = dummyHead;
        while (prev.next != null) {
            if (prev.next.data.equals(e)) {
                break;
            }
            prev = prev.next;
        }

        if (prev.next != null) {
            Node delNode = prev.next;
            prev.next = delNode.next;
            delNode.next = null;
            size--;
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("LinkedList: ");

        Node cur = dummyHead.next;
        while (cur != null) {
            res.append(cur).append(" -> ");
            cur = cur.next;
        }
        res.append("NULL");

        return res.toString();
    }

    /**
     * 使用示例
     */
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            linkedList.addFirst(i);
            System.out.println(linkedList);
        }

        linkedList.add(2, 666);
        System.out.println(linkedList);

        linkedList.remove(2);
        System.out.println(linkedList);

        linkedList.removeFirst();
        System.out.println(linkedList);

        linkedList.removeLast();
        System.out.println(linkedList);
    }
}