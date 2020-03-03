import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/* All ticketing system requirements: bit.ly/TicketSys
*/

class FloorPlanSystem extends JFrame {
    //private final int MAX_X = (int)getToolkit().getScreenSize().getWidth();
    //private final int MAX_Y = (int)getToolkit().getScreenSize().getHeight();
    private final int MAX_X = 1920;
    private final int MAX_Y = 1080;
    static FloorPlanSystem floor;
    static FloorViewPanel floorViewPanel;
    static ControlPanel controlPanel;
    static TableViewPanel tableViewPanel;
    static ArrayList<Table> tables;
    static FloorView floorView;
    static TableView tableView;
    static Table table;

    //main
    public static void main(String[] args) {
        tables = new ArrayList<Table>();
        for (int i = 0; i<45; i++){
            Table t = new Table(1,1,1);
            ArrayList<Student> students = new ArrayList<Student>();
            for (int j = 0; j<9; j++){
                Student s = new Student("","");
                students.add(s);
            }
            t.setStudents(students);
            tables.add(t);
        }

        floor = new FloorPlanSystem();
    }    
    
    FloorPlanSystem() {
        super("FloorPlanSystem");
        floorView = new FloorView(MAX_X, MAX_Y, tables);
        //create a cardlayout top layer frame and add it to the frame
        JPanel cardContentPane = new JPanel(new CardLayout());
        floorViewPanel = new FloorViewPanel();
        controlPanel = new ControlPanel();
        tableViewPanel = new TableViewPanel();
        floorViewPanel.add(controlPanel);
        this.setContentPane(cardContentPane);
        this.getContentPane().add(floorViewPanel, "FLOORVIEWPANEL");
        this.getContentPane().add(tableViewPanel, "TABLEVIEWPANEL");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set frame dimensions
        this.setSize(MAX_X, MAX_Y);
        
        //create and attach a key listener
        //make the frame active and visible
        this.requestFocusInWindow();
        this.setVisible(true);

    }

    void redraw () {
        floorViewPanel.repaint();
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
            setDoubleBuffered(true); 
            floorView.draw(g);
            repaint();
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
    private class TableViewPanel extends JPanel {
        TableViewPanel(){
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setDoubleBuffered(true);
            tableView.draw(g);
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
