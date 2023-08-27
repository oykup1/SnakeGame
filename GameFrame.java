
import javax.swing.*;

public class GameFrame extends JFrame {
    // Game frame

    public GameFrame() {
        this.add(new GamePanel());
        this.setTitle("Snake Game");
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}
