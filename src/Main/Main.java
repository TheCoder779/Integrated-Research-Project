package Main;


import javax.swing.*;
public class Main {
     static void main() {
        JFrame frame = new JFrame("Chesapeake Chase");
        GamePanel gamepanel = new GamePanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(gamepanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setTitle("Chesapeake Chase");
        frame.setVisible(true);
        boolean notLessonMode = true;
        while (notLessonMode) {
            IO.print(""); //Idk why but it needs this to work
            if (gamepanel.lessonMode) {
                Quiz quiz = new Quiz(gamepanel.score);
                frame.remove(gamepanel);
                frame.add(quiz);
                frame.pack();
                quiz.requestFocus();
                notLessonMode = false;
            }
        }
    }
}