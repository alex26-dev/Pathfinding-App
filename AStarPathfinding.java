import java.awt.Point;
import java.util.*;

public class AStarPathfinding {

    private final Grid grid;
    private final Node startNode;
    private final Node finishNode;

    AStarPathfinding(Grid grid) {
        this.grid = grid;
        this.startNode = grid.getRobotPos();
        this.finishNode = grid.getFinishPos();
    }

    // Calculates the distance between two nodes
    private int calculateHeuristic(Node node, Node finish) {
        return Math.abs(node.getX() - finish.getX()) + Math.abs(node.getY() - finish.getY());
    }

    private List<Point> reconstructPath(Node currentNode) {
        List<Point> path = new ArrayList<>();

        // Repeats until the start node, which has no parent node
        while (currentNode != null) {
            path.add(new Point(currentNode.getX(), currentNode.getY()));
            currentNode = currentNode.getParent();
        }

        Collections.reverse(path);

        return path;
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int x = node.getX();
        int y = node.getY();

        // Define neighbor coordinates (Left, Right, Down, Up)
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];

            if (grid.isValidPosition(newX, newY) && grid.getCell(newX, newY) != Grid.WALL) {
                neighbors.add(new Node(newX, newY));
            }
        }

        return neighbors;
    }

    public List<Point> findShortestPath() {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getFCost));
        Set<Node> closedSet = new HashSet<>();

        startNode.setGCost(0);
        startNode.setHCost(calculateHeuristic(startNode, finishNode));
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node currentNode = openSet.poll();

            if (currentNode.equals(finishNode)) {
                return reconstructPath(currentNode);
            }

            closedSet.add(currentNode);

            for (Node neighbor : getNeighbors(currentNode)) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                int tentativeGCost = currentNode.getGCost() + 1;

                if (tentativeGCost < neighbor.getGCost()) {
                    neighbor.setGCost(tentativeGCost);
                    neighbor.setHCost(calculateHeuristic(neighbor, finishNode));
                    neighbor.setParent(currentNode);

                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }

        return new ArrayList<>(); // Return empty list if no path is found
    }
}