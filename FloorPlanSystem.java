import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/* All ticketing system requirements: bit.ly/TicketSys
*/

class FloorPlanSystem extends JPanel {
    //private final int MAX_X = (int)getToolkit().getScreenSize().getWidth();
    //private final int MAX_Y = (int)getToolkit().getScreenSize().getHeight();
    private final int MAX_X = 1920;
    private final int MAX_Y = 1080;
    static FloorPlanSystem floor;
    static FloorViewPanel floorViewPanel;
    static ControlPanel controlPanel;
    static ArrayList<Table> tables;
    static FloorView floorView;
    static TableView tableView;

    //main
    public static void main(String[] args) {
        tables = new ArrayList<Table>();
        for (int i = 0; i<15; i++){
            Table t = new Table(1);
            ArrayList<Student> students = new ArrayList<Student>();
            for (int j = 0; j<5; j++){
                Student s = new Student("","");
                String cwd = new File("").getAbsolutePath();
                String imagestring = cwd + "\\photos\\person" + j + ".jpg";
                BufferedImage img = null;
                try {
                    img = ImageIO.read(new File(imagestring));
                } catch (IOException e) {
                    System.out.println("failed");
                }
                s.setPicture(img);
                students.add(s);
            }
            t.setStudents(students);
            tables.add(t);
        }

        floor = new FloorPlanSystem(tables);
    }

    FloorPlanSystem(ArrayList<Table> myTables) {
        this.tables = myTables;
        floorView = new FloorView(MAX_X, MAX_Y, tables);
        //create a cardlayout top layer frame and add it to the frame
        floorViewPanel = new FloorViewPanel();
        controlPanel = new ControlPanel();
        floorViewPanel.add(controlPanel);
        this.add(floorViewPanel);

        //set frame dimensions
        this.setSize(MAX_X, MAX_Y);
        
        //create and attach a key listener
        //make the frame active and visible
        this.requestFocusInWindow();
        this.setVisible(true);

    }
    public void paintComponent(Graphics g) {
        setDoubleBuffered(true);
        floorView.draw(g);
        repaint();
    }

//------------------------------------------------------------------------------
//  inner class
//------------------------------------------------------------------------------        
    private class FloorViewPanel extends JPanel {
        FloorViewPanel(){
            DisplayChangeListener myML = new DisplayChangeListener();
            addMouseListener(myML);
            addMouseMotionListener(myML);
            addMouseWheelListener(myML);
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
        }
    }
//------------------------------------------------------------------------------
//  inner class
//------------------------------------------------------------------------------
    private class ControlPanel extends JPanel {
        private JButton zoomIn, zoomOut, moveLeft, moveRight;
        ControlPanel(){
            //button stuff
            zoomIn = new JButton("Zoom In");
            zoomIn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    floorView.trans.changeZoom(1.25);
                }
            });
            zoomOut = new JButton("Zoom Out");
            zoomOut.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    floorView.trans.changeZoom(0.8);
                }
            });
            moveLeft = new JButton("Move Left");
            moveLeft.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    floorView.trans.changeTranslate(500,0);
                }
            });
            moveRight= new JButton("Move Right");
            moveRight.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    floorView.trans.changeTranslate(-500,0);
                }
            });
            //
            this.add(zoomIn);
            this.add(zoomOut);
            this.add(moveLeft);
            this.add(moveRight);

        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setDoubleBuffered(true);
            repaint();
        }
    }

//------------------------------------------------------------------------------
//  inner class
//------------------------------------------------------------------------------     
    private class DisplayChangeListener implements MouseListener, MouseMotionListener, MouseWheelListener {
        int xClicked, yClicked, xReleased, yReleased, currentX, currentY;
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {}

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            xClicked = mouseEvent.getX();
            yClicked = mouseEvent.getY();
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            xReleased = mouseEvent.getX();
            yReleased = mouseEvent.getY();
            floorView.trans.changeTranslate((xReleased-xClicked),(yReleased-yClicked));
        }
        @Override
        public void mouseEntered(MouseEvent mouseEvent) {}

        @Override
        public void mouseExited(MouseEvent mouseEvent) {}

        @Override
        public void mouseDragged(MouseEvent mouseEvent){}

        @Override
        public void mouseMoved(MouseEvent mouseEvent){
            currentX = mouseEvent.getX();
            currentY = mouseEvent.getY();
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
            if (mouseWheelEvent.getWheelRotation()<0){
                floorView.trans.changeZoom(1.25, currentX, currentY);
            } else if (mouseWheelEvent.getWheelRotation()>0){
                floorView.trans.changeZoom(0.8, currentX, currentY);
            }
        }
    }
}
