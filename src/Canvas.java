import java.util.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Canvas extends JPanel {
    final Color[] DISC_COLORS = {
            new Color(0, 255, 0),
            new Color(255, 0, 127),
            new Color(255, 255, 0),
            new Color(127, 0, 255),
            new Color(255, 128, 0),
            new Color(255, 0, 0),
            new Color(0, 0, 255),
            new Color(128, 128, 128)
    };
    Graphics2D g2d;
    Timer t;
    int discs_amount = 5;
    int speed = 500;
    int step = 1;
    boolean is_running = false;
    Stack<Integer>[] pillars;

    public Canvas() {
        this.setBackground(new Color(255, 255, 255));
        this.reset();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g2d = (Graphics2D) g;
        this.drawPillars();
        this.drawDiscs();
    }

    public int getPillarPosition(int n) {
        int w = this.getWidth();

        return switch (n) {
            case 0 -> (w / 6) - (w / 200);
            case 1 -> (w / 2) - (w / 200);
            case 2 -> w - (w / 6) - (w / 200);
            default -> 0;
        };
    }

    public void drawPillars() {
        int w = this.getWidth();
        int h = this.getHeight();

        this.g2d.setPaint(new Color(139, 69, 19));
        this.g2d.fillRect(this.getPillarPosition(0), 0, w / 100, h - 1);
        this.g2d.fillRect(this.getPillarPosition(1), 0, w / 100, h - 1);
        this.g2d.fillRect(this.getPillarPosition(2), 0, w / 100, h - 1);
    }

    public void drawDiscs() {
        this.drawPillarDiscs(0);
        this.drawPillarDiscs(1);
        this.drawPillarDiscs(2);
    }

    public void drawPillarDiscs(int n) {
        int w = this.getWidth();
        int h = this.getHeight();

        for (int i = 0; i < pillars[n].size(); i++) {
            int disc_id = pillars[n].get(i);
            int pillar_position = this.getPillarPosition(n);

            int disc_width = (w / 50) * (this.discs_amount + 1) - (w / 50) * (disc_id + 1);
            int disc_x = pillar_position + (w / 200) - (disc_width / 2);
            int disc_y = h - (w / 50) * (i + 1) - 1;

            this.g2d.setPaint(DISC_COLORS[disc_id % 8]);
            this.g2d.fillRect(disc_x, disc_y, disc_width, w / 50);
            this.g2d.setPaint(new Color(0, 0, 0));
            this.g2d.drawRect(disc_x, disc_y, disc_width, w / 50);
        }
    }

    public void runHanoi() {
        this.t = new Timer();
        this.is_running = true;
        this.t.schedule(new TimerTask() {
            @Override
            public void run() {
                stepHanoi();
                runHanoi();
            }
        }, this.speed);
    }

    public void stepHanoi() {
        if (step > Math.pow(2, discs_amount) - 1) {
            stopHanoi();
            return;
        }

        int p1 = -1, p2 = -1, p3 = -1;
        if (!pillars[0].isEmpty()) p1 = pillars[0].peek();
        if (!pillars[1].isEmpty()) p2 = pillars[1].peek();
        if (!pillars[2].isEmpty()) p3 = pillars[2].peek();

        if (discs_amount % 2 == 0) {
            if (step % 3 == 1) {
                if (p1 > p2) pillars[1].push(pillars[0].pop());
                else pillars[0].push(pillars[1].pop());
            } else if (step % 3 == 2) {
                if (p1 > p3) pillars[2].push(pillars[0].pop());
                else pillars[0].push(pillars[2].pop());
            } else if (step % 3 == 0) {
                if (p3 > p2) pillars[1].push(pillars[2].pop());
                else pillars[2].push(pillars[1].pop());
            }
        } else {
            if (step % 3 == 1) {
                if (p1 > p3) pillars[2].push(pillars[0].pop());
                else pillars[0].push(pillars[2].pop());
            } else if (step % 3 == 2) {
                if (p1 > p2) pillars[1].push(pillars[0].pop());
                else pillars[0].push(pillars[1].pop());
            } else if (step % 3 == 0) {
                if (p2 > p3) pillars[2].push(pillars[1].pop());
                else pillars[1].push(pillars[2].pop());
            }
        }

        this.step++;
        this.repaint();
    }

    public void stopHanoi() {
        if (this.is_running) {
            this.is_running = false;
            this.t.cancel();
            this.t.purge();
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDiscsAmount(int discs_amount) {
        this.discs_amount = discs_amount;
        this.reset();
        this.repaint();
    }

    public void reset() {
        this.t = new Timer();
        this.step = 1;

        this.pillars = new Stack[3];
        pillars[0] = new Stack<>();
        pillars[1] = new Stack<>();
        pillars[2] = new Stack<>();

        for (int i = 0; i < discs_amount; i++) pillars[0].push(i);
    }
}