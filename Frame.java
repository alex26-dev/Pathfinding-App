import javax.swing.JFrame;
import java.awt.BorderLayout;

public class Frame extends JFrame {

    static final int TARGET_SCREEN_WIDTH = 800;
    static final int TARGET_SCREEN_HEIGHT = 500;

    Frame() {
        this.setTitle("Path Finding Simulation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SimulationPanel simulationPanel = new SimulationPanel();
        ControlsPanel controlsPanel = new ControlsPanel(simulationPanel);

        this.setLayout(new BorderLayout());
        this.add(simulationPanel, BorderLayout.CENTER);
        this.add(controlsPanel, BorderLayout.EAST);

        this.pack();
        this.setResizable(false);

        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }
}
