package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class Player extends Entity {
    public boolean isalive;
    public BufferedImage Sprite;
    public int size = 48;
    public int playerX = 240;
    public int playerY = 360;

    public Player(){
        super(240, 360);
        isalive = true;
        try {
            Sprite = ImageIO.read(new File("res/bg.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(Sprite, playerX - size / 2, playerY - size / 2, size, size, null);
    }
}