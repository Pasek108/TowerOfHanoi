import javax.swing.*;

public class TowerOfHanoi extends JFrame {
    private JPanel JPanel1;
    private JButton stopButton;
    private JButton stepButton;
    private JButton runButton;
    private JSlider discsSlider;
    private JSlider speedSlider;
    private JPanel DrawingContainer;
    private final Canvas Canvas = new Canvas();

    public TowerOfHanoi() {
        super("Tower of Hanoi - Artur Pas");
        this.setContentPane(JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setSize(1024, 768);
        this.setVisible(true);

        DrawingContainer.add(Canvas);

        speedSlider.addChangeListener(e -> Canvas.setSpeed(speedSlider.getValue()));
        runButton.addActionListener(e -> {
            if (Canvas.is_running) return;
            Canvas.runHanoi();
        });
        stepButton.addActionListener(e -> Canvas.stepHanoi());
        stopButton.addActionListener(e -> Canvas.stopHanoi());
        discsSlider.addChangeListener(e -> {
            if (Canvas.is_running) Canvas.stopHanoi();
            Canvas.setDiscsAmount(discsSlider.getValue());
        });
    }

    public static void main(String[] args) {
        new TowerOfHanoi();
    }
}
