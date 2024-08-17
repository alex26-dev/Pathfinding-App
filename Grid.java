public class Grid {

    final private int[][] grid;
    final private int width;
    final private int height;

    static final int EMPTY = 0;
    static final int WALL = 1;
    static final int ROBOT = 2;
    static final int FINISH = 3;
    static final int TRAIL = 4; // Left behind the robot when simulating

    private int robotX = -1, robotY = -1;
    private int finishX = -1, finishY = -1;

    Grid(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("The width and the height must be positive values: (" + width + ", " + height + ")");
        }

        this.width = width;
        this.height = height;
        grid = new int[width][height];

        resetGrid();
    }

    // Sets all cells to 0
    public void resetGrid() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = 0;
            }
        }

        resetRobotPos();
        resetFinishPos();
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private void resetRobotPos() {
        robotX = -1;
        robotY = -1;
    }

    private void resetFinishPos() {
        finishX = -1;
        finishY = -1;
    }

    public boolean robotExists() {
        return robotX != -1 && robotY != -1;
    }

    public boolean finishExists() {
        return finishX != -1 && finishY != -1;
    }

    public void clearTrails() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[i][j] == TRAIL) {
                    grid[i][j] = EMPTY;
                }
            }
        }
    }

    public int getCell(int x, int y) {
        if (!isValidPosition(x, y)) {
            throw new IllegalArgumentException("Invalid coordinates: (" + x + ", " + y + ")");
        }

        return grid[x][y];
    }

    public void setCell(int x, int y, int value) {
        if (!isValidPosition(x, y)) {
            throw new IllegalArgumentException("Invalid coordinates: (" + x + ", " + y + ")");
        }

        if (value < 0 || value > 4) {
            throw new IllegalArgumentException("Value must be between 0 and 4: " + value);
        }

        // Only allows one robot and finish on the grid at once
        if (value == ROBOT) {
            if (robotExists()) grid[robotX][robotY] = EMPTY;

            robotX = x;
            robotY = y;
        } else if (value == FINISH) {
            if (finishExists()) grid[finishX][finishY] = EMPTY;

            finishX = x;
            finishY = y;
        }

        // If another element is placed over the Robot or Finish, reset their position
        if (grid[x][y] == ROBOT && value != ROBOT) resetRobotPos();
        if (grid[x][y] == FINISH && value != FINISH) resetFinishPos();

        grid[x][y] = value;
    }

    public Node getRobotPos() {
        return new Node(robotX, robotY);
    }

    public Node getFinishPos() {
        return new Node(finishX, finishY);
    }
}
