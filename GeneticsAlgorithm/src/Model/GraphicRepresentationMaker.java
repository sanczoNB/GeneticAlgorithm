package Model;

import Genetic.Individual;
import Genetic.Population;
import View.Parametrs.Parameters;
import com.sun.javafx.tk.Toolkit;
import javafx.scene.text.Font;

import java.awt.*;

/**
 * Created by sanczo on 2016-05-04.
 */
public class GraphicRepresentationMaker {

    private Parameters parameters;

    public GraphicRepresentationMaker()
    {
        parameters = Parameters.getInstance();

    }

    public GraphicPopulationRepresentation transform(Population population) {

        GraphicPopulationRepresentation graphicPopulationRepresentation = new GraphicPopulationRepresentation();


        double singleWidthOfDrawingGen = parameters.getGenDiameter() + parameters.getMarginLeft() + parameters.getMarginRight();
        double singleHeightOfDrawingGen = parameters.getGenDiameter() + parameters.getMarginTop() + parameters.getMarginBottom();

        double positionHorizontal;
        double positionVertical;
        Individual individual;


        for (int i = 0; i < population.size(); i++) {

            individual = population.getIndividualAtPosition(i);

            GraphicIndividualRepresentation graphicIndividualRepresentation = new GraphicIndividualRepresentation();

            for (int j = 0; j < Individual.getNumberOfGens(); j++) {
                positionHorizontal = singleWidthOfDrawingGen * j;
                positionVertical = singleHeightOfDrawingGen * i;

                double leftCornerX = positionHorizontal + parameters.getMarginLeft();
                double leftCornerY = positionVertical + parameters.getMarginTop();

                Circle circle = new Circle(leftCornerX, leftCornerY, parameters.getGenDiameter());

                String text = Integer.toString(individual.getGenAtPosition(j));

                Font font = new Font(parameters.getFontSize());

                double width = Toolkit.getToolkit().getFontLoader().computeStringWidth(text, font);
                double height = com.sun.javafx.tk.Toolkit.getToolkit().getFontLoader().getFontMetrics(font).getAscent();

                double leftCornerXForInscription = positionHorizontal + parameters.getMarginLeft() + parameters.getGenDiameter() / 2 - width / 2;
                double leftCornerYForInscription = positionVertical + parameters.getMarginTop() + parameters.getGenDiameter() / 2 + height / 3;

                DrawString drawString = new DrawString(leftCornerXForInscription, leftCornerYForInscription, text);

                graphicIndividualRepresentation.insertGraphicGenRepresentation(new GraphicGenRepresentation(circle, drawString));
            }
            graphicPopulationRepresentation.insertGraphicIndividualRepresentation(graphicIndividualRepresentation);
        }

        return graphicPopulationRepresentation;
    }

    public Size calculateSizeOfCanvas(Population population) {

        double canvasWidth = Individual.getNumberOfGens() * (parameters.getGenDiameter() + parameters.getMarginLeft() + parameters.getMarginRight());

        double canvasHeight = population.size()*(parameters.getGenDiameter() + parameters.getMarginTop() + parameters.getMarginBottom());

        return new Size(canvasWidth, canvasHeight);
    }




}
