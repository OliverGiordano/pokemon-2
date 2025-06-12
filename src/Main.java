import javax.swing.JFrame;

public class Main {
    public static void main(String[] args){
        JFrame window2D = new JFrame();
        window2D.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window2D.setResizable(false);
        window2D.setTitle("Raycast");
        GamePanel panel = new GamePanel();
        
        window2D.add(panel);
        window2D.pack();
        window2D.setLocationRelativeTo(null);
        window2D.setVisible(true);
        panel.startGameThread();
        MusicPlayer.playMusic("../assets/pokemon.wav");// set this to the song we want played
    }
}
