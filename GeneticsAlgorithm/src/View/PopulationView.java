package View;

import Controller.IController;
import Model.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/**
 * Created by sanczo on 2016-05-05.
 */
public class PopulationView extends Application {

    private IController controller;

    private View.Parametrs.Parameters parameters;


    private Label titleLabel;

    private Canvas canvasForGraphicalRepresentationOfPopulation;

    private Button previous;

    private Button next;

    private Scene scene;

    private Group group;


    public PopulationView(){

        parameters = View.Parametrs.Parameters.getInstance();

    }


    public void createViewItems(){

        titleLabel = new Label();

        canvasForGraphicalRepresentationOfPopulation = new Canvas();

        previous = new Button("Previous Population");

        next = new Button("Next Population");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setContent(canvasForGraphicalRepresentationOfPopulation);
        scrollPane.setPrefSize(parameters.getResolutionWidth(), parameters.getResolutionHeight());

        group = new Group();

        group.getChildren().add(titleLabel);

        group.getChildren().add(scrollPane);

        group.getChildren().add(previous);

        group.getChildren().add(next);

        scene = new Scene(group, parameters.getResolutionWidth(), parameters.getResolutionHeight());

    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Genetic Algorithm Simulator");

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public void setSizeForCanvasForGraphicalRepresentationOfPopulation(Size size){

        canvasForGraphicalRepresentationOfPopulation.setWidth(size.getWidth());
        canvasForGraphicalRepresentationOfPopulation.setHeight(size.getHeight());

    }

    public void drawPopulationRepresentation(GraphicPopulationRepresentation population){

        GraphicsContext gc = canvasForGraphicalRepresentationOfPopulation.getGraphicsContext2D();

        for (int i = 0; i < population.size(); i++){
            GraphicIndividualRepresentation individual = population.getGraphicIndividualRepresentationAtPosition(i);
            for (int j = 0; j < individual.gensLength(); j++)
            {
                GraphicGenRepresentation gen = individual.getGraphicGenRepresentationAtPosition(j);

                Circle circle = gen.getCircle();

                DrawString drawString = gen.getDrawString();

                gc.strokeOval(circle.getLeftUpperCornerX(),
                        circle.getLeftUpperCornerY(),
                        circle.getRadius(),
                        circle.getRadius());

                gc.strokeText(drawString.getContent(),
                        drawString.getLeftUpperCornerX(),
                        drawString.getLeftUpperCornerY());

            }

        }

    }

    public void setTitle(int populationNumber){

        titleLabel.setText("Populations nr: " + populationNumber);

    }
}
