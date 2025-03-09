package linear;

/**
 * 栈数据结构的Java实现
 * 实现了后进先出(LIFO)的线性表
 */
public class Stack<E> {
    private Array<E> array;  // 使用之前实现的动态数组作为底层数据结构

    /**
     * 构造函数
     * @param capacity 栈的容量
     */
    public Stack(int capacity) {
        array = new Array<>(capacity);
    }

    /**
     * 无参数的构造函数，默认容量为10
     */
    public Stack() {
        array = new Array<>();
    }

    /**
     * 获取栈中的元素个数
     * 时间复杂度 O(1)
     * @return 元素个数
     */
    public int getSize() {
        return array.getSize();
    }

    /**
     * 返回栈是否为空
     * 时间复杂度 O(1)
     * @return 栈是否为空
     */
    public boolean isEmpty() {
        return array.isEmpty();
    }

    /**
     * 获取栈的容量
     * 时间复杂度 O(1)
     * @return 栈的容量
     */
    public int getCapacity() {
        return array.getCapacity();
    }

    /**
     * 将元素压入栈顶
     * 时间复杂度 O(1) 均摊
     * @param e 待压入元素
     */
    public void push(E e) {
        array.addLast(e);
    }

    /**
     * 弹出栈顶元素
     * 时间复杂度 O(1) 均摊
     * @return 栈顶元素
     */
    public E pop() {
        return array.removeLast();
    }

    /**
     * 查看栈顶元素
     * 时间复杂度 O(1)
     * @return 栈顶元素
     */
    public E peek() {
        return array.getLast();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Stack: ");
        res.append('[');
        for (int i = 0; i < array.getSize(); i++) {
            res.append(array.get(i));
            if (i != array.getSize() - 1) {
                res.append(", ");
            }
        }
        res.append(']');
        res.append(" top");
        return res.toString();
    }

    /**
     * 使用示例：括号匹配问题
     */
    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }

                char topChar = stack.pop();
                if (c == ')' && topChar != '(') {
                    return false;
                }
                if (c == ']' && topChar != '[') {
                    return false;
                }
                if (c == '}' && topChar != '{') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    /**
     * 使用示例
     */
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < 5; i++) {
            stack.push(i);
            System.out.println(stack);
        }

        stack.pop();
        System.out.println(stack);

        // 括号匹配示例
        System.out.println(isValid("()[]{}"));
        System.out.println(isValid("([)]"));
    }
}