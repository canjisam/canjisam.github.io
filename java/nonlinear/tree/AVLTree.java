package nonlinear.tree;

/**
 * 平衡二叉树(AVL树)的Java实现
 * AVL树是一种自平衡的二叉搜索树，其中任意节点的左右子树高度差不超过1
 */
public class AVLTree<E extends Comparable<E>> {
    // 节点类，私有内部类
    private class Node {
        public E data;       // 节点数据
        public Node left;    // 左子节点
        public Node right;   // 右子节点
        public int height;   // 节点高度

        public Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.height = 1;  // 新节点初始高度为1
        }
    }

    private Node root;  // 根节点
    private int size;   // 树中节点个数

    /**
     * 构造函数
     */
    public AVLTree() {
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
     * 获取节点高度
     * 时间复杂度 O(1)
     * @param node 节点
     * @return 节点高度
     */
    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    /**
     * 获取节点的平衡因子
     * 时间复杂度 O(1)
     * @param node 节点
     * @return 平衡因子
     */
    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    /**
     * 判断该二叉树是否是一棵平衡二叉树
     * 时间复杂度 O(n)
     * @return 是否是平衡二叉树
     */
    public boolean isBalanced() {
        return isBalanced(root);
    }

    /**
     * 判断以Node为根的二叉树是否是一棵平衡二叉树，递归算法
     * @param node 当前节点
     * @return 是否是平衡二叉树
     */
    private boolean isBalanced(Node node) {
        if (node == null) {
            return true;
        }

        int balanceFactor = getBalanceFactor(node);
        if (Math.abs(balanceFactor) > 1) {
            return false;
        }
        return isBalanced(node.left) && isBalanced(node.right);
    }

    /**
     * 对节点y进行向右旋转操作，返回旋转后新的根节点x
     *        y                              x
     *       / \                           /   \
     *      x   T4     向右旋转 (y)        z     y
     *     / \       - - - - - - - ->    / \   / \
     *    z   T3                       T1  T2 T3 T4
     *   / \
     * T1   T2
     * 时间复杂度 O(1)
     * @param y 需要旋转的节点
     * @return 旋转后新的根节点
     */
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T3 = x.right;

        // 向右旋转过程
        x.right = y;
        y.left = T3;

        // 更新height
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    /**
     * 对节点y进行向左旋转操作，返回旋转后新的根节点x
     *    y                             x
     *   / \                          /   \
     *  T1  x      向左旋转 (y)       y     z
     *     / \   - - - - - - - ->   / \   / \
     *    T2  z                    T1 T2 T3 T4
     *       / \
     *      T3 T4
     * 时间复杂度 O(1)
     * @param y 需要旋转的节点
     * @return 旋转后新的根节点
     */
    private Node leftRotate(Node y) {
        Node x = y.right;
        Node T2 = x.left;

        // 向左旋转过程
        x.left = y;
        y.right = T2;

        // 更新height
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
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
     * 返回插入新节点后AVL树的根
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
        } else {
            // 元素相等时不做任何处理
            return node;
        }

        // 更新height
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        // 计算平衡因子
        int balanceFactor = getBalanceFactor(node);

        // 平衡维护
        // LL
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        // RR
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        // LR
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
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
     * 从二叉搜索树中删除元素为e的节点
     * 时间复杂度 O(log n)
     * @param e 待删除元素
     */
    public void remove(E e) {
        root = remove(root, e);
    }

    /**
     * 删除以node为根的二叉搜索树中值为e的节点，递归算法
     * 返回删除节点后新的AVL树的根
     * @param node 当前子树的根节点
     * @param e 待删除元素
     * @return 删除后的子树根节点
     */
    private Node remove(Node node, E e) {
        if (node == null) {
            return null;
        }

        Node retNode;
        if (e.compareTo(node.data) < 0) {
            node.left = remove(node.left, e);
            retNode = node;
        } else if (e.compareTo(node.data) > 0) {
            node.right = remove(node.right, e);
            retNode = node;
        } else {  // e.compareTo(node.data) == 0
            // 待删除节点左子树为空的情况
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                retNode = rightNode;
            }
            // 待删除节点右子树为空的情况
            else if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                retNode = leftNode;
            } else {
                // 待删除节点左右子树均不为空的情况
                // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
                // 用这个节点顶替待删除节点的位置
                Node successor = minimum(node.right);
                successor.right = remove(node.right, successor.data);
                successor.left = node.left;

                node.left = node.right = null;

                retNode = successor;
            }
        }

        if (retNode == null) {
            return null;
        }

        // 更新height
        retNode.height = 1 + Math.max(getHeight(retNode.left), getHeight(retNode.right));

        // 计算平衡因子
        int balanceFactor = getBalanceFactor(retNode);

        // 平衡维护
        // LL
        if (balanceFactor > 1 && getBalanceFactor(retNode.left) >= 0) {
            return rightRotate(retNode);
        }

        // RR
        if (balanceFactor < -1 && getBalanceFactor(retNode.right) <= 0) {
            return leftRotate(retNode);
        }

        // LR
        if (balanceFactor > 1 && getBalanceFactor(retNode.left) < 0) {
            retNode.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }

        // RL
        if (balanceFactor < -1 && getBalanceFactor(retNode.right) > 0) {
            retNode.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }

        return retNode;
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
     * 使用示例
     */
    public static void main(String[] args) {
        AVLTree<Integer> avl = new AVLTree<>();
        
        // 测试添加元素
        int[] nums = {9, 5, 10, 0, 6, 11, -1, 1, 2};
        for (int num : nums) {
            avl.add(num);
        }
        
        System.out.println("树的大小: " + avl.getSize());
        System.out.println("是否是平衡二叉树