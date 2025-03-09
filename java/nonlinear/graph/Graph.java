package nonlinear.graph;

import java.util.*;

/**
 * 图数据结构的Java实现
 * 支持有向图和无向图，使用邻接表表示
 */
public class Graph<V> {
    // 是否为有向图
    private boolean directed;
    // 顶点集合，使用Map存储顶点和对应的索引
    private Map<V, Integer> vertexMap;
    // 顶点列表，根据索引获取顶点
    private List<V> vertexList;
    // 邻接表，使用List<List<Edge>>表示
    private List<List<Edge>> adjList;
    // 边的数量
    private int edgeCount;

    /**
     * 边类，表示从一个顶点到另一个顶点的边
     */
    private class Edge {
        int to;      // 目标顶点的索引
        int weight;  // 边的权重

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "(" + to + ", " + weight + ")";
        }
    }

    /**
     * 构造函数
     * @param directed 是否为有向图
     */
    public Graph(boolean directed) {
        this.directed = directed;
        vertexMap = new HashMap<>();
        vertexList = new ArrayList<>();
        adjList = new ArrayList<>();
        edgeCount = 0;
    }

    /**
     * 添加顶点
     * 时间复杂度 O(1)
     * @param vertex 顶点
     */
    public void addVertex(V vertex) {
        if (!vertexMap.containsKey(vertex)) {
            int index = vertexList.size();
            vertexMap.put(vertex, index);
            vertexList.add(vertex);
            adjList.add(new ArrayList<>());
        }
    }

    /**
     * 添加边
     * 时间复杂度 O(1)
     * @param from 起始顶点
     * @param to 目标顶点
     * @param weight 边的权重
     */
    public void addEdge(V from, V to, int weight) {
        // 确保顶点存在
        addVertex(from);
        addVertex(to);

        int fromIndex = vertexMap.get(from);
        int toIndex = vertexMap.get(to);

        // 添加边
        adjList.get(fromIndex).add(new Edge(toIndex, weight));
        edgeCount++;

        // 如果是无向图，则添加反向边
        if (!directed) {
            adjList.get(toIndex).add(new Edge(fromIndex, weight));
        }
    }

    /**
     * 获取顶点数量
     * 时间复杂度 O(1)
     * @return 顶点数量
     */
    public int getVertexCount() {
        return vertexList.size();
    }

    /**
     * 获取边的数量
     * 时间复杂度 O(1)
     * @return 边的数量
     */
    public int getEdgeCount() {
        return directed ? edgeCount : edgeCount / 2;
    }

    /**
     * 判断两个顶点之间是否有边
     * 时间复杂度 O(E)，其中E为顶点的出度
     * @param from 起始顶点
     * @param to 目标顶点
     * @return 是否有边
     */
    public boolean hasEdge(V from, V to) {
        if (!vertexMap.containsKey(from) || !vertexMap.containsKey(to)) {
            return false;
        }

        int fromIndex = vertexMap.get(from);
        int toIndex = vertexMap.get(to);

        for (Edge edge : adjList.get(fromIndex)) {
            if (edge.to == toIndex) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取边的权重
     * 时间复杂度 O(E)，其中E为顶点的出度
     * @param from 起始顶点
     * @param to 目标顶点
     * @return 边的权重，如果边不存在则返回-1
     */
    public int getWeight(V from, V to) {
        if (!vertexMap.containsKey(from) || !vertexMap.containsKey(to)) {
            return -1;
        }

        int fromIndex = vertexMap.get(from);
        int toIndex = vertexMap.get(to);

        for (Edge edge : adjList.get(fromIndex)) {
            if (edge.to == toIndex) {
                return edge.weight;
            }
        }

        return -1;
    }

    /**
     * 获取顶点的所有邻接顶点
     * 时间复杂度 O(E)，其中E为顶点的出度
     * @param vertex 顶点
     * @return 邻接顶点列表
     */
    public List<V> getNeighbors(V vertex) {
        if (!vertexMap.containsKey(vertex)) {
            return new ArrayList<>();
        }

        int index = vertexMap.get(vertex);
        List<V> neighbors = new ArrayList<>();

        for (Edge edge : adjList.get(index)) {
            neighbors.add(vertexList.get(edge.to));
        }

        return neighbors;
    }

    /**
     * 深度优先遍历(DFS)
     * 时间复杂度 O(V + E)，其中V为顶点数，E为边数
     * @param start 起始顶点
     * @return 遍历结果
     */
    public List<V> dfs(V start) {
        if (!vertexMap.containsKey(start)) {
            return new ArrayList<>();
        }

        List<V> result = new ArrayList<>();
        boolean[] visited = new boolean[vertexList.size()];
        dfsHelper(vertexMap.get(start), visited, result);

        return result;
    }

    /**
     * DFS辅助方法
     * @param vertex 当前顶点索引
     * @param visited 访问标记数组
     * @param result 结果列表
     */
    private void dfsHelper(int vertex, boolean[] visited, List<V> result) {
        visited[vertex] = true;
        result.add(vertexList.get(vertex));

        for (Edge edge : adjList.get(vertex)) {
            if (!visited[edge.to]) {
                dfsHelper(edge.to, visited, result);
            }
        }
    }

    /**
     * 广度优先遍历(BFS)
     * 时间复杂度 O(V + E)，其中V为顶点数，E为边数
     * @param start 起始顶点
     * @return 遍历结果
     */
    public List<V> bfs(V start) {
        if (!vertexMap.containsKey(start)) {
            return new ArrayList<>();
        }

        List<V> result = new ArrayList<>();
        boolean[] visited = new boolean[vertexList.size()];
        Queue<Integer> queue = new LinkedList<>();

        int startIndex = vertexMap.get(start);
        queue.offer(startIndex);
        visited[startIndex] = true;

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            result.add(vertexList.get(vertex));

            for (Edge edge : adjList.get(vertex)) {
                if (!visited[edge.to]) {
                    queue.offer(edge.to);
                    visited[edge.to] = true;
                }
            }
        }

        return result;
    }

    /**
     * Dijkstra算法求解单源最短路径
     * 时间复杂度 O(V^2 + E)，使用优先队列可优化为O((V+E)logV)
     * @param start 起始顶点
     * @return 从起始顶点到所有其他顶点的最短距离
     */
    public Map<V, Integer> dijkstra(V start) {
        if (!vertexMap.containsKey(start)) {
            return new HashMap<>();
        }

        int n = vertexList.size();
        int[] dist = new int[n];
        boolean[] visited = new boolean[n];
        Arrays.fill(dist, Integer.MAX_VALUE);

        int startIndex = vertexMap.get(start);
        dist[startIndex] = 0;

        for (int i = 0; i < n; i++) {
            // 找到未访问的最小距离顶点
            int u = -1;
            int minDist = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && dist[j] < minDist) {
                    u = j;
                    minDist = dist[j];
                }
            }

            if (u == -1) break;
            visited[u] = true;

            // 更新邻接顶点的距离
            for (Edge edge : adjList.get(u)) {
                int v = edge.to;
                if (!visited[v] && dist[u] != Integer.MAX_VALUE && dist[u] + edge.weight < dist[v]) {
                    dist[v] = dist[u] + edge.weight;
                }
            }
        }

        // 构建结果Map
        Map<V, Integer> result = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (dist[i] != Integer.MAX_VALUE) {
                result.put(vertexList.get(i), dist[i]);
            }
        }

        return result;
    }

    /**
     * Prim算法求解最小生成树
     * 时间复杂度 O(V^2)，使用优先队列可优化为O(ElogV)
     * @return 最小生成树的边集合
     */
    public List<String> primMST() {
        if (vertexList.isEmpty()) {
            return new ArrayList<>();
        }

        int n = vertexList.size();
        int[] key = new int[n];
        int[] parent = new int[n];
        boolean[] mstSet = new boolean[n];

        Arrays.fill(key, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        // 从第一个顶点开始
        key[0] = 0;

        for (int count = 0; count < n - 1; count++) {
            // 找到未包含在MST中的最小权值顶点
            int u = -1;
            int minKey = Integer.MAX_VALUE;
            for (int v = 0; v < n; v++) {
                if (!mstSet[v] && key[v] < minKey) {
                    u = v;
                    minKey = key[v];
                }
            }

            if (u == -1) break;
            mstSet[u] = true;

            // 更新相邻顶点的key值
            for (Edge edge : adjList.get(u)) {
                int v = edge.to;
                if (!mstSet[v] && edge.weight < key[v]) {
                    parent[v] = u;
                    key[v] = edge.weight;
                }
            }
        }

        // 构建结果
        List<String> result = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            if (parent[i] != -1) {
                result.add(vertexList.get(parent[i]) + " - " + vertexList.get(i) + " : " + key[i]);
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph: " + (directed ? "Directed" : "Undirected") + "\n");
        sb.append("Vertices: " + vertexList.size() + ", Edges: " + getEdgeCount() + "\n");

        for (int i = 0; i < vertexList.size(); i++) {
            sb.append(vertexList.get(i) + ": ");
            for (Edge edge : adjList.get(i)) {
                sb.append(vertexList.get(edge.to) + "(" + edge.weight + ") ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * 使用示例
     */
    public static void main(String[] args) {
        // 创建一个无向图
        Graph<String> graph = new Graph<>(false);

        // 添加顶点和边
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");

        graph.addEdge("A", "B", 2);
        graph.addEdge("A", "C", 4);
        graph.addEdge("B", "C", 1);
        graph.addEdge("B", "D", 5);
        graph.addEdge("C", "E", 10);
        graph.addEdge("D", "E", 2);

        // 打印图信息
        System.out.println(graph);

        // 深度优先遍历
        System.out.println("DFS from A: " + graph.dfs("A"));

        // 广度优先遍历
        System.out.println("BFS from A: " + graph.bfs("A"));

        // 最短路径
        System.out.println("Shortest paths from A: " + graph.dijkstra("A"));

        // 最小生成树
        System.out.println("Minimum Spanning Tree:");
        List<String> mst = graph.primMST();
        for (String edge : mst) {
            System.out.println(edge);
        }
    }
}, "D", 8);
        graph.addEdge("C