package nonlinear.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉搜索树数据结构的Java实现
 * 二叉搜索树是一种特殊的二叉树，其中每个节点的左子树中的值都小于该节点的值，
 * 右子树中的值都大于该节点的值
 */
public class BST<E extends Comparable<E>> {
    // 节点类，私有内部类
    private class Node {
        public E data;       // 节点数据
        public Node left;    // 左子节点
        public Node right;   // 右子节点

        public Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;  // 根节点
    private int size;   // 树中节点个数

    /**
     * 构造函数
     */
    public BST() {
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
     * 向二叉搜索树中添加新的元素e
     * 时间复杂度 O(log n)
     * @param e 待添加元素
     */
    public void add(E e) {
        root = add(root, e);
    }

    /**
     * 向以node为根的二叉搜索树中插入元素e，递归算法
     * 返回插入新节点后二叉搜索树的根
     * @param node 当前子树的根节点
     * @param e 待插入元素
     * @return 插入后的子树根节点
     */
    private Node add(Node node, E e) {
        if (node == null) {
            size++;
            return new Node(e);
        }

        if (e.compareTo(node.data) < 0) {
            node.left = add(node.left, e);
        } else if (e.compareTo(node.data) > 0) {
            node.right = add(node.right, e);
        }

        return node;
    }

    /**
     * 查看二叉搜索树中是否包含元素e
     * 时间复杂度 O(log n)
     * @param e 待查找元素
     * @return 是否包含元素e
     */
    public boolean contains(E e) {
        return contains(root, e);
    }

    /**
     * 查看以node为根的二叉搜索树中是否包含元素e，递归算法
     * @param node 当前子树的根节点
     * @param e 待查找元素
     * @return 是否包含元素e
     */
    private boolean contains(Node node, E e) {
        if (node == null) {
            return false;
        }

        if (e.compareTo(node.data) == 0) {
            return true;
        } else if (e.compareTo(node.data) < 0) {
            return contains(node.left, e);
        } else {
            return contains(node.right, e);
        }
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        preOrder(root);
    }

    /**
     * 前序遍历以node为根的二叉搜索树，递归算法
     * @param node 当前子树的根节点
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
     * 中序遍历以node为根的二叉搜索树，递归算法
     * @param node 当前子树的根节点
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
     * 后序遍历以node为根的二叉搜索树，递归算法
     * @param node 当前子树的根节点
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
     * 寻找二叉搜索树的最小元素
     * 时间复杂度 O(log n)
     * @return 最小元素
     */
    public E minimum() {
        if (size == 0) {
            throw new IllegalArgumentException("BST is empty");
        }

        return minimum(root).data;
    }

    /**
     * 返回以node为根的二叉搜索树的最小值所在的节点
     * @param node 当前子树的根节点
     * @return 最小值所在的节点
     */
    private Node minimum(Node node) {
        if (node.left == null) {
            return node;
        }
        return minimum(node.left);
    }

    /**
     * 寻找二叉搜索树的最大元素
     * 时间复杂度 O(log n)
     * @return 最大元素
     */
    public E maximum() {
        if (size == 0) {
            throw new IllegalArgumentException("BST is empty");
        }

        return maximum(root).data;
    }

    /**
     * 返回以node为根的二叉搜索树的最大值所在的节点
     * @param node 当前子树的根节点
     * @return 最大值所在的节点
     */
    private Node maximum(Node node) {
        if (node.right == null) {
            return node;
        }
        return maximum(node.right);
    }

    /**
     * 从二叉搜索树中删除最小值所在节点，返回最小值
     * 时间复杂度 O(log n)
     * @return 最小值
     */
    public E removeMin() {
        E ret = minimum();
        root = removeMin(root);
        return ret;
    }

    /**
     * 删除以node为根的二叉搜索树中的最小节点
     * 返回删除节点后新的二叉搜索树的根
     * @param node 当前子树的根节点
     * @return 删除后的子树根节点
     */
    private Node removeMin(Node node) {
        if (node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    /**
     * 从二叉搜索树中删除最大值所在节点，返回最大值
     * 时间复杂度 O(log n)
     * @return 最大值
     */
    public E removeMax() {
        E ret = maximum();
        root = removeMax(root);
        return ret;
    }

    /**
     * 删除以node为根的二叉搜索树中的最大节点
     * 返回删除节点后新的二叉搜索树的根
     * @param node 当前子树的根节点
     * @return 删除后的子树根节点
     */
    private Node removeMax(Node node) {
        if (node.right == null) {
            Node leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }

        node.right = removeMax(node.right);
        return node;
    }

    /**
     * 从二叉搜索树中删除元素为e的节点
     * 时间复杂度 O(log n)
     * @param e 待删除元素
     */
    public void remove(E e) {
        root = remove(root, e);
    }

    /**
     * 删除以node为根的二叉搜索树中值为e的节点，递归算法
     * 返回删除节点后新的二叉搜索树的根
     * @param node 当前子树的根节点
     * @param e 待删除元素
     * @return 删除后的子树根节点
     */
    private Node remove(Node node, E e) {
        if (node == null) {
            return null;
        }

        if (e.compareTo(node.data) < 0) {
            node.left = remove(node.left, e);
            return node;
        } else if (e.compareTo(node.data) > 0) {
            node.right = remove(node.right, e);
            return node;
        } else {  // e.compareTo(node.data) == 0
            // 待删除节点左子树为空的情况
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }

            // 待删除节点右子树为空的情况
            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }

            // 待删除节点左右子树均不为空的情况
            // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
            // 用这个节点顶替待删除节点的位置
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;

            node.left = node.right = null;

            return successor;
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        generateBSTString(root, 0, res);
        return res.toString();
    }

    /**
     * 生成以node为根节点，深度为depth的描述二叉树的字符串
     * @param node 当前节点
     * @param depth 当前深度
     * @param res 结果字符串
     */
    private void generateBSTString(Node node, int depth, StringBuilder res) {
        if (node == null) {
            res.append(generateDepthString(depth)).append("null\n");
            return;
        }

        res.append(generateDepthString(depth)).append(node.data).append("\n");
        generateBSTString(node.left, depth + 1, res);
        generateBSTString(node.right, depth + 1, res);
    }

    /**
     * 生成深度字符串
     * @param depth 深度
     * @return 深度字符串
     */
    private String generateDepthString(int depth) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            res.append("--");
        }
        return res.toString();
    }

    /**
     * 使用示例
     */
    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        int[] nums = {5, 3, 6, 8, 4, 2};
        for (int num : nums) {
            bst.add(num);
        }

        // 前序遍历
        System.out.println("前序遍历:");
        bst.preOrder();
        System.out.println();

        // 中序遍历
        System.out.println("中序遍历:");
        bst.inOrder();