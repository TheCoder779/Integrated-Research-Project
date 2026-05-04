package Main;

import java.awt.event.*;

public class KeyMouseHandler implements KeyListener, MouseListener
{
    public boolean upPressed, downPressed, leftPressed, rightPressed = false;
    public boolean exitPressed = false;
    public boolean enterPressed = false;
    public boolean clicked;
    public int pressX, pressY;
    public boolean menu1StartCoordsPressed;
    public int diffSelected;
    public boolean diffHasBeenSelected;

    @Override
    public void keyTyped(KeyEvent e)
    {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_ENTER)
        {
            enterPressed = true;
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W)
        {
            upPressed = true;
        } else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S)
        {
            downPressed = true;
        } else if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A)
        {
            leftPressed = true;
        } else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
        {
            rightPressed = true;
        } else if (code == KeyEvent.VK_ESCAPE)
        {
            exitPressed = true;
        }
        if(code == KeyEvent.VK_1 || code == KeyEvent.VK_2 || code == KeyEvent.VK_3 || code == KeyEvent.VK_4 || code == KeyEvent.VK_5)
        {
            diffSelected = code - 49;
            diffHasBeenSelected = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W)
        {
            upPressed = false;
        } else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S)
        {
            downPressed = false;
        } else if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A)
        {
            leftPressed = false;
        } else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D)
        {
            rightPressed = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            clicked = true;
            pressX = e.getX();
            pressY = e.getY();
            if((pressX >= 360 && pressX <= 600) && (pressY >= 400 && pressY <= 490)){
                menu1StartCoordsPressed = true;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        clicked = false;
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {

    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }
}
