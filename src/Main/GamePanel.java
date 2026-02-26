package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GamePanel extends JPanel {
    public final int WIDTH = 960;
    public final int HEIGHT = 720;
    private final BufferedImage background;
    private int x;
    private int y;


    public GamePanel() {
        try {
            background = ImageIO.read(new File("res/bg.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        repaint();
    }


    public void moveImage(int deltaX, int deltaY) {
        x += deltaX;
        y += deltaY;
        repaint(); // Schedules a paint update
    }




    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
    }


}