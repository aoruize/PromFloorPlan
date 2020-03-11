/**
 * Class to handle zooming, panning, etc. on floor view
 * @author Mike
 */
class Transformations {
    private int xCentre;
    private int yCentre;
    private int xTransform;
    private int yTransform;
    private double zoomLevel;

    /**
     * Constructor for Transformations class
     * @param width width of panel
     * @param height height of panel
     */
    Transformations(int width, int height){
        this.xCentre = width/2;
        this.yCentre = height/2;
        this.xTransform = 0;
        this.yTransform = 0;
        this.zoomLevel = 1;
    }

    /**
     * Changes centre of view, for panning
     * @param xTrans change in X
     * @param yTrans change in Y
     */
    public void changeTranslate (int xTrans, int yTrans){
        xTransform += xTrans/zoomLevel;
        yTransform += yTrans/zoomLevel;
    }

    /**
     * Changes zoom, for zooming (zooms on centre)
     * @param zoom zoom level change
     */
    public void changeZoom(double zoom){
        zoomLevel *= zoom;
    }

    /**
     * Changes zoom, for zooming on non-centred location
     * @param zoom zoom level change
     * @param xLoc x-location of place to be zoomed in on
     * @param yLoc y-location of place to be zoomed in on
     */
    public void changeZoom(double zoom, int xLoc, int yLoc){
        zoomLevel *= zoom;
        xCentre = (int)(xCentre + (xLoc-xCentre)/zoom);
        yCentre = (int)(yCentre + (yLoc-yCentre)/zoom);
    }
    /**
     * Returns the x-coordinate after all transformations are applied
     * @param xCoord the x-coord before transformations
     * @return the x-coord after transformations
     */
    public int applyXTranslate(int xCoord){
        return (int)(xCentre + zoomLevel*(xCoord-xCentre) + xTransform*zoomLevel);
    }
    /**
     * Returns the y-coordinate after all transformations are applied
     * @param yCoord the y-coord before transformations
     * @return the y-coord after transformations
     */
    public int applyYTranslate(int yCoord){
        return (int)(yCentre + zoomLevel*(yCoord-yCentre) + yTransform*zoomLevel);
    }

    /**
     * the radius after zoom has been applied
     * @param radius original radius
     * @return radius after zoom
     */
    public int applyRadiusChange(int radius){
        return (int)(radius*zoomLevel);
    }
}