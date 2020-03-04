import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MyUtil {
    public static void drawCircle(Graphics g, int xCentre, int yCentre, int radius){
        g.fillOval(xCentre-radius, yCentre-radius, radius*2, radius*2);
    }
    public static void drawPerson(Graphics g, BufferedImage b, int xCentre, int yCentre, int sidelength){
        g.drawImage(b, xCentre-(sidelength/2), yCentre-(sidelength/2), sidelength, sidelength, null);
    }
}

