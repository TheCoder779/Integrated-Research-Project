package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Garbage extends Entity {

    public static BufferedImage[] GarbageSprites = new BufferedImage[9];
    public int type;
    public int size = 48;
    public int garbX;
    public int garbY;
    public int garbSpeed = 6;

    public Garbage(int x, int y) {
        // Swapped the positions of the super and the garbX/garbY because it caused an error - Edward Fix
    	super(x, y);
    	garbX = x;
        garbY = y;
        try {
            GarbageSprites[0] = ImageIO.read(new File("res/plastic1.png"));
            GarbageSprites[1] = ImageIO.read(new File("res/plastic2.png"));
            GarbageSprites[2] = ImageIO.read(new File("res/plastic3.png"));
            GarbageSprites[3] = ImageIO.read(new File("res/fertilizer1.png"));
            GarbageSprites[4] = ImageIO.read(new File("res/fertilizer2.png"));
            GarbageSprites[5] = ImageIO.read(new File("res/fertilizer3.png"));
            GarbageSprites[6] = ImageIO.read(new File("res/sewage1.png"));
            GarbageSprites[7] = ImageIO.read(new File("res/sewage1.png"));
            GarbageSprites[8] = ImageIO.read(new File("res/sewage2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.type = (int) (Math.random() * 9);
    }

    public void draw(Graphics2D g) {
        g.drawImage(GarbageSprites[type], garbX - size / 2, garbY - size / 2, size, size, null);
    }
}
