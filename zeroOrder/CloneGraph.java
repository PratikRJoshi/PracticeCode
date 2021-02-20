package zeroOrder;

import java.util.*;

public class CloneGraph {
    public Node cloneGraph(Node node) {
        if (node == null)
            return null;

        // map to store the copy of created nodes and their neighbors
        Map<Node, Node> map = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        map.put(node, new Node(node.val));

        while (!queue.isEmpty()) {
            Node undirectedGraphNode = queue.remove();

            for (Node neighbor : undirectedGraphNode.neighbors) {
                if (map.containsKey(neighbor)) {
                    map.get(undirectedGraphNode).neighbors.add(map.get(neighbor));
                } else {
                    Node newNode = new Node(neighbor.val);
                    map.put(neighbor, newNode);
                    map.get(undirectedGraphNode).neighbors.add(newNode);
                    queue.add(neighbor);
                }
            }
        }

        return node;
    }
}

  class Node {
      int val;
      List<Node> neighbors;
      Node(int x) {
          val = x; neighbors = new ArrayList<Node>();
      }
  }
