import java.awt.Graphics;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class TableView {
    private int width, height, tableRadius, studentRadius;
    private Table table;
    private ArrayList<Student> students;
    TableView(int width, int height, Table table){
        this.width = width;
        this.height = height;
        this.table = table;
        this.tableRadius = height/4;
        this.studentRadius = tableRadius/5;
        this.students = table.getStudents();
    }

    public Student getStudentByCoord(int mouseX, int mouseY){
        for (int i = 0; i < students.size(); i++) {
            int xCoord = (int)(width/2 + tableRadius * Math.cos(i* 2*Math.PI/students.size()));
            int yCoord = (int)(height/2 + tableRadius * Math.sin(i* 2*Math.PI/students.size()));
            if (Point2D.distance(mouseX, mouseY, xCoord, yCoord) <= studentRadius){
                return (students.get(i));
            }
        }
        return null;
    }
    public void draw(Graphics g){
        g.setColor(new Color(139,69,19));
        MyUtil.drawCircle(g,width/2, height/2, tableRadius);
        g.setColor(Color.GREEN);
        for (int i = 0; i < students.size(); i++){
            Student s = students.get(i);
            if (s.getPicture()==null){
                MyUtil.drawCircle(g,(int)(width/2 + tableRadius * Math.cos(i* 2*Math.PI/students.size())), (int)(height/2 + tableRadius * Math.sin(i* 2*Math.PI/students.size())), tableRadius /5);
            } else {
                MyUtil.drawPerson(g,s.getPicture(),(int)(width/2 + tableRadius * Math.cos(i* 2*Math.PI/students.size())), (int)(height/2 + tableRadius * Math.sin(i* 2*Math.PI/students.size())), tableRadius /5);
            }

        }
    }
}
