import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;

public class TableView {
    private int width, height;
    private Table table;
    TableView(int width, int height, Table table){
        this.width = width;
        this.height = height;
        this.table = table;
    }
    public void draw(Graphics g){
        g.setColor(Color.BLACK);
        MyUtil.drawCircle(g,width/2, height/2, 50);
        ArrayList<Student> students;
        students = table.getStudents();
        g.setColor(Color.GREEN);
        for (int i = 0; i < students.size(); i++){
            MyUtil.drawCircle(g,(int)(width/2 + 50 * Math.cos(i* 2*Math.PI/students.size())), (int)(height/2 + 50* Math.sin(i* 2*Math.PI/students.size())), 10);
        }
    }
}
