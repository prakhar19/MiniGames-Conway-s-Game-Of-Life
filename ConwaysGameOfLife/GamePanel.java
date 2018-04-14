package ConwaysGameOfLife;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel {

    private boolean city[][];

    private int gridSize, columnSize;

    GamePanel(boolean city[][], int gridSize, int columnSize) {
        this.city = city;
        this.gridSize = gridSize;
        this.columnSize = columnSize;

        setPreferredSize(new Dimension(gridSize * columnSize, gridSize * columnSize));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics = (Graphics2D) g;

        drawGrid(graphics);
    }

    private void drawGrid(Graphics2D g) {
        for(int i = 0; i < gridSize; i++) {

            for(int j = 0; j < gridSize; j++) {

                if(city[i][j]) {
                    g.setColor(new Color(0x92DBF9));
                    g.drawRect(i * columnSize, j * columnSize, columnSize, columnSize);

                    g.setColor(new Color(0x9EE7F9));
                    g.fillRect(i * columnSize + 1, j * columnSize + 1, columnSize - 1, columnSize - 1);
                }
                else {
                    g.setColor(new Color(0x92DBF9));
                    g.drawRect(i * columnSize, j * columnSize, columnSize, columnSize);
                }

            }

        }
    }

}
