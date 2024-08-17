import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;

public class SimulationPanel extends JPanel {

    static final int PANEL_SIDE_SIZE = Frame.TARGET_SCREEN_HEIGHT;
    static final int UNIT_SIZE = PANEL_SIDE_SIZE / 20;
    static final int GRID_LENGTH = PANEL_SIDE_SIZE / UNIT_SIZE;

    final private Grid grid;

    private int selectedElement = Grid.EMPTY;

    private boolean simulationEnded = false;

    SimulationPanel() {
        this.setPreferredSize(new Dimension(PANEL_SIDE_SIZE, PANEL_SIDE_SIZE));
        this.setBackground(Color.BLACK);

        grid = new Grid(GRID_LENGTH, GRID_LENGTH);

        setupMouseListener();
    }

    private void setupMouseListener() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseInput(e);
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseInput(e);
            }
        });
    }

    private void handleMouseInput(MouseEvent e) {
        if (!this.isEnabled()) {
            return;
        }

        int x = e.getX() / UNIT_SIZE;
        int y = e.getY() / UNIT_SIZE;

        // Checks for valid click position
        if (!grid.isValidPosition(x, y)) {
            return;
        }

        // This clears trails if a simulation has finished
        if (simulationEnded) {
            grid.clearTrails();
            simulationEnded = false;
            this.repaint();
        }

        // Only changes element if it is different from the one at the same position
        if (grid.getCell(x, y) == selectedElement) {
            return;
        }

        grid.setCell(x, y, selectedElement);
        this.repaint();
    }

    public void clearGrid() {
        grid.resetGrid();
        repaint();
    }

    // Checks if both the robot and the finish are placed
    public boolean canStart() {
        return grid.robotExists() && grid.finishExists();
    }

    public void startSimulation() {
        this.setEnabled(false);
        new StartPathfinding(this, grid);
    }

    public void stopSimulation() {
        this.setEnabled(true);
        simulationEnded = true;
    }

    public void drawGrid(Graphics g) {

        // Draw array elements
        for (int i = 0; i < GRID_LENGTH; i++) {
            for (int j = 0; j < GRID_LENGTH; j++) {
                switch (grid.getCell(i, j)) {
                    case Grid.EMPTY -> g.setColor(Color.BLACK);
                    case Grid.WALL -> g.setColor(Color.WHITE);
                    case Grid.ROBOT -> g.setColor(new Color(0, 153, 153));
                    case Grid.FINISH -> g.setColor(new Color(102, 204, 0));
                    case Grid.TRAIL -> g.setColor(new Color(255, 255, 102));
                }

                g.fillRect(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
            }
        }

        // Draw grid
        g.setColor(Color.WHITE);

        for (int i = UNIT_SIZE; i < PANEL_SIDE_SIZE; i += UNIT_SIZE) {
            g.drawLine(i, 0, i, PANEL_SIDE_SIZE);
        }

        for (int i = UNIT_SIZE; i < PANEL_SIDE_SIZE; i += UNIT_SIZE) {
            g.drawLine(0, i, PANEL_SIDE_SIZE, i);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawGrid(g);
    }

    public void setSelectedElement(int value) {
        if (value < 0 || value > 3) {
            throw new IllegalArgumentException("Value must be between 0 and 3: " + value);
        }

        selectedElement = value;
    }
}
