package Main;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {
    private ScrollingBackground background;
    private Thread gameThread;
    private boolean running = true;
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int FPS = 60;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);

        try {
            // Load your 960x720 background image
            background = new ScrollingBackground("path/to/your/background.png", WIDTH, HEIGHT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double ns = 1000000000.0 / FPS;
        double delta = 0;

        while (running) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / ns;
            lastTime = currentTime;

            while (delta >= 1) {
                update();
                delta--;
            }

            repaint();
        }
    }

    private void update() {
        // Scroll the background to the left (positive value moves it left)
        // Adjust the speed value (2.0f) to control how fast it scrolls
        background.update(2.0f);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        background.draw(g2d);

        // Draw your player, enemies, and other game objects here

        g2d.dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chesapeake Chase");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GamePanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}