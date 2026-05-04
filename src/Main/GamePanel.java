package Main;

import Entity.Garbage;
import Entity.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    private ScrollingBackground background;
    public static Player player;
    KeyMouseHandler keyH = new KeyMouseHandler();
    ArrayList<Garbage> garbage = new ArrayList<>();
    static int FPS = 60;
    static JFrame frame = new JFrame("Chesapeake Chase");
    static GamePanel gamepanel = new GamePanel();
    Thread gameThread = new Thread(this);
    static int score = 0;
    public int menuNum;
    public int DIFFICULTY;
    public boolean gameOver;
    public boolean gameStarted;
    int spriteNum = 1;
    int spriteTimer = 0;
    int rand;
    int WIDTH, HEIGHT;

    public GamePanel()
    {
        WIDTH = 960;
        HEIGHT = 720;
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
        DIFFICULTY = 2;
        gameStarted = false;
        gameOver = false;
        menuNum = 0;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double delta = 1000000000.0 / FPS;
        double timeLeft = 0;

        while (true) {
            long currentTime = System.nanoTime();
            timeLeft += (currentTime - lastTime) / delta;
            lastTime = currentTime;

            while (timeLeft >= 1) {
                update();
                timeLeft--;
            }
            repaint();
        }
    }

    private void update() {
        if(keyH.menu1StartCoordsPressed && !gameStarted && menuNum == 0){
            menuNum++;
        }
        if(menuNum == 1 && keyH.diffHasBeenSelected){
            DIFFICULTY = keyH.diffSelected;
            gameStarted = true;
        }
        if(gameStarted)
        {
            background.update(6.0f);
            if (keyH.upPressed && player.y > 24)
            {
                player.y -= player.playerSpeed;
            }
            if (keyH.downPressed && player.y < 696)
            {
                player.y += player.playerSpeed;
            }
            if (keyH.leftPressed && player.x > 24)
            {
                player.x -= player.playerSpeed;
            }
            if (keyH.rightPressed && player.x < 936)
            {
                player.x += player.playerSpeed;
            }
            if (!keyH.rightPressed && !keyH.leftPressed && !keyH.upPressed && !keyH.downPressed && player.x > 24)
            {
                player.x -= 2;
            }
            if (keyH.exitPressed)
            {
                System.exit(0);
            }
            if (DIFFICULTY == 0)
            {
                rand = 1;
            } else if (DIFFICULTY == 1)
            {
                rand = (int) (Math.random() * 50);
            } else if (DIFFICULTY == 2)
            {
                rand = (int) (Math.random() * 20);
            } else if (DIFFICULTY == 3)
            {
                rand = (int) (Math.random() * 5);
            } else if (DIFFICULTY == 4)
            {
                rand = (int) (Math.random() * 3);
            } else if (DIFFICULTY == 5)
            {
                rand = 0;
            }
            if (rand == 0)
            {
                garbage.add(new Garbage(960, (int) (Math.random() * 720)));
            }
            for (int i = 0; i < garbage.size(); i++)
            {
                garbage.get(i).garbX -= Garbage.garbSpeed;
                if (garbage.get(i).garbX < 0)
                {
                    garbage.remove(i);
                    score++;
                }
            }
            spriteTimer++;
            if (spriteTimer >= 6)
            {
                if (spriteNum == 1)
                {
                    spriteNum = 2;
                } else if (spriteNum == 2)
                {
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

            BufferedImage bg;
            if(menuNum == 0)
            {
                try
                {
                    bg = ImageIO.read(new File("res/menu.png"));
                } catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
            else if(menuNum == 1){
                try
                {
                    bg = ImageIO.read(new File("res/diffselect.png"));
                } catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
            else {
                bg = null;
            }
            g2d.drawImage(bg, 0, 0, WIDTH, HEIGHT, null);
        }
        else if (gameOver){

        }
        else
        {
            background.draw(g2d);
            player.draw(g2d, spriteNum);
            for (int i = 0; i < garbage.size(); i++)
            {
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
                gameOver = true;
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

    public static void main(String[] args)
    {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(gamepanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setTitle("FPS: " + FPS);
        frame.setVisible(true);
    }
}