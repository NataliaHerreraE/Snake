import javax.swing.*;


public class GameFrame extends JFrame {

    GameFrame(){
        // GameScreen screen = new GameScreen();
        this.add(new GamePanel()); // same as create the object separate
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null); //windows open in the middle
    }
}
