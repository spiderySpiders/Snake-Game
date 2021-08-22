/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivi.com.snake1.view;

import java.awt.Point;
import java.util.ArrayList;

public class Snake {
    //Sloppy snake game
    
    public int[][] border = new int[16][16];
    public int[][] board = new int[14][14];
    private ArrayList<Point> snakePart = new ArrayList<Point>();
    private int snakeId = 3;
    private int appleId = 2;
    public int maxParts = 0;
    private Point lastPart;
    private ArrayList<Point> appleLocations = new ArrayList<Point>();
    private int apples = 0;
    public int score = 0;
    
    public boolean game_over = false;
    
    public Snake(){
        init();
    }
    
    public void game(Input keys){
        if(!game_over){
            move(keys);
            if(apples <= 0){
                createApple();
            }
        }
        if(keys.enter){
            init();
        }
    }
    
    public void move(Input keys){
        try{
            lastPart = new Point(snakePart.get(0).x, snakePart.get(0).y);
            Point nextPosition = null;
            if(keys.up){
                nextPosition = new Point(snakePart.get(maxParts).x, snakePart.get(maxParts).y-1);
                snakePart.add(nextPosition);
                if(snakeCollide(nextPosition)){
                game_over = true;
                }
                board[lastPart.y][lastPart.x] = 0;
                snakePart.remove(lastPart);
            }
            else if(keys.down){
                nextPosition = new Point(snakePart.get(maxParts).x, snakePart.get(maxParts).y+1);
                snakePart.add(nextPosition);
                if(snakeCollide(nextPosition)){
                game_over = true;
                }
                board[lastPart.y][lastPart.x] = 0;
                snakePart.remove(lastPart);
            }
            else if(keys.left){
                nextPosition = new Point(snakePart.get(maxParts).x-1, snakePart.get(maxParts).y);
                snakePart.add(nextPosition);
                if(snakeCollide(nextPosition)){
                game_over = true;
                }
                board[lastPart.y][lastPart.x] = 0;
                snakePart.remove(lastPart);
            }
            else if(keys.right){
                nextPosition = new Point(snakePart.get(maxParts).x+1, snakePart.get(maxParts).y);
                snakePart.add(nextPosition);
                if(snakeCollide(nextPosition)){
                game_over = true;
                }
                board[lastPart.y][lastPart.x] = 0;
                snakePart.remove(lastPart);
            }   
            if(snakeAteApple() && maxParts <= 25){
                grow(nextPosition);
            }
            snakeToBoard();
        }catch(IndexOutOfBoundsException ex){
            game_over = true;
        }
    }
    
    public boolean snakeCollide(Point nextPoint){
        if(board[nextPoint.y][nextPoint.x] == snakeId){
            return true;
        }
        return false;
    }
    
    public void createApple(){
        if(apples <= 0){
            apples++;
            System.out.println(apples);
        }
        for(int i=0;i<apples;i++){
            appleLocations.add(i, new Point((int) 
                    (board.length * Math.random()), 
                    (int) (board[0].length * Math.random())));
            
            board[appleLocations.get(i).y]
                [appleLocations.get(i).x] = appleId;
        }
    }
    
    public void snakeToBoard(){
        for(int i=0;i<snakePart.size();i++){
            board[snakePart.get(i).y][snakePart.get(i).x] = snakeId;
        }
    }
    
    public boolean snakeAteApple(){
        for(int i=0;i<appleLocations.size();i++){
            if(board[appleLocations.get(i).y][appleLocations.get(i).x] == 0){
                appleLocations.remove(i);
                apples--;
                score++;
                createApple();
                return true;
            }
        }
        return false;
    }
    
    public void grow(Point lastPoint){
        maxParts++;
        snakePart.add(maxParts, lastPoint);
        System.out.println(maxParts);
    }
    
    public void init(){
        //sets board tile ids
        for(int y=0;y<border.length;y++){
            for(int x=0;x<border[0].length;x++){
                border[y][x] = 1;
            }
        }
        for(int y=0;y<board.length;y++){
            for(int x=0;x<board[0].length;x++){
                board[y][x] = 0;
            }
        }
        maxParts = 0;
        apples = 0;
        score = 0;
        if(snakePart.size() > 0){
            removeParts();
        }
        if(appleLocations.size() > 0){
            removeApples();
        }
        snakePart.add(new Point(board.length / 2, board[0].length / 2));
        System.out.println(snakePart.size());
        board[snakePart.get(0).x][snakePart.get(0).y] = snakeId;
        game_over = false;
    }
    
    public void removeParts(){
        snakePart.clear();
    }
    
    public void removeApples(){
        appleLocations.clear();
    }
}
