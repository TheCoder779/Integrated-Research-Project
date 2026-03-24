package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class Player extends Entity {
    public boolean isalive;
    public BufferedImage Sprite;
    public int size = 48;
    public int x = 240;
    public int y = 360;
    public int playerSpeed = 5;

    public Player(){
        super(240, 360);
        isalive = true;
        try {
            Sprite = ImageIO.read(new File("res/boy_down_1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(Sprite, x - size / 2, y - size / 2, size, size, null);
    }
}