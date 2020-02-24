import java.awt.Graphics;
public class MyUtil {
    public static void drawCircle(Graphics g, int xCentre, int yCentre, int radius){
        g.fillOval(xCentre-radius, yCentre-radius, radius*2, radius*2);
    }
}
