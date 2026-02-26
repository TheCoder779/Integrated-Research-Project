package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class ScrollingBackground {
    private final BufferedImage backgroundImage;
    private final int imageWidth;
    private final int screenWidth;
    private float scrollX;

    public ScrollingBackground(int screenWidth) throws IOException {
        this.backgroundImage = ImageIO.read(new File("res/bg.png"));
        this.imageWidth = backgroundImage.getWidth();
        this.screenWidth = screenWidth;
        this.scrollX = 0;
    }


    public void update(float speed) {
        scrollX += speed;

        // Wrap around when the scroll position exceeds the image width
        if (scrollX >= imageWidth) {
            scrollX -= imageWidth;
        } else if (scrollX < 0) {
            scrollX += imageWidth;
        }
    }


    public void draw(Graphics2D g) {
        int offsetX = (int) -scrollX;

        // Draw the first image
        g.drawImage(backgroundImage, offsetX, 0, null);

        // Draw the second image to fill the gap on the right
        g.drawImage(backgroundImage, offsetX + imageWidth, 0, null);

        // Draw the second image to fill the gap on the right if speed is too fast
        if (offsetX + imageWidth * 2 < screenWidth) {
            g.drawImage(backgroundImage, offsetX + imageWidth * 2, 0, null);
        }
    }

}