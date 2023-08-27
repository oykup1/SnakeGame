
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    // game panel

    static final int width = 800;
    static final int height = 800;
    static final int tickSize = 50;
    static final int boardSize = (width * height) / tickSize;

    int[] snakePosX = new int[boardSize];
    int[] snakePosY = new int[boardSize];
    int snakeLength;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean isMoving = false;
    Random random;
    Color grass = new Color(173, 193, 120);
    Color snake = new Color(108, 88, 76);
    Color fruit = new Color(175, 77, 152);
    final Timer timer = new Timer(75, this);

    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(grass);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        start();
    }

    public void start() {
        newApple();
        snakePosX = new int[boardSize];
        snakePosY = new int[boardSize];
        snakeLength = 5;
        direction = 'R';
        isMoving = true;
        timer.start();
    }

    @Override
    public void paintComponent(Graphics j) {
        super.paintComponent(j);
        draw(j);

    }

    public void draw(Graphics g) {
        if (isMoving) {
            g.setColor(fruit);
            g.fillOval(appleX, appleY, tickSize, tickSize);
            for (int i = 0; i < snakeLength; i++) {
                if (i == 0) {
                    g.setColor(Color.black);
                    g.fillRect(snakePosX[i], snakePosY[i], tickSize, tickSize);
                } else {
                    g.setColor(snake);
                    g.fillRect(snakePosX[i], snakePosY[i], tickSize, tickSize);
                }
            }
            g.setColor(snake);
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (width - metrics.stringWidth("Score: " + applesEaten)) / 2,
                    g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        appleX = random.nextInt((int) width / tickSize) * tickSize;
        appleY = random.nextInt((int) height / tickSize) * tickSize;
    }

    public void move() {

        for (int i = snakeLength; i > 0; i--) {
            snakePosX[i] = snakePosX[i - 1];
            snakePosY[i] = snakePosY[i - 1];
        }
        switch (direction) {
            case 'U':
                snakePosY[0] = snakePosY[0] - tickSize;
                break;
            case 'D':
                snakePosY[0] = snakePosY[0] + tickSize;
                break;
            case 'L':
                snakePosX[0] = snakePosX[0] - tickSize;
                break;
            case 'R':
                snakePosX[0] = snakePosX[0] + tickSize;

        }
    }

    public void checkApple() {
        if ((snakePosX[0] == appleX) && (snakePosY[0] == appleY)) {
            snakeLength++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        // checks if head collides with body
        for (int i = snakeLength; i > 0; i--) {
            if ((snakePosX[0] == snakePosX[i]) && (snakePosY[0] == snakePosY[i])) {
                isMoving = false;
            }
        }
        // check if head touches left border
        if (snakePosX[0] < 0)
            isMoving = false;
        // check if head touches right border
        if (snakePosX[0] > width)
            isMoving = false;
        // check if head touches top border
        if (snakePosY[0] < 0)
            isMoving = false;
        // check if head touches bottom border
        if (snakePosY[0] > height)
            isMoving = false;

        if (!isMoving)
            timer.stop();
    }

    public void gameOver(Graphics j) {
        // Game Over text
        j.setColor(snake);
        j.setFont(new Font("Monospaced", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(j.getFont());
        j.drawString("Game Over!", (width - metrics.stringWidth("Game Over!")) / 2, height / 2);
        j.setColor(snake);
        j.setFont(new Font("Monospaced", Font.BOLD, 40));
        FontMetrics metrics2 = getFontMetrics(j.getFont());
        j.drawString("Score: " + applesEaten, (width - metrics2.stringWidth("Score: " + applesEaten)) / 2,
                j.getFont().getSize());
    }

    public class myKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isMoving) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
}