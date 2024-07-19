import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    static final int screenWidth = 600;
    static final int screenHeight = 600;
    static final int unitSize = 20; //sizes of the dots in the screen
    static final int GameUnits = (screenWidth*screenHeight)/unitSize;
    static final int delay = 75; // initial velocity of the game

    //arrays that content the body part of the snake
    final int x[] = new int [GameUnits]; // holds the x coordinates for the body parts
    final int y[] = new int[GameUnits]; // holds the y coordinates of the body parts

    //initial size of the snake
    int bodyDots = 3;

    //apple part
    int applesEaten;
    int appleX; // random coordinate X of the apple when appears
    int appleY; // random coordinate X of the apple when appears
    int goldenAppleX; // random coordinate X of the apple when appears
    int goldenAppleY; // random coordinate X of the apple when appears
    int goldenApplesEaten;
    int redApplesEaten;
    int redAppleCount;

    //snake start movement
    char direction = 'R';
    boolean running = false; // game running or not
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize( new Dimension(screenWidth,screenHeight)); //size of the screen
        this.setBackground(new Color(80,80,90)); //gris :)
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        StartGame();
    }

    public void StartGame(){
        NewApple();
        running = true;
        timer = new Timer(delay,this);
        timer.start();
    }
    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Draw(graphics);
    }

    public void Draw(Graphics graphics){
        if(running) {
            //grid for development purpose, guys comment this part at the end before we upload the final result
            for (int i = 0; i < screenHeight / unitSize; i++) {
                graphics.drawLine(i * unitSize, 0, i * unitSize, screenHeight);
                graphics.drawLine(0, i * unitSize, screenWidth, i * unitSize);
            }

            //red apple
            graphics.setColor(new Color(230, 0, 0)); // apple color, red coral :)
            graphics.fillOval(appleX, appleY, unitSize, unitSize);

            //golden apple
            graphics.setColor(new Color(255, 193, 51)); // apple color, red coral :)
            graphics.fillOval(goldenAppleX, goldenAppleY, unitSize, unitSize);

            //snake
            for (int i = 0; i < bodyDots; i++) {
                if (i == 0) {

                    //head
                    graphics.setColor(new Color(138, 218, 193));
                    graphics.fillRect(x[i], y[i], unitSize, unitSize);
                } else {
                    //body
                    graphics.setColor(new Color(51, 162, 127));
                    graphics.fillRect(x[i], y[i], unitSize, unitSize);
                    //serpiente de colores random, solo queria probar :)
                    //graphics.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                }
            }
            //score text
            graphics.setColor(new Color(255,0,66));
            graphics.setFont(new Font("Ink Free", Font.BOLD,35));
            FontMetrics fontAlign = getFontMetrics(graphics.getFont());
            graphics.drawString("Score: "+applesEaten, (screenWidth - fontAlign.stringWidth("Score: "+applesEaten))/2, graphics.getFont().getSize());

        }
        else {
            GameOver(graphics);
        }

    }
    public void NewApple(){
        if(redAppleCount <= 5){
            appleX = random.nextInt((int)(screenWidth/unitSize))*unitSize;
            appleY = random.nextInt((int)(screenHeight/unitSize))*unitSize;
            redAppleCount = 0;
        }


    }
    public void Move(){
        //shift the position of the bodydots when moves
        for (int i = bodyDots; i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        //to give direction to the snake
        switch (direction){
            case'U':
                y[0] = y[0] - unitSize;
                break;
            case'D':
                y[0] = y[0] + unitSize;
                break;
            case'L':
                x[0] = x[0] - unitSize;
                break;
            case'R':
                x[0] = x[0] + unitSize;
                break;

        }

    }
    public void CheckApple(){

        if((x[0] == appleX) && (y[0] == appleY)){
            bodyDots++;
            applesEaten++;
            NewApple();
        }
    }
    public void CheckCollisions(){
        //check if the head of the snake collides with the body
        for(int i = bodyDots; i>0;i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running=false;
            }
        }
        //check if the head of the snake collides with the left border
        if(x[0] < 0){
            running=false;
        }
        //check if the head of the snake collides with the right border
        if(x[0] > screenWidth){
            running=false;
        }
        //check if the head of the snake collides with the top border
        if(y[0] < 0){
            running=false;
        }
        //check if the head of the snake collides with the bottom border
        if(y[0] > screenHeight){
            running=false;
        }

        if(!running) {
            timer.stop();
        }
    }
    public void GameOver(Graphics graphics){
        //score text
        graphics.setColor(new Color(100,10,10));
        graphics.setFont(new Font("Ink Free", Font.BOLD,35));
        FontMetrics fontAlign1 = getFontMetrics(graphics.getFont());
        graphics.drawString("Score: "+applesEaten, (screenWidth - fontAlign1.stringWidth("Score: "+applesEaten))/2, graphics.getFont().getSize());

        //game over text
        graphics.setColor(new Color(100,10,10));
        graphics.setFont(new Font("Ink Free", Font.BOLD,75));
        FontMetrics fontAlign2 = getFontMetrics(graphics.getFont());
        //center the text on the screen
        graphics.drawString("Game Over", (screenWidth - fontAlign2.stringWidth("Game Over"))/2, screenHeight/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            Move();
            CheckApple();
            CheckCollisions();
        }
        repaint();
    }


    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent event) {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }


}
