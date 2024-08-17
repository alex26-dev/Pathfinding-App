import java.util.Objects;

public class Node {
    private final int x, y;
    private int gCost, hCost;
    Node parent;

    Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.gCost = Integer.MAX_VALUE;
        this.hCost = 0;
        this.parent = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // Cast to node
        Node node = (Node) obj;
        return x == node.getX() && y == node.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getGCost() {
        return gCost;
    }

    public void setGCost(int gCost) {
        this.gCost = gCost;
    }

    public void setHCost(int hCost) {
        this.hCost = hCost;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getFCost() {
        return gCost + hCost;
    }
}
