package View.Parametrs;

import Genetic.FitnessCalculator.IFitnessCalculator;

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

    public void setUpParameters(){

        genDiameter = 20;
        marginTop = 5;
        marginBottom = 5;
        marginLeft = 5;
        marginRight = 5;
        fontSize = 15;

        resolutionWidth = 1366/4;
        resolutionHeight = 768/2;

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
