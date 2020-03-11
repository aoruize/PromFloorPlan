import java.awt.Graphics;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
/**
 * TableView
 * Class to handle the Table view of the floor plan
 * @author Mike
 */
public class TableView {
    private int width, height, tableRadius, studentRadius;
    private Table table;
    private ArrayList<Student> students;

    /**
     * TableView
     * Constructor for TableView
     * @param width width of screen
     * @param height height of screen
     * @param table table that is being shown
     */
    TableView(int width, int height, Table table){
        this.width = width;
        this.height = height;
        this.table = table;
        this.tableRadius = height/4;
        this.studentRadius = tableRadius/5;
        this.students = table.getStudents();
    }

    /**
     * getStudentByCoord
     * A function to check if the mouse is over a student
     * @param mouseX x-location of the mouse
     * @param mouseY y-location of the mouse
     * @return the Student that is being moused over, or null if none
     */
    public Student getStudentByCoord(int mouseX, int mouseY){
        for (int i = 0; i < students.size(); i++) {
            //student locations
            int xCoord = (int)(width/2 + tableRadius * Math.cos(i* 2*Math.PI/students.size()));
            int yCoord = (int)(height/2 + tableRadius * Math.sin(i* 2*Math.PI/students.size()));
            //check if in circle
            if (Point2D.distance(mouseX, mouseY, xCoord, yCoord) <= studentRadius){
                return (students.get(i));
            }
        }
        return null;
    }

    /**
     * draw
     * Draws table and all students
     * @param g Graphics object to be painted to
     */
    public void draw(Graphics g){
        g.setColor(new Color(139,69,19));
        //table
        MyUtil.drawCircle(g,width/2, height/2, tableRadius);
        g.setColor(Color.GRAY);
        for (int i = 0; i < students.size(); i++){
            Student s = students.get(i);
            if (s.getPicture()==null){
                //no picture
                MyUtil.drawCircle(g,(int)(width/2 + tableRadius * Math.cos(i* 2*Math.PI/students.size())), (int)(height/2 + tableRadius * Math.sin(i* 2*Math.PI/students.size())), tableRadius /4);
            } else {
                //picture
                MyUtil.drawPerson(g,s.getPicture(),(int)(width/2 + tableRadius * Math.cos(i* 2*Math.PI/students.size())), (int)(height/2 + tableRadius * Math.sin(i* 2*Math.PI/students.size())), tableRadius /2);
            }

        }
    }
}
