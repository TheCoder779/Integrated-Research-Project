package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class Player extends Entity {
    public boolean isalive;
    public BufferedImage Sprite1;
    public BufferedImage Sprite2;
    public int size = 48;
    public int x = 240;
    public int y = 360;
    public int playerSpeed = 10;

    public Player(){
        super(240, 360);
        isalive = true;
        try
        {
            Sprite1 = ImageIO.read(new File("res/fish1.png"));
            Sprite2 = ImageIO.read(new File("res/fish2.png"));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }

    public void draw(Graphics2D g, int n) {
        if(n==1){
            g.drawImage(Sprite1, x - size / 2, y - size / 2, size, size, null);
        }else if(n==2){
            g.drawImage(Sprite2, x - size / 2, y - size / 2, size, size, null);
        }
    }
}