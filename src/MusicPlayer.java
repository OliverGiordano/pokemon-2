import java.io.File;
import javax.sound.sampled.*;


public class MusicPlayer {
    public static void playMusic(String filePath){
        try
        {   
            AudioInputStream gameBackgroundMusic = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
            clip.open(gameBackgroundMusic);
            clip.start();
            clip.loop(-1);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
