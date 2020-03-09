import java.util.ArrayList;

/**
 * Student class required for Prom Project. Used by TicketingSystem
 * @see TicketingSystem
 * @see Student
 * @author Daksh & Matthew
 */
public class Table {
    private int size;
    private ArrayList<Student> students;
    private int x;
    private int y;

    /**
     * Constructor for table.
     * @param size The max number of students a table can have.
     */
    Table(int size){
        this.size = size;
        this.students = new ArrayList<Student>();
    }

    /**
     * Gets the tables max size.
     * @return The max size of the table.
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets the students seated at the table.
     * @return Students seated at the table.
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * Sets the students seated at the table.
     * @param students Students seated at the table.
     */
    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    /**
     * Gets the X coordinate of the table.
     * @return The X coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the X coordinate of the table.
     * @param x The X coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the Y coordinate of the table.
     * @return The Y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the Y coordinate of the table.
     * @param y The Y coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Adds a student to the table.
     * @param student The student that has to be added.
     */
    public void addStudent(Student student){
        students.add(student);
    }

    /**
     * Removes a student from the table.
     * @param student The student that has to be removed.
     */
    public void removeStudent(Student student){
        students.remove(student);
    }

    /**
     * Checks if the table is full or not.
     * @return True if the table is full.
     */
    public boolean isFull(){
        if (this.students.size() >= this.size){
            return true;
        } else {
            return false;
        }
    }
}
