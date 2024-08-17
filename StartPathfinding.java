import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import java.awt.Point;
import java.util.List;

public class StartPathfinding {

    StartPathfinding(SimulationPanel simulationPanel, Grid grid) {
        simulationPanel.setEnabled(false);

        AStarPathfinding pathfinding = new AStarPathfinding(grid);
        List<Point> shortestPath = pathfinding.findShortestPath();

        // In case the path is blocked
        if (shortestPath.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "The Robot can't reach the Finish.",
                    "Blocked Path",
                    JOptionPane.WARNING_MESSAGE
            );

            simulationPanel.stopSimulation();
            return;
        }

        // Using SwingWorker in order to update UI in the background
        SwingWorker<Void, Point> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                for (Point point : shortestPath) {
                    try {
                        // Publish the next node to update the UI
                        publish(point);

                        // Pause for a fifth of a second (changes movement speed)
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        SwingUtilities.invokeLater(() ->
                                JOptionPane.showMessageDialog(
                                        null,
                                        "The pathfinding process got interrupted: " + e.getMessage(),
                                        "An error occurred",
                                        JOptionPane.WARNING_MESSAGE
                                )
                        );

                        Thread.currentThread().interrupt();
                    }
                }

                return null;
            }

            @Override
            protected void process(List<Point> chunks) {
                for (Point point : chunks) {
                    Node oldRobotPos = grid.getRobotPos();

                    // Adds a trail behind the robot
                    grid.setCell(oldRobotPos.getX(), oldRobotPos.getY(), Grid.TRAIL);

                    // Moves the robot to the next point
                    grid.setCell(point.x, point.y, Grid.ROBOT);

                    simulationPanel.repaint();
                }
            }

            @Override
            protected void done() {
                simulationPanel.stopSimulation();
            }
        };

        worker.execute();
    }
}
