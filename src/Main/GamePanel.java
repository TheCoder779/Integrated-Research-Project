package Main;

import Entity.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {
    private ScrollingBackground background;
    public static Player player;
    KeyHandler keyH = new KeyHandler();
    ArrayList<Garbage> garbage = new ArrayList<>();
    static int FPS = 60;
    static JFrame frame = new JFrame("Chesapeake Chase");
    static GamePanel gamepanel = new GamePanel();
    Thread gameThread = new Thread(this);
    static int score = 0;

    public GamePanel() {
        int WIDTH = 960;
        int HEIGHT = 720;
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        try {
            background = new ScrollingBackground(WIDTH);
        } catch (IOException e) {
            IO.print("An error has occurred constructing the game panel");
        }
        this.addKeyListener(keyH);
        this.setFocusable(true);
        player = new Player();
        gameThread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double delta = 1000000000.0 / FPS;
        double timeleft = 0;

        while (true) {
            long currentTime = System.nanoTime();
            timeleft += (currentTime - lastTime) / delta;
            lastTime = currentTime;

            while (timeleft >= 1) {
                update();
                timeleft--;
            }

            repaint();
        }
    }

    private void update() {
        background.update(3.0f);
        if (keyH.upPressed) {
            player.y -= player.playerSpeed;
        }
        if (keyH.downPressed) {
            player.y += player.playerSpeed;
        }
        if (keyH.leftPressed) {
            player.x -= player.playerSpeed;
        }
        if (keyH.rightPressed) {
            player.x += player.playerSpeed;
        }
        if(keyH.exitPressed){
            System.exit(0);
        }
        int rand = (int) (Math.random() * 60);
        if (rand == 0) {
            garbage.add(new Garbage(960, (int) (Math.random() * 624 + 48)));
        }
        for (int i = 0; i < garbage.size(); i++) {
            garbage.get(i).garbX -= garbage.get(i).garbSpeed;
            if(garbage.get(i).garbX < 0){
                garbage.remove(i);
                score++;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        background.draw(g2d);
        player.draw(g2d);
        for (int i = 0; i < garbage.size(); i++) {
            garbage.get(i).draw(g2d);
        }
        showScore(g2d);
        checkIfDead();
        g2d.dispose();
    }

    public void checkIfDead() {
        for(int i = 0; i < garbage.size(); i++) {
            if((player.x - garbage.get(i).garbX >= -48) && (player.x - garbage.get(i).garbX <= 48) && (player.y - garbage.get(i).garbY <= 48) && (player.y - garbage.get(i).garbY >= -48)) {
                player.isalive = false;
                frame.setVisible(false);

            }
        }
    }

    public void showScore(Graphics2D g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Score: " + score, 10, 30);
    }

    static void main() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(gamepanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setTitle("FPS: " + FPS);
        frame.setVisible(true);
    }
}