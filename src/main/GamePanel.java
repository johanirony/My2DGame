package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //screen setting
    final int originalTileSize=16;//16 x 16 res
    final int scale= 3;
    public final int tileSize = originalTileSize * scale; //48x48 res
    public final int maxScreenCol= 16;
    public final int maxScreenRow=12;
    public final int screenWidth=tileSize*maxScreenCol; //768 pixels
    public final int screenHeight= tileSize* maxScreenRow;//576 pixels

    //world settings
    public final int maxWorldCol=50;
    public final int maxWorldRow=50;
    public final int worldWidth=tileSize*maxWorldCol;
    public final int worldHeight=tileSize*maxWorldRow;
    //set FPS
    int FPS = 60;
    TileManager tileM= new TileManager(this);
    public collisionChecker cChecker = new collisionChecker(this);


    Thread gameThread;
    KeyHandler keyH = new KeyHandler();
    public Player player = new Player(this,keyH);


    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    //we will code the game loop in run interface


//    @Override
//    public void run() {
//        double drawInterval = 1000000000/FPS;
//        double nextDrawTime = System.nanoTime()+drawInterval;
//        while (gameThread !=null){
//
//            long currentTime= System.nanoTime();
//            System.out.println("Current time:"+ currentTime);
//            //1.Update: this will update the information such as char position
//            update();
//            //2.Draw : this will render the screen with updated information
//            repaint();
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime /= 1000000;
//                if(remainingTime<0){
//                    remainingTime=0;
//                }
//                Thread.sleep((long)(remainingTime));
//                nextDrawTime +=drawInterval;
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//
//        }
//
//    }

    @Override
    public void run() {
        double drawInterval = 1000000000 /FPS;
        double delta = 0;
        long lastTime= System.nanoTime();
        long currentTime;
        long timer=0;
        int drawCount=0;

        while(gameThread!=null){
            currentTime = System.nanoTime();
            delta +=(currentTime - lastTime)/drawInterval;
            timer += (currentTime-lastTime);
            lastTime= currentTime;

            if(delta>1){
                update();
                repaint();
                delta--;
                drawCount++;

            }
            if(timer>=1000000000){
                System.out.println("FPS:"+drawCount);
                drawCount=0;
                timer=0;

            }


        }
    }

    public void update(){
        player.update();



    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);
        player.draw(g2);


        g2.dispose();


    }

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);//this will keep the game panel focused on keystrokes


    }
}
