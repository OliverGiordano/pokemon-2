import java.awt.Color;
import java.awt.Graphics2D;

public class CatchUI {
    private String text;
    private int toastLife = 0;
    private int toastHeight = 70;

    public CatchUI(String text, int toastLife){
        this.text = text;
        this.toastLife = toastLife;
        this.toastLife = 3*GamePanel.GAMEFPS;
    }

    public void deIncrementCatchTime(){
        toastLife--;
    }

    public int getRemainingTime(){
        return toastLife;
    }

    public void drawToast(Graphics2D g2D, int index, int hOffset){// precondition
        g2D.setColor(Color.BLACK);
        g2D.fillRect(hOffset, (50+((index)*(toastHeight+50))), 450, toastHeight);
        g2D.setColor(GamePanel.cForground);
        g2D.drawString("You caught a " + text, hOffset+10, 100+(((index)*(toastHeight+50))));
    }

}
