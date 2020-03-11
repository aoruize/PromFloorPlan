import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import static java.awt.BorderLayout.*;

/**
 * FloorPlan.class
 * Main panel
 * @author Mike
 */
class FloorPlanSystem extends JPanel {
    private final int MAX_X = (int)getToolkit().getScreenSize().getWidth();
    private final int MAX_Y = (int)getToolkit().getScreenSize().getHeight();
    static FloorViewPanel floorViewPanel;
    static TableViewPanel tableViewPanel;
    static FloorControlPanel floorControlPanel;
    static TableControlPanel tableControlPanel;
    static ArrayList<Table> tables;
    static FloorView floorView;
    static TableView tableView;
    static FloorPlanSystem rootPanel;

    /**
     * FloorPlanSystem
     * constructor for FloorPlanSystem
     * @param myTables ArrayList of tables to be made into floor plan
     */
    FloorPlanSystem(ArrayList<Table> myTables) {
        //ref to
        this.rootPanel = this;
        this.setLayout(new CardLayout());
        this.tables = myTables;

        floorView = new FloorView(MAX_X, MAX_Y, tables);
        floorViewPanel = new FloorViewPanel(floorView);
        floorControlPanel = new FloorControlPanel(floorView);
        floorViewPanel.add(floorControlPanel, PAGE_START);
        this.add(floorViewPanel, "Floor");

        tableView = new TableView(MAX_X, MAX_Y, new Table(1));
        tableViewPanel = new TableViewPanel(tableView);
        tableControlPanel =  new TableControlPanel(tableView);
        tableViewPanel.add(tableControlPanel, PAGE_START);
        this.add(tableViewPanel, "Table");

        //set frame dimensions
        this.setSize(MAX_X, MAX_Y);

        //create and attach a key listener
        //make the frame active and visible
        this.requestFocusInWindow();
        this.setVisible(true);

    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        repaint();
    }

    //------------------------------------------------------------------------------
    //  inner class
    //------------------------------------------------------------------------------
    private class FloorViewPanel extends JPanel {
        private FloorView floorView;
        FloorViewPanel(FloorView thisFloorView){
            this.setLayout(new BorderLayout());
            this.floorView = thisFloorView;
            DisplayChangeListener myML = new DisplayChangeListener(floorView);
            addMouseListener(myML);
            addMouseMotionListener(myML);
            addMouseWheelListener(myML);
            this.setVisible(true);
        }
        @Override
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
    private class TableViewPanel extends JPanel {
        private TableView tableView;
        TableViewPanel(TableView thisTableView){
            this.setLayout(new BorderLayout());
            this.tableView = thisTableView;
            this.setVisible(true);
        }

        public void setTableView(TableView tableView) {
            this.tableView = tableView;
            TableViewListener tvl = new TableViewListener(tableView);
            addMouseListener(tvl);
        }

