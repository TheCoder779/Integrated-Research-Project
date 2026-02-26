package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class ScrollingBackground {
    private BufferedImage backgroundImage;
    private int imageWidth;
    private int imageHeight;
    private int screenWidth;
    private int screenHeight;
    private float scrollX; // Track the scroll position

    public ScrollingBackground(String imagePath, int screenWidth, int screenHeight) throws IOException {
        this.backgroundImage = ImageIO.read(new File("res/bg.png"));
        this.imageWidth = backgroundImage.getWidth();
        this.imageHeight = backgroundImage.getHeight();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.scrollX = 0;
    }

    /**
     * Update the scroll position (call this in your game loop)
     * @param speed The speed at which to scroll (pixels per frame)
     */
    public void update(float speed) {
        scrollX += speed;

        // Wrap around when the scroll position exceeds the image width
        if (scrollX >= imageWidth) {
            scrollX -= imageWidth;
        } else if (scrollX < 0) {
            scrollX += imageWidth;
        }
    }

    /**
     * Draw the repeating background (call this in your render method)
     */
    public void draw(Graphics2D g) {
        // Calculate the offset for seamless tiling
        int offsetX = (int) -scrollX;

        // Draw the first image
        g.drawImage(backgroundImage, offsetX, 0, null);

        // Draw the second image to fill the gap on the right
        g.drawImage(backgroundImage, offsetX + imageWidth, 0, null);

        // If needed, draw a third image for wider screens or faster scrolling
        if (offsetX + imageWidth * 2 < screenWidth) {
            g.drawImage(backgroundImage, offsetX + imageWidth * 2, 0, null);
        }
    }

    /**
     * Get the current scroll position
     */
    public float getScrollX() {
        return scrollX;
    }

    /**
     * Set the scroll position directly
     */
    public void setScrollX(float x) {
        this.scrollX = x % imageWidth;
    }
}