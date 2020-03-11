import java.awt.Graphics;
import java.awt.image.BufferedImage;
/**
 * A few helper methods to draw circles and avatars from their centres rather than their corners
 * @author Mike
 */
public class MyUtil {
    /**
     * Draws a circle given its centre and radius
     * @param g Graphics object to be drawn to
     * @param xCentre x-coords of centre of circle
     * @param yCentre y-coords of centre of circle
     * @param radius radius of circle
     */
    public static void drawCircle(Graphics g, int xCentre, int yCentre, int radius){
        g.fillOval(xCentre-radius, yCentre-radius, radius*2, radius*2);
    }
    /** Draws a image given its centre and side length
     * @param g Graphics object to be drawn to
     * @param xCentre x-coords of centre of image
     * @param yCentre y-coords of centre of image
     * @param sidelength length of one side of image
     */
    public static void drawPerson(Graphics g, BufferedImage b, int xCentre, int yCentre, int sidelength){
        g.drawImage(b, xCentre-(sidelength/2), yCentre-(sidelength/2), sidelength, sidelength, null);
    }
}

