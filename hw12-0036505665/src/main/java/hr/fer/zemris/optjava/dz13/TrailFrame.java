package hr.fer.zemris.optjava.dz13;

import hr.fer.zemris.optjava.dz13.ant.Ant;
import hr.fer.zemris.optjava.dz13.ant.Position;

import java.util.List;
import javax.swing.*;
import java.awt.*;

/**
 * A {@link JFrame} for displaying GP results.
 *
 * @author Bruna Dujmović
 *
 */
class TrailFrame extends JFrame {

    /**
     * The ant trail to display.
     */
    private boolean[][] trail;

    /**
     * The ant to control.
     */
    private Ant ant;

    /**
     * Constructs a {@link TrailFrame}.
     *
     * @param trail the ant trail to display
     * @param ant the ant to control
     */
    TrailFrame(boolean[][] trail, Ant ant) {
        this.trail = trail;
        this.ant = ant;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Santa Fe Ant Trail");
        setSize(500, 500);

        initGUI();
    }

    /**
     * Initializes the GUI.
     */
    private void initGUI() {
        setLayout(new BorderLayout());

        JPanel trailPanel = new JPanel(new GridLayout(trail.length, trail[0].length));
        JLabel[][] cellLabels = new JLabel[trail.length][trail[0].length];

        JLabel statsLabel = new JLabel("Food eaten: " + ant.getFoodEaten());

        for (int y = 0; y < trail.length; y++) {
            for (int x = 0; x < trail[y].length; x++) {
                cellLabels[y][x] = new JLabel(" ");
                cellLabels[y][x].setOpaque(true);

                if (trail[y][x]) {
                    cellLabels[y][x].setBackground(Color.GREEN);
                }

                trailPanel.add(cellLabels[y][x]);
            }
        }

        JButton simulateButton = new JButton("Simulate");
        simulateButton.addActionListener(e -> {
            simulateButton.setEnabled(false);

            new Thread(() -> {
                List<Position> antPositions = ant.getPositions();

                for (Position p : antPositions) {
                    SwingUtilities.invokeLater(() -> {
                        cellLabels[p.y][p.x].setBackground(Color.RED);
                        cellLabels[p.y][p.x].setText("x");
                    });

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                    SwingUtilities.invokeLater(() -> cellLabels[p.y][p.x].setText(" "));
                }
            }).start();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(simulateButton);

        add(statsLabel, BorderLayout.NORTH);
        add(trailPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
