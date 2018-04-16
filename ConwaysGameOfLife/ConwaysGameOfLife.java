package ConwaysGameOfLife;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConwaysGameOfLife {

    private final int GRID_SIZE;
    private final int COLUMN_SIZE;

    private JFrame mainFrame;

    volatile private boolean city[][];

    private GamePanel gamePanel;
    private InfoPanel infoPanel;

    volatile boolean runFlag = false;

    volatile private int fps = 10;

    private boolean firstDragCell = false;


    ConwaysGameOfLife(int gridSize, int columnSize) {
        GRID_SIZE = gridSize;
        COLUMN_SIZE = columnSize;

        city = new boolean[GRID_SIZE][GRID_SIZE];
    }


    public static void main(String[] args) {
        int gridSize, columnSize;

        if(args.length == 2) {
            try {
                gridSize = Integer.parseInt(args[0]);
                columnSize = Integer.parseInt(args[1]);
            }
            catch(Exception e) {
                gridSize = 50;
                columnSize = 15;
            }

        }
        else {
            gridSize = 50;
            columnSize = 15;
        }


        ConwaysGameOfLife game = new ConwaysGameOfLife(gridSize, columnSize);

        game.newGame();

        game.pauseGame();

        game.startGame();
    }

    public void newGame() {
        mainFrame = new JFrame("Conway's Game of Life");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setBackground(Color.WHITE);

        gamePanel = new GamePanel(city, GRID_SIZE, COLUMN_SIZE);
        mainFrame.add(gamePanel, BorderLayout.CENTER);

        infoPanel = new InfoPanel(GRID_SIZE * COLUMN_SIZE);
        mainFrame.add(infoPanel, BorderLayout.EAST);

        mainFrame.addKeyListener(new KeyLogger());
        mainFrame.addMouseListener(new MouseLogger());
        mainFrame.addMouseMotionListener(new MouseMotionLogger());

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void pauseGame() {
        while(!runFlag) {
            try {
                Thread.sleep(1);
            }
            catch(InterruptedException e) { }
        }
    }

    public void startGame() {
        int i, j, count = 0;
        boolean newcity[][] = new boolean[GRID_SIZE + 2][GRID_SIZE + 2];

        while(true) {
            repaintGamePanel();

            if(!runFlag) {
                pauseGame();
            }

            for(i = 0; i < GRID_SIZE; i++) {
                for(j = 0; j < GRID_SIZE; j++) {
                    newcity[i + 1][j + 1] = city[i][j];
                }
            }

            for(i = 1; i <= GRID_SIZE; i++) {
                for(j = 1; j <= GRID_SIZE; j++) {
                    count = 0;

                    count += (newcity[i - 1][j - 1]) ? 1 : 0;
                    count += (newcity[i - 1][  j  ]) ? 1 : 0;
                    count += (newcity[i - 1][j + 1]) ? 1 : 0;
                    count += (newcity[  i  ][j - 1]) ? 1 : 0;
                    count += (newcity[  i  ][j + 1]) ? 1 : 0;
                    count += (newcity[i + 1][j - 1]) ? 1 : 0;
                    count += (newcity[i + 1][  j  ]) ? 1 : 0;
                    count += (newcity[i + 1][j + 1]) ? 1 : 0;

                    if(count < 2) {
                        city[i - 1][j - 1] = false;
                    }
                    else if(count == 2) {
                        city[i - 1][j - 1] = newcity[i][j];
                    }
                    else if(count == 3) {
                        city[i - 1][j - 1] = true;
                    }
                    else if(count > 3) {
                        city[i - 1][j - 1] = false;
                    }
                }
            }

            try {
                Thread.sleep((int) 1000 / fps);
            }
            catch(InterruptedException e) { }
        }
    }

    public void repaintGamePanel() {
        gamePanel.repaint();
    }


    public class KeyLogger extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent key) {
            switch(key.getKeyCode()) {
                case KeyEvent.VK_EQUALS:
                    if(!key.isShiftDown()) {
                        break;
                    }
                case KeyEvent.VK_ADD:
                    fps++;
                    break;

                case KeyEvent.VK_SUBTRACT:
                case KeyEvent.VK_MINUS:
                    if(fps > 1) {
                        fps--;
                    }
                    break;

                case KeyEvent.VK_SPACE:
                    runFlag = !runFlag;
                    break;

                case KeyEvent.VK_R:
                    for(int i = 0; i < GRID_SIZE; i++) {
                        for(int j = 0; j < GRID_SIZE; j++) {
                            city[i][j] = false;
                        }
                    }
                    runFlag = false;
                    break;

                case KeyEvent.VK_ESCAPE:
                    System.exit(0);
                    break;
            }
        }
    }

    public class MouseLogger extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent mouse) {
            int x = (int) Math.floor((mouse.getX() - mainFrame.getInsets().left) / COLUMN_SIZE);
            int y = (int) Math.floor((mouse.getY() - mainFrame.getInsets().top) / COLUMN_SIZE);

            if(!(x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE)) {
                return;
            }

            firstDragCell = city[x][y];
        }

        @Override
        public void mouseClicked(MouseEvent mouse) {
            int x = (int) Math.floor((mouse.getX() - mainFrame.getInsets().left) / COLUMN_SIZE);
            int y = (int) Math.floor((mouse.getY() - mainFrame.getInsets().top) / COLUMN_SIZE);

            if(!(x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE)) {
                return;
            }

            city[x][y] = !city[x][y];

            repaintGamePanel();
        }
    }

    public class MouseMotionLogger extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent mouse) {
            int x = (int) Math.floor((mouse.getX() - mainFrame.getInsets().left) / COLUMN_SIZE);
            int y = (int) Math.floor((mouse.getY() - mainFrame.getInsets().top) / COLUMN_SIZE);

            if(!(x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE)) {
                return;
            }

            city[x][y] = !firstDragCell;

            repaintGamePanel();
        }
    }
}