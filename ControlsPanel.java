import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.*;

public class ControlsPanel extends JPanel {

    static final int PANEL_WIDTH = Frame.TARGET_SCREEN_WIDTH - SimulationPanel.PANEL_SIDE_SIZE;
    static final int PANEL_HEIGHT = Frame.TARGET_SCREEN_HEIGHT;

    ControlsPanel(SimulationPanel simulationPanel) {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        this.setLayout(new GridBagLayout());

        addElements(simulationPanel);
    }

    private void addElements(SimulationPanel simulationPanel) {
        JLabel title = new JLabel("Controls");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(50, 50, 0, 50);

        this.add(title, gbc);

        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(50, 50, 50, 50);

        this.add(new ButtonPanel(simulationPanel), gbc);

        JLabel instructions = new JLabel("<html><div style='text-align: center;'>Left Click on any grid tile to place the selected element</div></html>");
        instructions.setFont(new Font("Arial", Font.BOLD, 16));
        instructions.setForeground(Color.WHITE);
        instructions.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(0, 50, 50, 50);

        this.add(instructions, gbc);
    }
}
