import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Component;

public class ButtonPanel extends JPanel {

    final private SimulationPanel simulationPanel;

    ButtonPanel(SimulationPanel simulationPanel) {
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.simulationPanel = simulationPanel;

        addButtons();
    }

    private void addButtons() {
        JRadioButton emptyButton = new JRadioButton("Empty");
        JRadioButton wallButton = new JRadioButton("Wall");
        JRadioButton robotButton = new JRadioButton("Robot");
        JRadioButton finishButton = new JRadioButton("Finish");
        JButton startButton = new JButton("Start");
        JButton clearButton = new JButton("Clear");

        emptyButton.setActionCommand("EMPTY");
        wallButton.setActionCommand("WALL");
        robotButton.setActionCommand("ROBOT");
        finishButton.setActionCommand("FINISH");

        ButtonGroup radioButtonGroup = new ButtonGroup();

        JComponent[] buttons = {emptyButton, wallButton, robotButton, finishButton, clearButton, startButton};
        JRadioButton[] radioButtons = {emptyButton, wallButton, robotButton, finishButton};

        // Set all button's properties
        for (JComponent button : buttons) {
            button.setMaximumSize(new Dimension(200, 30));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setFocusable(false);
        }

        this.add(startButton);
        this.add(Box.createRigidArea(new Dimension(0, 20)));

        // Set radio button's properties
        for (JRadioButton button : radioButtons) {
            radioButtonGroup.add(button);
            button.setHorizontalAlignment(SwingConstants.CENTER);

            this.add(button);
            this.add(Box.createRigidArea(new Dimension(0, 10)));

            addRadioActionListener(button);
        }

        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(clearButton);

        emptyButton.setSelected(true);

        startButton.addActionListener(e -> {
            if (!simulationPanel.isEnabled()) return;

            if (!simulationPanel.canStart()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please place both the Robot and the Finish before starting.",
                        "Missing Elements",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            simulationPanel.startSimulation();
        });

        clearButton.addActionListener(e -> {
            if (!simulationPanel.isEnabled()) return;

            simulationPanel.clearGrid();
        });
    }

    private void addRadioActionListener(JRadioButton button) {
        button.addActionListener(e -> {
            String command = e.getActionCommand();

            switch (command) {
                case "EMPTY" -> simulationPanel.setSelectedElement(Grid.EMPTY);
                case "WALL" -> simulationPanel.setSelectedElement(Grid.WALL);
                case "ROBOT" -> simulationPanel.setSelectedElement(Grid.ROBOT);
                case "FINISH" -> simulationPanel.setSelectedElement(Grid.FINISH);
                default -> throw new IllegalStateException("Unexpected value: " + command);
            }
        });
    }
}