        @Override
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
    private class FloorControlPanel extends JPanel {
        private JButton zoomIn, zoomOut, moveLeft, moveRight, moveUp, moveDown;
        FloorControlPanel(FloorView thisFloorView){
            //button stuff
            zoomIn = new JButton("Zoom In");
            zoomIn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    thisFloorView.getTrans().changeZoom(1.25);
                }
            });
            zoomOut = new JButton("Zoom Out");
            zoomOut.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    thisFloorView.getTrans().changeZoom(0.8);
                }
            });
            moveLeft = new JButton("Move Left");
            moveLeft.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    thisFloorView.getTrans().changeTranslate(500,0);
                }
            });
            moveRight= new JButton("Move Right");
            moveRight.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    thisFloorView.getTrans().changeTranslate(-500,0);
                }
            });
            moveUp = new JButton("Move Up");
            moveUp.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    thisFloorView.getTrans().changeTranslate(0,500);
                }
            });
            moveDown = new JButton("Move Down");
            moveDown.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    thisFloorView.getTrans().changeTranslate(0,-500);
                }
            });
            //
            this.add(zoomIn);
            this.add(zoomOut);
            this.add(moveLeft);
            this.add(moveRight);
            this.add(moveUp);
            this.add(moveDown);
        }
        @Override
        public void paintComponent(Graphics g) {
        }
    }
    //------------------------------------------------------------------------------
    //  inner class
    //------------------------------------------------------------------------------
    private class TableControlPanel extends JPanel {
        private JButton goBack;
        TableControlPanel(TableView thisTableView){
            //button stuff
            goBack = new JButton("Return to the original ");
            goBack.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    CardLayout cL = (CardLayout)rootPanel.getLayout();
                    cL.next(rootPanel);
                }
            });
            this.add(goBack);
        }
        @Override
        public void paintComponent(Graphics g) {
        }
    }
    //------------------------------------------------------------------------------
    //  inner class
    //------------------------------------------------------------------------------
    private class DisplayChangeListener implements MouseListener, MouseMotionListener, MouseWheelListener {
        int xClicked, yClicked, xPressed, yPressed, xReleased, yReleased, currentX, currentY;
        FloorView thisFloorView;
        Table tableClicked;
        TableInfoPanel tip;
        boolean tableDisplayed = false;
        DisplayChangeListener(FloorView myFloorView){
            thisFloorView = myFloorView;
        }
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            xClicked = mouseEvent.getX();
            yClicked = mouseEvent.getY();
            tableClicked = thisFloorView.getTableByCoord(xClicked, yClicked);
            if (tableDisplayed){
                floorViewPanel.remove(tip);
                tableDisplayed = false;
            }
            if (tableClicked != null){
                tip = new TableInfoPanel(tableClicked);
                floorViewPanel.add(tip, PAGE_END);
                floorViewPanel.revalidate();
                tableDisplayed = true;
            }
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            xPressed = mouseEvent.getX();
            yPressed = mouseEvent.getY();
        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            xReleased = mouseEvent.getX();
            yReleased = mouseEvent.getY();
            floorView.getTrans().changeTranslate((xReleased- xPressed),(yReleased- yPressed));
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
                floorView.getTrans().changeZoom(1.25, currentX, currentY);
            } else if (mouseWheelEvent.getWheelRotation()>0){
                floorView.getTrans().changeZoom(0.8, currentX, currentY);
            }
        }
    }

    /**
     * TableViewListener
     *
     */
    private class TableViewListener implements MouseListener{
        int xClicked, yClicked;
        TableView thisTableView;
        Student studentClicked;
        StudentInfoPanel sip;
        boolean studentDisplayed = false;
        TableViewListener(TableView myTableView){
            thisTableView = myTableView;
        }
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            xClicked = mouseEvent.getX();
            yClicked = mouseEvent.getY();
            studentClicked = thisTableView.getStudentByCoord(xClicked, yClicked);
            if (studentDisplayed){
                tableViewPanel.remove(sip);
                studentDisplayed = false;
            }
            if (studentClicked != null){
                sip = new StudentInfoPanel(studentClicked);
                tableViewPanel.add(sip, PAGE_END);
                tableViewPanel.revalidate();
                studentDisplayed = true;
            }
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {}

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {}

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {}

        @Override
        public void mouseExited(MouseEvent mouseEvent) {}

    }

    /**
     * TableInfoPanel
     * A panel to display information about a specific table
     * @author Mike
     */
    private class TableInfoPanel extends JPanel {
        private Table thisTable;
        private JButton detailedView;
        private ArrayList<Student> studentList;
        private String studentsString = "Students: ";
        private String accommodationsString = "Accommodations: ";
        private JLabel studentsLabel, accommodationsLabel;

        /**
         * Constructor for TableInfoPanel
         * @param t table to display into about
         */
        TableInfoPanel(Table t){
            this.thisTable = t;
            this.setVisible(true);
            //Builds string of student names
            studentList = thisTable.getStudents();
            for (Student s: studentList){
                studentsString += s.getName() + " ";
                ArrayList<String> sa = s.getAccommodations();
                if (!sa.isEmpty()){
                    //builds string of accomodations
                    for (String accom : sa){
                        accommodationsString += sa + ", ";
                    }
                    accommodationsString = accommodationsString.substring(0,accommodationsString.length()-2);
                }
            }
            //if no accomodations, get rid of
            if (accommodationsString.equals("Accommodations: ")){
                accommodationsString += "N/A";
            }
            //add labels
            studentsLabel = new JLabel(studentsString);
            this.add(studentsLabel);
            accommodationsLabel = new JLabel(accommodationsString);
            this.add(accommodationsLabel);

            //create and add button that switches to table view
            detailedView = new JButton("See Table Details");
            detailedView.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    tableViewPanel.setTableView(new TableView(MAX_X, MAX_Y, thisTable));
                    tableViewPanel.revalidate();
                    CardLayout cL = (CardLayout)rootPanel.getLayout();
                    cL.next(rootPanel);
                }
            });
            this.add(detailedView);
        }
        @Override
        public void paintComponent(Graphics g) {
        }
    }

    /**
     * StudentInfoPanel
     * panel to draw information about a student, when they are clicked
     * @author Mike
     */
    private class StudentInfoPanel extends JPanel {
        private Student thisStudent;
        private String infoString = "";
        private JLabel infoLabel;
        StudentInfoPanel(Student s){
            this.thisStudent = s;
            this.setVisible(true);
            infoString += "Name: " + s.getName();
            infoString += " Student # " + s.getId();
            ArrayList<String> sa = s.getAccommodations();
            if (!sa.isEmpty()){
                infoString += "Accomodations: ";
                for (String accom : sa){
                    infoString += accom + ", ";
                }
                infoString = infoString.substring(0,infoString.length()-2);
            }
            infoLabel = new JLabel(infoString);
            this.add(infoLabel);
        }

        /**
         * paintComponent
         * empty override
         * @param g Graphics
         */
        @Override
        public void paintComponent(Graphics g) {
        }
    }
}