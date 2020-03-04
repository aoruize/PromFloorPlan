import javafx.scene.shape.Circle;

import java.awt.*;
import java.util.ArrayList;
class FloorView {
    private int xSize, ySize, width, height, tableRadius;
    public Transformations trans;
    ArrayList<Table> tables;
    FloorView(int width, int height, ArrayList<Table> tables) {
        this.width = width;
        this.height = height;
        this.tables = tables;
        trans = new Transformations(width, height);
        xSize = (int)Math.ceil(Math.pow(tables.size(), 0.56));
        ySize = (int)Math.ceil(Math.pow(tables.size(), 0.44));
        tableRadius = width/(xSize+1)/4;
    }
    public void draw(Graphics g){

        for (int i = 0; i < tables.size(); i++){
            g.setColor(Color.GREEN);
            //Table//
            int xCoord = trans.applyXTranslate((i%xSize + 1)*(width/(xSize+1)));
            int yCoord = trans.applyYTranslate((i/xSize + 1)*(height/(ySize+1)));
            int newTableRadius = trans.applyRadiusChange(tableRadius);
            MyUtil.drawCircle(g,xCoord, yCoord, newTableRadius);
            ////

            //Seats//
            ArrayList<Student> students;
            students = tables.get(i).getStudents();
            for (int j = 0; j < students.size(); j++){
                Student s = students.get(j);
                MyUtil.drawPerson(g, s.getPicture(), (int)(xCoord + newTableRadius * Math.cos(j* 2*Math.PI/students.size())), (int)(yCoord + newTableRadius* Math.sin(j* 2*Math.PI/students.size())), newTableRadius/2);
            }
            ////
        }
    }
}
