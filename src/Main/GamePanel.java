package Main;

import Entity.Garbage;
import Entity.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    private ScrollingBackground background;
    public static Player player;
    KeyMouseHandler keyH = new KeyMouseHandler();
    ArrayList<Garbage> garbage = new ArrayList<>();
    static int FPS = 60;
    Thread gameThread = new Thread(this);
    public int score = 0;
    public boolean gameStarted = false;
    public boolean noEnemies = false;
    public boolean easyMode = false;
    public boolean normalMode = false;
    public boolean hardMode = false;
    public boolean impossibleMode = false;
    public boolean do_not_even_try = false;
    public boolean lessonMode = false;
    int spriteNum = 1;
    int spriteTimer = 0;
    int rand;

    public GamePanel()
    {
        int WIDTH = 960;
        int HEIGHT = 720;
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.addKeyListener(keyH);
        this.addMouseListener(keyH);
        this.setFocusable(true);
        this.setVisible(true);
        gameThread.start();
        player = new Player();
        try {
            background = new ScrollingBackground(WIDTH);
        } catch (IOException e) {
            IO.print("An error has occurred constructing the game panel");
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double delta = 1000000000.0 / FPS;
        double timeLeft = 0;

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            timeLeft += (currentTime - lastTime) / delta;
            lastTime = currentTime;

            while (timeLeft >= 1) {
                update();
                repaint();
                timeLeft--;
            }
        }
    }

    private void update() {
        if (keyH.diffHasBeenSelected)
        {
            gameStarted = true;
        }
        if (gameStarted) {
            background.update(6.0f);
            if (keyH.upPressed && player.y > 24) {
                player.y -= player.playerSpeed;
            }
            if (keyH.downPressed && player.y < 696) {
                player.y += player.playerSpeed;
            }
            if (keyH.leftPressed && player.x > 24) {
                player.x -= player.playerSpeed;
            }
            if (keyH.rightPressed && player.x < 936) {
                player.x += player.playerSpeed;
            }
            if (!keyH.rightPressed && !keyH.leftPressed && !keyH.upPressed && !keyH.downPressed && player.x > 24) {
                player.x -= 2;
            }
            if (keyH.exitPressed) {
                System.exit(0);
            }
            if (noEnemies) {
                rand = 1;
            }  if (easyMode) {
                rand = (int) (Math.random() * 50);
            }  if (normalMode) {
                rand = (int) (Math.random() * 20);
            }  if (hardMode) {
                rand = (int) (Math.random() * 5);
            }  if (impossibleMode) {
                rand = (int) (Math.random() * 3);
            }  if (do_not_even_try) {
                rand = 0;
            }
            if (rand == 0) {
                garbage.add(new Garbage(960, (int) (Math.random() * 720)));
            }
            for (int i = 0; i < garbage.size(); i++) {
                garbage.get(i).garbX -= Garbage.garbSpeed;
                if (garbage.get(i).garbX < 0) {
                    garbage.remove(i);
                    score++;
                }
            }
            spriteTimer++;
            if (spriteTimer >= 6) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteTimer = 0;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if(!gameStarted){
            if(!keyH.menu1StartCoordsPressed) {
                try {
                    g2d.drawImage(ImageIO.read(new File("res/menu.png")), 0, 0, 960, 720, null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(keyH.diffSelection) {
                try {
                    g2d.drawImage(ImageIO.read(new File("res/diffselect.png")), 0, 0, 960, 720, null);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(keyH.diffHasBeenSelected){
                if (keyH.diffSelected == 0) noEnemies = true;
                if (keyH.diffSelected == 1) easyMode = true;
                if (keyH.diffSelected == 2) normalMode = true;
                if (keyH.diffSelected == 3) hardMode = true;
                if (keyH.diffSelected == 4) impossibleMode = true;
                if (keyH.diffSelected == 5) do_not_even_try = true;
            }
        }
        if(gameStarted) {
            background.draw(g2d);
            player.draw(g2d, spriteNum);
            for (int i = 0; i < garbage.size(); i++) {
                garbage.get(i).draw(g2d);
            }
            showScore(g2d);
            checkIfDead();
            g2d.dispose();
        }
    }

    public void checkIfDead()
    {
        for (Garbage value : garbage)
        {
            if ((player.x - value.garbX >= -42) && (player.x - value.garbX <= 42) && (player.y - value.garbY <= 42) && (player.y - value.garbY >= -42))
            {
                player.isalive = false;
                lessonMode = true;
                break;
            }
        }
    }

    public void showScore(Graphics2D g)
    {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Score: " + score, 10, 30);
    }
}