package View.Parametrs;

/**
 * Created by sanczo on 2016-05-04.
 */
public class Parameters {

    private double genDiameter;

    private double marginTop;

    private double marginBottom;

    private double marginLeft;

    private double marginRight;

    private int fontSize;

    private double resolutionWidth;

    private double resolutionHeight;

    private static Parameters instance;

    private Parameters()
    {

    }

    public static Parameters getInstance ()
    {
        if (instance == null)
            instance = new Parameters();
        return instance;
    }

    private void setUpParameters(){

        genDiameter = 75;
        marginTop = 20;
        marginBottom = 20;
        marginLeft = 5;
        marginRight = 5;
        fontSize = 30;

        resolutionWidth = 1366;
        resolutionHeight = 768;

    }


    public double getGenDiameter() {
        return genDiameter;
    }

    public double getMarginTop() {
        return marginTop;
    }

    public double getMarginBottom() {
        return marginBottom;
    }

    public double getMarginLeft() {
        return marginLeft;
    }

    public double getMarginRight() {
        return marginRight;
    }

    public int getFontSize() {
        return fontSize;
    }

    public double getResolutionWidth() {
        return resolutionWidth;
    }

    public double getResolutionHeight() {
        return resolutionHeight;
    }
}
