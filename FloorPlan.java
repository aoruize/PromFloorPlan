/**
 * This template can be used as reference or a starting point for the Shape Game
 **/

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class FloorPlan extends JFrame {
    private final int MAX_X = (int)getToolkit().getScreenSize().getWidth();
    private final int MAX_Y = (int)getToolkit().getScreenSize().getHeight();
    static FloorPlan floor;
    private ProgramAreaPanel panel;

    //main
    public static void main(String[] args) {
        floor = new FloorPlan(); 
    }    
    
    FloorPlan() {
        super("FloorPlan");

        //create enemies and player

        //create the panel and add it to the frame
        panel = new ProgramAreaPanel();

        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //set frame dimensions
        this.setSize(MAX_X, MAX_Y);

        //set to fullscreen
//        this.setSize(MAX_X,MAX_Y);
        //or
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //remove borders
//        this.setUndecorated(true);
        
        //create and attach a key listener
        this.addKeyListener(new MyKeyListener());
        //make the frame active and visible
        this.requestFocusInWindow();
        this.setVisible(true);
    }

    void redraw () {
        panel.repaint();
    }
//------------------------------------------------------------------------------
//  inner class
//------------------------------------------------------------------------------        
    private class ProgramAreaPanel extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setDoubleBuffered(true); 
            
            //move enemies
            
            //check for collision
            
            //draw Stuff Here

    //example drawing:
            //change color
            g.setColor(Color.BLUE);
            //draw Rectangle
            g.fillOval(50, 100, 50, 50);
            //change color
            g.setColor(new Color(255, 0, 255));
            //draw ellipse
            g.fillRect(200, 200, 20, 20);
            
            repaint();
        }
    }
//------------------------------------------------------------------------------
//  inner class
//------------------------------------------------------------------------------     
    private class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent keyEvent) {
            char keyChar = keyEvent.getKeyChar();
            if(keyChar == 'a' ){   
                System.out.println("move left");     
            } else if(keyChar == 'd' ){
                System.out.println("move right"); ;
            } 
        }
        
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT){
                System.out.println("move left");
            } else if (keyCode == KeyEvent.VK_RIGHT){
                System.out.println("move right");
            } else if (keyCode == KeyEvent.VK_ESCAPE){
                System.out.println("Quitting!"); 
                game.dispose(); //close the frame & quit
            }
        }
        
        @Override
        public void keyReleased(KeyEvent keyEvent) {
            
        }
    }
}
