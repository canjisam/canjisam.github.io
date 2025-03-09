package nonlinear.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树数据结构的Java实现
 */
public class BinaryTree<E> {
    // 节点类，内部类
    protected class Node {
        public E data;       // 节点数据
        public Node left;    // 左子节点
        public Node right;   // 右子节点

        public Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    protected Node root;  // 根节点
    protected int size;   // 树中节点个数

    /**
     * 构造函数
     */
    public BinaryTree() {
        root = null;
        size = 0;
    }

    /**
     * 获取树中的节点个数
     * 时间复杂度 O(1)
     * @return 节点个数
     */
    public int getSize() {
        return size;
    }

    /**
     * 返回树是否为空
     * 时间复杂度 O(1)
     * @return 树是否为空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        preOrder(root);
    }

    /**
     * 前序遍历以node为根的二叉树
     * 时间复杂度 O(n)
     * @param node 当前节点
     */
    private void preOrder(Node node) {
        if (node == null) {
            return;
        }

        System.out.print(node.data + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * 中序遍历
     */
    public void inOrder() {
        inOrder(root);
    }

    /**
     * 中序遍历以node为根的二叉树
     * 时间复杂度 O(n)
     * @param node 当前节点
     */
    private void inOrder(Node node) {
        if (node == null) {
            return;
        }

        inOrder(node.left);
        System.out.print(node.data + " ");
        inOrder(node.right);
    }

    /**
     * 后序遍历
     */
    public void postOrder() {
        postOrder(root);
    }

    /**
     * 后序遍历以node为根的二叉树
     * 时间复杂度 O(n)
     * @param node 当前节点
     */
    private void postOrder(Node node) {
        if (node == null) {
            return;
        }

        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.data + " ");
    }

    /**
     * 层序遍历
     * 时间复杂度 O(n)
     */
    public void levelOrder() {
        if (root == null) {
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node cur = queue.remove();
            System.out.print(cur.data + " ");

            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
        }
    }

    /**
     * 获取二叉树的最大深度
     * 时间复杂度 O(n)
     * @return 最大深度
     */
    public int maxDepth() {
        return maxDepth(root);
    }

    /**
     * 获取以node为根节点的二叉树的最大深度
     * @param node 当前节点
     * @return 最大深度
     */
    private int maxDepth(Node node) {
        if (node == null) {
            return 0;
        }

        int leftDepth = maxDepth(node.left);
        int rightDepth = maxDepth(node.right);

        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * 判断是否是完全二叉树
     * 时间复杂度 O(n)
     * @return 是否是完全二叉树
     */
    public boolean isComplete() {
        if (root == null) {
            return true;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        boolean reachedNull = false;

        while (!queue.isEmpty()) {
            Node cur = queue.remove();
            
            if (cur == null) {
                reachedNull = true;
                continue;
            }
            
            if (reachedNull) {
                return false;
            }
            
            queue.add(cur.left);
            queue.add(cur.right);
        }
        
        return true;
    }

    /**
     * 使用示例
     */
    public static void main(String[] args) {
        // 创建一个简单的二叉树用于测试
        // 这里只是示例，实际应用中可能需要更复杂的构建方法
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.root = tree.new Node(1);
        tree.root.left = tree.new Node(2);
        tree.root.right = tree.new Node(3);
        tree.root.left.left = tree.new Node(4);
        tree.root.left.right = tree.new Node(5);
        tree.size = 5;

        System.out.println("前序遍历:");
        tree.preOrder();
        System.out.println();

        System.out.println("中序遍历:");
        tree.inOrder();
        System.out.println();

        System.out.println("后序遍历:");
        tree.postOrder();
        System.out.println();

        System.out.println("层序遍历:");
        tree.levelOrder();
        System.out.println();

        System.out.println("树的最大深度: " + tree.maxDepth());
        System.out.println("是否是完全二叉树: " + tree.isComplete());
    }
}