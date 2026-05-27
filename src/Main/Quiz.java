package Main;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Quiz extends JPanel implements Runnable {

    private double correct;
    public int gameScore;
    private int qNum = 0;
    private final Thread gameThread = new Thread(this);
    int[] answers;
    KeyMouseHandler keyH = new KeyMouseHandler();
    public boolean scoreScreen = false;

    public Quiz(int gameScore) {
        int WIDTH = 960;
        int HEIGHT = 720;
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.setVisible(true);
        this.requestFocus();
        correct = 0;
        this.gameScore = gameScore;
        answers = new int[]{4, 1, 3, 3, 2};
        gameThread.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double delta = 1000000000.0 / 60;
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

    public void update(){

        if(qNum == 0 && keyH.spacePressed){
            qNum = 1;
            keyH.spacePressed = false;
        }
        if(qNum >= 1 && qNum <= 5){
            if(keyH.aNumPressed && (keyH.numPressed == answers[qNum - 1])){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                correct++;
                qNum++;
                keyH.aNumPressed = false;
            }
            else if (keyH.aNumPressed){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                qNum++;
                keyH.aNumPressed = false; // Add this
            }
            if(qNum == 6){
                scoreScreen = true;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if(scoreScreen){
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Times New Roman", Font.BOLD, 36));
            g2d.drawString("Quiz Score: " + correct / 5 * 100 + "%", 240, 180);
            g2d.drawString("Game Score: " + gameScore, 240, 240);
        } 
        else {
            try {
                if (qNum == 0) {
                    g2d.drawImage(ImageIO.read(new File("res/lessons/lesson.png")), 0, 0, 960, 720, null);
                }
                if (qNum == 1) {
                    g2d.drawImage(ImageIO.read(new File("res/lessons/q1.png")), 0, 0, 960, 720, null);
                }
                if (qNum == 2) {
                    g2d.drawImage(ImageIO.read(new File("res/lessons/q2.png")), 0, 0, 960, 720, null);
                }
                if (qNum == 3) {
                    g2d.drawImage(ImageIO.read(new File("res/lessons/q3.png")), 0, 0, 960, 720, null);
                }
                if (qNum == 4) {
                    g2d.drawImage(ImageIO.read(new File("res/lessons/q4.png")), 0, 0, 960, 720, null);
                }
                if (qNum == 5) {
                    g2d.drawImage(ImageIO.read(new File("res/lessons/q5.png")), 0, 0, 960, 720, null);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
