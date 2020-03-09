import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
public class FloorView {
    private int xSize, ySize, width, height, tableRadius;
    private Transformations trans;
    private ArrayList<Table> tables;
    FloorView(int width, int height, ArrayList<Table> tablesList) {
        this.width = width;
        this.height = height;
        this.tables = tablesList;
        trans = new Transformations(width, height);
        xSize = (int)Math.ceil(Math.pow(tables.size(), 0.56));
        ySize = (int)Math.ceil(Math.pow(tables.size(), 0.44));
        tableRadius = width/(xSize+1)/4;
    }
    public Transformations getTrans() {
        return trans;
    }

    public Table getTableByCoord(int mouseX, int mouseY){
        for (int i = 0; i < tables.size(); i++) {
            int xCoord = trans.applyXTranslate((i % xSize + 1) * (width / (xSize + 1)));
            int yCoord = trans.applyYTranslate((i / xSize + 1) * (height / (ySize + 1)));
            int newTableRadius = trans.applyRadiusChange(tableRadius);
            if (Point2D.distance(mouseX, mouseY, xCoord, yCoord) <= newTableRadius){
                return (tables.get(i));
            }
        }
        return null;
    }

    public void draw(Graphics g){
        for (int i = 0; i < tables.size(); i++){
            g.setColor(new Color(139,69,19));
            //Table//
            int xCoord = trans.applyXTranslate((i%xSize + 1)*(width/(xSize+1)));
            int yCoord = trans.applyYTranslate((i/xSize + 1)*(height/(ySize+1)));
            int newTableRadius = trans.applyRadiusChange(tableRadius);
            MyUtil.drawCircle(g,xCoord, yCoord, newTableRadius);
            ////

            //Seats//
            g.setColor(Color.GREEN);
            ArrayList<Student> students;
            students = tables.get(i).getStudents();
            for (int j = 0; j < students.size(); j++){
                Student s = students.get(j);
                if (s.getPicture()==null) {
                    MyUtil.drawCircle(g, (int) (xCoord + newTableRadius * Math.cos(j * 2 * Math.PI / students.size())), (int) (yCoord + newTableRadius * Math.sin(j * 2 * Math.PI / students.size())), newTableRadius / 4);
                } else {
                    MyUtil.drawPerson(g, s.getPicture(), (int) (xCoord + newTableRadius * Math.cos(j * 2 * Math.PI / students.size())), (int) (yCoord + newTableRadius * Math.sin(j * 2 * Math.PI / students.size())), newTableRadius / 4);
                }
            }
            ////
        }
    }
}
