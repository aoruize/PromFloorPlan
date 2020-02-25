import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/* All ticketing system requirements: bit.ly/TicketSys
*/

class FloorPlanSystem extends JFrame {
    //private final int MAX_X = (int)getToolkit().getScreenSize().getWidth();
    //private final int MAX_Y = (int)getToolkit().getScreenSize().getHeight();
    private final int MAX_X = 1920;
    private final int MAX_Y = 1080;
    static FloorPlanSystem floor;
    static ProgramAreaPanel panel;
    static ArrayList<Table> tables;
    static FloorView floorView;
    static TableView tableView;
    static Table table;
    static JButton zoomIn, zoomOut;

    //main
    public static void main(String[] args) {
        tables = new ArrayList<Table>();
        for (int i = 0; i<250; i++){
            Table t = new Table(1,1,1);
            tables.add(t);
        }
        /*
        table = new Table(5,0,0);
        ArrayList<Student> students = new ArrayList<Student>();
        for (int i = 0; i<5; i++){
            Student s = new Student("","");
            students.add(s);
        }
        table.setStudents(students);
        */
        floor = new FloorPlanSystem();
    }    
    
    FloorPlanSystem() {
        super("FloorPlanSystem");



        floorView = new FloorView(MAX_X, MAX_Y, tables);
        //tableView = new TableView(MAX_X, MAX_Y, table);
        //create the panel and add it to the frame
        panel = new ProgramAreaPanel();

        //button stuff
        zoomIn = new JButton("Zoom In");
        zoomIn.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                             floorView.trans.changeZoom(2);
                                        }
                                        });
        zoomOut = new JButton("Zoom Out");
        zoomOut.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            floorView.trans.changeZoom(0.5);
                                        }
                                        });
        //
        panel.add(zoomIn);
        panel.add(zoomOut);

        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        //set frame dimensions
        this.setSize(MAX_X, MAX_Y);
        
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
            floorView.draw(g);
            //tableView.draw(g);
            
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

        }
        
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT){
                System.out.println("");
            }
        }
        
        @Override
        public void keyReleased(KeyEvent keyEvent) {
            
        }
    }
}
