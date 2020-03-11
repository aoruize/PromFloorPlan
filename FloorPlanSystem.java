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
        //ref to top panel because repeated parent() is annoying, plus setup
        this.rootPanel = this;
        this.setLayout(new CardLayout());
        this.tables = myTables;

        //floor setup
        floorViewPanel = new FloorViewPanel();
        this.add(floorViewPanel, "Floor");

        //table setup
        tableViewPanel = new TableViewPanel();
        this.add(tableViewPanel, "Table");

        //set frame dimensions
        this.setSize(MAX_X, MAX_Y);

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
    //  inner classes
    //------------------------------------------------------------------------------

    /**
     * FloorViewPanel
     * Panel to display floor view
     * @author Mike
     */
    private class FloorViewPanel extends JPanel {
        private FloorView floorView;
        private FloorControlPanel floorControlPanel;

        /**
         * FloorViewPanel
         * Constructor for FloorViewPanel
         */
        FloorViewPanel(){
            this.setLayout(new BorderLayout());
            this.floorView = new FloorView(MAX_X, MAX_Y, tables);
            this.floorControlPanel = new FloorControlPanel(this.floorView);
            this.add(floorControlPanel, PAGE_START);
            this.setVisible(true);
            DisplayChangeListener displayChangeListener = new DisplayChangeListener(floorView);
            addMouseListener(displayChangeListener);
            addMouseMotionListener(displayChangeListener);
            addMouseWheelListener(displayChangeListener);
        }

        /**
         * paintComponent
         * draws tables and students
         * @param g Graphics object
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setDoubleBuffered(true);
            floorView.draw(g);
            repaint();
        }
        //------------------------------------------------------------------------------
        //  inner x2 clases
        //------------------------------------------------------------------------------
        /**
         * FloorControlPanel.class
         * Panel to display navigation buttons
         * @author Mike
         */
        private class FloorControlPanel extends JPanel {
            private JButton zoomIn, zoomOut, moveLeft, moveRight, moveUp, moveDown;

            /**
             * FloorControlPanel
             * constructor for FloorControlPanel
             * @param aFloorView floorView containing floorplan data
             */
            FloorControlPanel(FloorView aFloorView){
                //button stuff
                zoomIn = new JButton("Zoom In");
                zoomIn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        aFloorView.getTrans().changeZoom(1.25);
                    }
                });
                zoomOut = new JButton("Zoom Out");
                zoomOut.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        aFloorView.getTrans().changeZoom(0.8);
                    }
                });
                moveLeft = new JButton("Move Left");
                moveLeft.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        aFloorView.getTrans().changeTranslate(500,0);
                    }
                });
                moveRight= new JButton("Move Right");
                moveRight.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        aFloorView.getTrans().changeTranslate(-500,0);
                    }
                });
                moveUp = new JButton("Move Up");
                moveUp.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        aFloorView.getTrans().changeTranslate(0,500);
                    }
                });
                moveDown = new JButton("Move Down");
                moveDown.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        aFloorView.getTrans().changeTranslate(0,-500);
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
            public void paintComponent(Graphics g) {}
        }

        /**
         * DisplayChangeListener.class
         * Listener for mouse actions on FloorView
         * @author Mike
         */
        private class DisplayChangeListener implements MouseListener, MouseMotionListener, MouseWheelListener {
            private int xClicked, yClicked, xPressed, yPressed, xReleased, yReleased, currentX, currentY;
            private FloorView floorView;
            private Table tableClicked;
            private TableInfoPanel tableInfoPanel;
            private boolean tableDisplayed = false;

            /**
             * DisplayChangeListener
             * Constructor for DisplayChangeListener
             * @param aFloorView
             */
            DisplayChangeListener(FloorView aFloorView){
                this.floorView = aFloorView;
            }

            /**
             * mouseClicked
             * overridden method, gets click location and checks for table there
             * @param e mouse event
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                this.xClicked = e.getX();
                this.yClicked = e.getY();
                this.tableClicked = floorView.getTableByCoord(xClicked, yClicked);
                if (tableDisplayed){
                    floorViewPanel.remove(tableInfoPanel);
                    tableDisplayed = false;
                }
                if (tableClicked != null){
                    tableInfoPanel = new TableInfoPanel(tableClicked);
                    floorViewPanel.add(tableInfoPanel, PAGE_END);
                    floorViewPanel.revalidate();
                    tableDisplayed = true;
                }
            }

            /**
             * mousePressed
             * tracks when mouse was pressed, for panning
             * @param e mouse event
             */
            @Override
            public void mousePressed(MouseEvent e) {
                xPressed = e.getX();
                yPressed = e.getY();
            }

            /**
             * mouseReleased
             * tracks when mouse was released, for panning, then performs pan
             * @param e
             */
            @Override
            public void mouseReleased(MouseEvent e) {
                xReleased = e.getX();
                yReleased = e.getY();
                FloorViewPanel.this.floorView.getTrans().changeTranslate((xReleased- xPressed),(yReleased- yPressed));
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mouseDragged(MouseEvent e){}

            @Override
            public void mouseMoved(MouseEvent e){
                currentX = e.getX();
                currentY = e.getY();
            }

            /**
             * mouseWheelMoved
             * tracks mouse wheel movement, for zooming, then performs
             * @param e mouse wheel event
             */
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation()<0){
                    //zoom in
                    FloorViewPanel.this.floorView.getTrans().changeZoom(1.25, currentX, currentY);
                } else if (e.getWheelRotation()>0){
                    //zoom out
                    FloorViewPanel.this.floorView.getTrans().changeZoom(0.8, currentX, currentY);
                }
            }
        }

        /**
         * TableInfoPanel
         * A panel to display information about a specific table
         * @author Mike
         */
        private class TableInfoPanel extends JPanel {
            private Table table;
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
                this.table = t;
                this.setVisible(true);
                //Builds string of student names
                studentList = table.getStudents();
                for (Student s: studentList){
                    studentsString += s.getName() + " ";
                    ArrayList<String> sa = s.getAccommodations();
                    if (!sa.isEmpty()){
                        //builds string of accommodations
                        for (String accommodation : sa){
                            accommodationsString += accommodation + ", ";
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
                        tableViewPanel.setTableView(new TableView(MAX_X, MAX_Y, table));
                        tableViewPanel.revalidate();
                        CardLayout cL = (CardLayout)rootPanel.getLayout();
                        cL.next(rootPanel);
                    }
                });
                this.add(detailedView);
            }
            @Override
            public void paintComponent(Graphics g) {}
        }
    }

    /**
     * TableViewPanel
     * Panel to display table view
     * @author Mike
     */
    private class TableViewPanel extends JPanel {
        private TableView tableView;
        private TableControlPanel tableControlPanel;

        /**
         * TableViewPanel
         * Constructor for TableViewPanel
         */
        TableViewPanel(){
            this.setLayout(new BorderLayout());
            this.tableView = new TableView(MAX_X, MAX_Y, new Table(1));
            this.tableControlPanel = new TableControlPanel(tableView);
            this.add(tableControlPanel, PAGE_START);
            this.setVisible(true);
        }

        /**
         * setTableView
         * method to change table displayed in table
         * @param aTableView table view
         */
        public void setTableView(TableView aTableView) {
            this.tableView = aTableView;
            TableViewListener tableViewListener = new TableViewListener(aTableView);
            addMouseListener(tableViewListener);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setDoubleBuffered(true);
            tableView.draw(g);
            repaint();
        }
        //------------------------------------------------------------------------------
        //  inner x2 clases
        //------------------------------------------------------------------------------
        /**
         * TableControlPanel.class
         * Panel to display button to return to floor view
         * @author Mike
         */
        private class TableControlPanel extends JPanel {
            private JButton goBack;

            /**
             * TableControlPanel
             * constructor for TableControlPanel
             * @param aTableView tableView containing table data
             */
            TableControlPanel(TableView aTableView){
                //button stuff
                goBack = new JButton("Return to the floor plan ");
                goBack.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //change back to floor plan
                        CardLayout cL = (CardLayout)rootPanel.getLayout();
                        cL.next(rootPanel);
                    }
                });
                this.add(goBack);
            }
            @Override
            public void paintComponent(Graphics g) {}
        }

        /**
         * TableViewListener.class
         * MouseListener which checks if the mouse clicks on a student
         * @author Mike
         */
        private class TableViewListener implements MouseListener{
            int xClicked, yClicked;
            TableView thisTableView;
            Student studentClicked;
            StudentInfoPanel sip;
            boolean studentDisplayed = false;

            /**
             * TableViewListener
             * Constructor for TableViewListener
             * @param myTableView a tableView containing table data
             */
            TableViewListener(TableView myTableView){
                thisTableView = myTableView;
            }

            /**
             * mouseClicked
             * overridden method, gets click location and checks for student there
             * @param e event
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                xClicked = e.getX();
                yClicked = e.getY();
                studentClicked = thisTableView.getStudentByCoord(xClicked, yClicked);
                //remove previous student
                if (studentDisplayed){
                    tableViewPanel.remove(sip);
                    tableViewPanel.revalidate();
                    studentDisplayed = false;
                }
                //add new student
                if (studentClicked != null){
                    sip = new StudentInfoPanel(studentClicked);
                    tableViewPanel.add(sip, PAGE_END);
                    tableViewPanel.revalidate();
                    studentDisplayed = true;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        }

        /**
         * StudentInfoPanel.class
         * panel to draw information about a student, when they are clicked
         * @author Mike
         */
        private class StudentInfoPanel extends JPanel {
            private String infoString = "";
            private JLabel infoLabel;

            /**
             * StudentInfoPanel
             * constructor for StudentInfoPanel
             * @param s student to display info about
             */
            StudentInfoPanel(Student s){
                //student info
                infoString += "Name: " + s.getName();
                infoString += " Student #: " + s.getId();

                //accomodations
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
                this.setVisible(true);
            }
            @Override
            public void paintComponent(Graphics g) {}
        }
    }
}