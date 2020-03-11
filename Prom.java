import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * Class Prom. Main class for the project. Holds constants for TicketingSystem.
 * @see TicketingSystem
 * @author Daksh & Matthew
 */
public class Prom extends JFrame {
    private TicketingSystem ticketingPanel;
    private ArrayList<Student> students;
    private ArrayList<Table> tables;

    // constants that hold game window size information
    private final int WINDOW_WIDTH = (int) getToolkit().getScreenSize().getWidth();
    private final int WINDOW_HEIGHT = (int) getToolkit().getScreenSize().getHeight();

    public final static int maxTables = 10;
    public final static int tableSize = 10;
    
    public final static String TYPE_OF_EVENT = "Prom";
    public final static String COST_OF_TICKET = "$60.00 CAD";

    /**
     *Constructor for Prom. Adds the ticketingSystem Panel and displays it.
     * @see TicketingSystem
     */
    public Prom() {
        super(TYPE_OF_EVENT);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setResizable(false);
        
        this.students = new ArrayList<Student>();
        this.tables = new ArrayList<Table>();
        //tables.add(new Table());

        this.ticketingPanel = new TicketingSystem(students, tables);

        this.add(ticketingPanel);

        this.requestFocusInWindow();
        this.setVisible(true);
    }

    /**
     * Entry point for the application. Constructs a Prom object.
     * @param args does literally nothing
     */
    public static void main(String[] args){
        new Prom();
    }
}
