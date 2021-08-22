/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vivi.com.snake1.main;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import vivi.com.snake1.view.View;

/**
 *
 * @author spide
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        JFrame frame = new JFrame("Snake Game 1.0");
        View view = new View();
        frame.setPreferredSize(new Dimension(800, 600));
        frame.getContentPane().add(view);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        
        view.requestFocus();
        view.start();
        });
    }
    
}
