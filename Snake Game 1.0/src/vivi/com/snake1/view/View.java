/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivi.com.snake1.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Vivian
 */
public class View extends Canvas {
    
    private Thread thread;
    private BufferedImage backbuffer;
    private BufferStrategy bs;
    private boolean running = false;
    private double refresh_period = 1e9 / 30.0;
    
    public int screen[][] = new int[17][24];
    public int tileSize = 32;
    
    //game
    Snake snake = new Snake();
    Input keys = new Input();
    
    public void start() {
        createBufferStrategy(2);
        bs = getBufferStrategy();
        backbuffer = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        init();
        
        running = true;
        thread = new Thread(new MainLoop());
        thread.start();
        this.addKeyListener(keys);
    }
    
    public void init(){
        //initializes tile ids
        for(int y=0;y<screen.length;y++){
            for(int x=0;x<screen[0].length;x++){
                screen[y][x] = 4;
            }
        }
        for(int y=0;y<snake.board.length;y++){
            for(int x=0;x<snake.board[0].length;x++){
                screen[y + 2][x + 2] = snake.board[y][x];
            }
        }
        //shows example of apple and snake part
        screen[3][18] = 2;
        screen[5][18] = 3;
    }
    
    private class MainLoop implements Runnable {

        @Override
        public void run() {
            long currentTime = System.nanoTime();
            long lastTime = currentTime;
            long delta;
            long unprocessedTime = 0;
            boolean updated = false;
            while(running){
                currentTime = System.nanoTime();
                delta = currentTime - lastTime;
                unprocessedTime += delta;
                lastTime = currentTime;
                if(unprocessedTime > refresh_period){
                    unprocessedTime -= refresh_period;
                    update();
                    updated = true;
                }
                if(updated){
                    Graphics2D g =(Graphics2D) bs.getDrawGraphics();
                    draw((Graphics2D) backbuffer.getGraphics());
                    g.drawImage(backbuffer, 0, 0, 800, 600, null);
                    
                    g.dispose();
                    bs.show();
                    updated = false;
                }
                try {
                    Thread.sleep(5);
                } catch (InterruptedException ex) {
                    
                }
            }
        }
        
        private void draw(Graphics2D g){
            g.clearRect(0, 0, 800, 600);
            g.translate(7, 7);
            drawScreen(g);
            Font gameFont = new Font("Comic Sans MS", 10,20);
            g.setFont(gameFont);
            g.setColor(Color.WHITE);
            g.drawString("Snake size: " + (snake.maxParts + 1), 620, 190);
            g.drawString("Apples eaten: " + snake.score, 620, 130);
            if(snake.game_over){
                Font tempFont = new Font("Comic Sans MS", 50,50);
                g.setFont(tempFont);
                g.setColor(Color.YELLOW);
                g.drawString("GAME OVER", 200, 200);
                g.drawString("Press ENTER to play again", 100, 250);
            }
        }
        
        private void update(){
            border();
            boardToScreen();
            snake.game(keys);
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                
            }
        }
        
        private void drawScreen(Graphics2D g){
            int dx = 0;
            int dy = 0;
            for(int y=0;y<screen.length;y++){
                for(int x=0;x<screen[0].length;x++){
                    g.setColor(findColor(screen[y][x]));
                    g.fillOval(dx, dy, tileSize, tileSize);
                    //Outline over tiles
                    if(findColor(screen[y][x]) == Color.WHITE){
                        g.setColor(Color.DARK_GRAY);
                        g.drawOval(dx, dy, tileSize, tileSize);
                        g.drawOval(dx, dy, tileSize - 1, tileSize - 1);
                        g.drawOval(dx, dy, tileSize - 2, tileSize - 2);
                    }
                    if(findColor(screen[y][x]) == Color.GREEN){
                        g.setColor(Color.ORANGE);
                        g.drawOval(dx, dy, tileSize, tileSize);
                        g.drawOval(dx, dy, tileSize - 1, tileSize - 1);
                        g.drawOval(dx, dy, tileSize - 2, tileSize - 2);
                    }
                    if(findColor(screen[y][x]) == Color.RED){
                        g.setColor(Color.BLUE);
                        g.drawOval(dx, dy, tileSize, tileSize);
                        g.drawOval(dx, dy, tileSize - 1, tileSize - 1);
                        g.drawOval(dx, dy, tileSize - 2, tileSize - 2);
                    }
                    dx += tileSize;
                }
                dx = 0;
                dy += tileSize;
            }
        }
        
        
        private void border(){
            for(int y=0;y<snake.border.length;y++){
                for(int x=0;x<snake.border[0].length;x++){
                screen[y + 1][x + 1] = snake.border[y][x];
                }
            }
        }
        
        private void boardToScreen(){
            for(int y=0;y<snake.board.length;y++){
                for(int x=0;x<snake.board[0].length;x++){
                screen[y + 2][x + 2] = snake.board[y][x];
                }
            }
        }
        
        private Color findColor(int id){
            switch(id){
                case 0: return Color.BLACK;
                case 1: return Color.WHITE;
                case 2: return Color.RED;
                case 3: return Color.GREEN;
                case 4: return Color.GRAY;
                default: return Color.BLACK;
            }
        }
    }
    
}
