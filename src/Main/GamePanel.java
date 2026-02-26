package Main;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePanel extends JPanel implements Runnable {
    private ScrollingBackground background;

    public GamePanel() {
        int WIDTH = 960;
        int HEIGHT = 720;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);

        try {
            background = new ScrollingBackground(WIDTH);
        } catch (IOException e) {
            IO.print("An error has occurred constructing the game panel");
        }

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        int FPS = 60;
        double ns = 1000000000.0 / FPS;
        double delta = 0;

        while (true) {
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
        background.update(5.0f);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        background.draw(g2d);

        // Draw the player, enemies, and other game objects here

        g2d.dispose();
    }

    static void main() {
        JFrame frame = new JFrame("Chesapeake Chase");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GamePanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}