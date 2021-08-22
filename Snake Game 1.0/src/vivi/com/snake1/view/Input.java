/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivi.com.snake1.view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author spide
 */
public class Input implements KeyListener {

    boolean up, down, left, right, enter;
    //because snake rules it should be that up would make all other keys false, vice versa
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP){
            allKeysFalse();
            up = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            allKeysFalse();
            down = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            allKeysFalse();
            left = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            allKeysFalse();
            right = true;
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            enter = true;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            enter = false;
        }
    }
    
    public void allKeysFalse(){
        up = false;
        down = false;
        left = false;
        right = false;
    }
    
}
