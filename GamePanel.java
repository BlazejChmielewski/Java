package Monkas;

import com.sun.javafx.font.Metrics;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    private static final int screenWidth = 1000;
    private static final int screenHeight = 1000;
    private static final int unitSize = 50;
    private static final int delay = 100;
    private static final int gameUnit = (screenHeight*screenWidth)/unitSize;
    public int appleX, appleY;
    public char direction = 'd';
    public int bodyParts = 4;
    public int applesEaten = 0;
    public int[] x = new int [gameUnit];
    public int[] y = new int [gameUnit];

    public boolean runMode = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.addKeyListener(new myKeyAdapter());
        this.setBackground(Color.black);
        this.setFocusable(true);
        startGame();
    }

    public void startGame(){
        newApple();
        runMode = true;
        timer = new Timer(delay,this);
        timer.start();
    }

    public void gameOver(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (screenWidth - metrics1.stringWidth("Game Over"))/2, screenHeight/2);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawGame(g);
    }

    public void appleCheck(){
        if((x[0] == appleX) && (y[0] == appleY)){
            applesEaten ++;
            bodyParts ++;
            newApple();
        }
    }

    public void move(){
        for(int i = bodyParts; i > 0; i --){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch (direction){
            case 'w':
                y[0] += unitSize;
                break;
            case 's':
                y[0] -= unitSize;
                break;
            case 'a':
                x[0] -= unitSize;
                break;
            case 'd':
                x[0] += unitSize;
                break;
        }
    }

    public void checkCollision(){
        for(int i = bodyParts; i > 0; i--){
            if((y[0] == y[i]) && (x[0] == x[i])){
                runMode = false;
            }
            if(x[0] < 0){
                runMode = false;
            }
            if(x[0] > screenWidth){
                runMode = false;
            }
            if(y[0] < 0){
                runMode = false;
            }
            if(y[0] > screenHeight){
                runMode = false;
            }
            if(!runMode){
                timer.stop();
            }
        }
    }

    public void newApple(){
        appleX = random.nextInt((screenWidth/unitSize))*unitSize;
        appleY = random.nextInt((screenHeight/unitSize))*unitSize;
    }

    public void drawGame(Graphics g){
        if(runMode) {
           /* for (int i = 0; i < screenHeight / unitSize; i++) {
                g.drawLine(i * unitSize, 0, 0, screenHeight);
                g.drawLine(0, i * unitSize, screenWidth, 0);
            }
            */
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD,50));
            FontMetrics metric = g.getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten,(screenWidth-metric.stringWidth("Score " + applesEaten))/2,5+g.getFont().getSize());
            g.setColor(new Color(123, 12, 200));
            g.fillOval(appleX, appleY, unitSize, unitSize);

            for (int j = 0; j < bodyParts; j++) {
                if (j == 0) {
                    g.setColor(new Color(40, 190, 30));
                    g.fillRect(x[0], y[0], unitSize, unitSize);
                } else {
                    g.setColor(new Color(100, 160, 150));
                    g.fillRect(x[j], y[j], unitSize, unitSize);
                }
            }
        }
        else gameOver(g);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(runMode){
            move();
            checkCollision();
            appleCheck();
        }
        repaint();
    }

    public class myKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_UP:
                    if(direction != 'w'){
                        direction = 's';
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if(direction != 's'){
                        direction = 'w';
                    }
                        break;


                case KeyEvent.VK_RIGHT:
                    if(direction != 'a'){
                        direction = 'd';
                    }
                        break;


                case KeyEvent.VK_LEFT:
                    if(direction != 'd'){
                        direction = 'a';
                    }
                    break;
            }
        }
    }
}
