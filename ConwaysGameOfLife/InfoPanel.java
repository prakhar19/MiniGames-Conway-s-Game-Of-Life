package ConwaysGameOfLife;

import javax.swing.JPanel;
import java.awt.*;

public class InfoPanel extends JPanel {

    private int height;

    InfoPanel(int height) {
        this.height = height;
        setPreferredSize(new Dimension(200, height));
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics = (Graphics2D) g;

        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Calibri", Font.BOLD, 16));
        graphics.drawString("Controls", 8, 25);

        graphics.setColor(Color.BLUE);
        graphics.setFont(new Font("Calibri", Font.BOLD, 14));

        graphics.drawString("+     -> Increase FPS", 8, 55);
        graphics.drawString("-     -> Decrease FPS", 8, 75);
        graphics.drawString("SPACE -> Start/Pause", 8, 95);
        graphics.drawString("R     -> Restart", 8, 115);
        graphics.drawString("ESC   -> Close", 8, 135);

    }
}
