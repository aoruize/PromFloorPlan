public class Transformations {
    private int xCentre;
    private int yCentre;
    private int xTransform;
    private int yTransform;
    private double zoomLevel;

    Transformations(int width, int height){
        this.xCentre = width/2;
        this.yCentre = height/2;
        this.xTransform = 0;
        this.yTransform = 0;
        this.zoomLevel = 1;
    }
    public void changeTranslate (int xTrans, int yTrans){
        this.xTransform += xTrans;
        this.yTransform += yTrans;
    }
    public void changeZoom(double zoom){
        zoomLevel *= zoom;
    }
    public int applyXTranslate(int xCoord){
        return (int)(xCentre + zoomLevel*(xCoord-xCentre));
    }
    public int applyYTranslate(int yCoord){
        return (int)(yCentre + zoomLevel*(yCoord-yCentre));
    }
    public int applyRadiusChange(int radius){
        return (int)(radius*zoomLevel);
    }

    public int getxCentre() {
        return xCentre;
    }

    public int getyCentre() {
        return yCentre;
    }

    public int getxTransform() {
        return xTransform;
    }

    public int getyTransform() {
        return yTransform;
    }

    public double getZoomLevel() {
        return zoomLevel;
    }
}