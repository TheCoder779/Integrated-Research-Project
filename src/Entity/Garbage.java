package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Garbage extends Entity {

    public BufferedImage[] GarbageSprites = new BufferedImage[5];
    public int size = 48;
    public int garbX;
    public int garbY;
    public int garbSpeed = 3;

    public Garbage(int x, int y) {
        garbX = x;
        garbY = y;
        super(x, y);
        try {
            GarbageSprites[0] = ImageIO.read(new File("res/Player/boy_down_1.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(GarbageSprites[0], garbX - size / 2, garbY - size / 2, size, size, null);
    }
}
