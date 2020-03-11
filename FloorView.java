import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
/**
 * Class to handle the Floor view of the floor plan
 * @author Mike
 */
public class FloorView {
    private int xSize, ySize, width, height, tableRadius, studentRadius;
    private Transformations trans;
    private ArrayList<Table> tables;
    FloorView(int aWidth, int aHeight, ArrayList<Table> tablesList) {
        this.width = aWidth;
        this.height = aHeight;
        this.tables = tablesList;
        this.trans = new Transformations(aWidth, aHeight);
        this.xSize = (int)Math.ceil(Math.pow(tables.size(), 0.56));
        this.ySize = (int)Math.ceil(Math.pow(tables.size(), 0.44));
        this.tableRadius = aWidth/(xSize+1)/4;
    }

    /**
     * getter for Transformations, to allow change to zoom level and translation
     * @return trans variable of FloorView
     */
    public Transformations getTrans() {
        return trans;
    }

    /**
     * A function to check if the mouse is over a table
     * @param mouseX x-location of the mouse
     * @param mouseY y-location of the mouse
     * @return the Table that is being moused over, or null if none
     */
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
    /**
     * Draws all tables and all students
     * @param g Graphics object to be painted to
     */
    public void draw(Graphics g){
        for (int i = 0; i < tables.size(); i++){
            g.setColor(new Color(139,69,19));
            //Table//
            int xCoord = trans.applyXTranslate((i%xSize + 1)*(width/(xSize+1)));
            int yCoord = trans.applyYTranslate((i/xSize + 1)*(height/(ySize+1)));
            int newTableRadius = trans.applyRadiusChange(tableRadius);
            MyUtil.drawCircle(g,xCoord, yCoord, newTableRadius);
            //Seats//
            g.setColor(Color.GRAY);
            ArrayList<Student> students;
            students = tables.get(i).getStudents();
            for (int j = 0; j < students.size(); j++){
                Student s = students.get(j);
                if (s.getPicture()==null) {
                    MyUtil.drawCircle(g, (int) (xCoord + newTableRadius * Math.cos(j * 2 * Math.PI / students.size())), (int) (yCoord + newTableRadius * Math.sin(j * 2 * Math.PI / students.size())), newTableRadius / 4);
                } else {
                    MyUtil.drawPerson(g, s.getPicture(), (int) (xCoord + newTableRadius * Math.cos(j * 2 * Math.PI / students.size())), (int) (yCoord + newTableRadius * Math.sin(j * 2 * Math.PI / students.size())), newTableRadius / 2);
                }
            }
        }
    }
}
