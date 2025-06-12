
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;


public class InputManager implements KeyListener {
    
    private boolean upPressed, downPressed, leftPressed, rightPressed, leftArrowPressed, rightArrowPressed, spacePressed, qPressed, nPressed;


    @Override
    public void keyTyped(KeyEvent event){
    }
    @Override
    public void keyPressed(KeyEvent event){
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.VK_W) upPressed = true;
        if (keyCode == KeyEvent.VK_A) leftPressed = true;
        if (keyCode == KeyEvent.VK_S) downPressed = true;
        if (keyCode == KeyEvent.VK_D) rightPressed = true;
        if (keyCode == KeyEvent.VK_LEFT) leftArrowPressed = true;
        if (keyCode == KeyEvent.VK_RIGHT) rightArrowPressed = true;
        if (keyCode == KeyEvent.VK_SPACE) spacePressed = true;
        if (keyCode == KeyEvent.VK_Q) qPressed = true;
        if (keyCode == KeyEvent.VK_N) nPressed = true;
    }
    @Override
    public void keyReleased(KeyEvent event){
        int keyCode = event.getKeyCode();
        if (keyCode == KeyEvent.VK_W) upPressed = false;
        if (keyCode == KeyEvent.VK_A) leftPressed = false;
        if (keyCode == KeyEvent.VK_S) downPressed = false;
        if (keyCode == KeyEvent.VK_D) rightPressed = false;
        if (keyCode == KeyEvent.VK_LEFT) leftArrowPressed = false;
        if (keyCode == KeyEvent.VK_RIGHT) rightArrowPressed = false;
        if (keyCode == KeyEvent.VK_SPACE) spacePressed = false;
        if (keyCode == KeyEvent.VK_Q) qPressed = false;
        if (keyCode == KeyEvent.VK_N) nPressed = false;

    }

    public boolean isQPressed(){
        return qPressed;
    }
    public boolean isNPressed(){
        return nPressed;
    }

    public boolean isUpPressed(){
        return upPressed;
    }
    public boolean isDownPressed(){
        return downPressed;
    }
    public boolean isLeftPressed(){
        return leftPressed;
    }
    public boolean isRightPressed(){
        return rightPressed;
    }
    public boolean isLeftArrowPressed(){
        return leftArrowPressed;
    }
    public boolean isRightArrowPressed(){
        return rightArrowPressed;
    }
    public boolean isSpacedPressed(){
        return spacePressed;
    }
}
