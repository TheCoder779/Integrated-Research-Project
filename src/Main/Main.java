package Main;


import javax.swing.*;


public class Main extends JFrame{
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(960,720);
        frame.setLocationRelativeTo(null);
        frame.add(new GamePanel());
        frame.setVisible(true);
    }
}
