package View;

import Controller.Controller;
import Controller.IController;
import Exceptions.FlatPopulationException;
import Genetic.AlgorithmParameters;
import Genetic.Individual;
import Helpers.ProgramDependencies;
import Model.*;
import MyGraph.GraphMaker;
import MyGraph.IGraphService;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * Created by sanczo on 2016-05-05.
 */
public class PopulationView extends Application implements EventHandler<ActionEvent> {

    private IController controller;

    private View.Parametrs.Parameters parameters;


    private Label titleLabel;

    private Canvas canvasForGraphicalRepresentationOfPopulation;

    private Button previous;

    private Button next;

    private Scene scene;

    private Group group;

    private static AlgorithmParameters algorithmParameters;

    public static void main(String[] args) throws FlatPopulationException {


        String fileName = "GEOM4";

        GraphMaker factory = new GraphMaker(fileName + ".col");

        IGraphService graph =  factory.getGraph();

        algorithmParameters = AlgorithmParameters.getInstance();

        View.Parametrs.Parameters.getInstance().setUpParameters();

        algorithmParameters.setGraphService(graph);

        algorithmParameters.setMaxGenValue(Individual.getNumberOfGens() - 1);

        ProgramDependencies.setUpDependencies();

        launch();



    }


    public PopulationView() {
        super();
        parameters = View.Parametrs.Parameters.getInstance();
    }


    public void createViewItems(){

        titleLabel = new Label("");

        canvasForGraphicalRepresentationOfPopulation = new Canvas();

        previous = new Button("Previous Population");

        next = new Button("Next Population");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setContent(canvasForGraphicalRepresentationOfPopulation);
        scrollPane.setPrefSize(parameters.getResolutionWidth(), parameters.getResolutionHeight());

        BorderPane border = new BorderPane();

        border.setCenter(canvasForGraphicalRepresentationOfPopulation);

        border.setBottom(addHBox());

        border.setTop(titleLabel);

        group= new Group();

        group.getChildren().add(titleLabel);

        group.getChildren().add(scrollPane);





        scene = new Scene(border, parameters.getResolutionWidth(), parameters.getResolutionHeight());

    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Genetic Algorithm Simulator");

        controller = new Controller(this);

        controller.callForZeroGeneration();

        primaryStage.setScene(scene);

        primaryStage.show();


    }

    public void setSizeForCanvasForGraphicalRepresentationOfPopulation(Size size){

        canvasForGraphicalRepresentationOfPopulation.setWidth(size.getWidth());
        canvasForGraphicalRepresentationOfPopulation.setHeight(size.getHeight());

    }

    public void drawPopulationRepresentation(GraphicPopulationRepresentation population){

        GraphicsContext gc = canvasForGraphicalRepresentationOfPopulation.getGraphicsContext2D();

        gc.clearRect(0,0,canvasForGraphicalRepresentationOfPopulation.getWidth(), canvasForGraphicalRepresentationOfPopulation.getHeight());

        gc.setFont(new Font(parameters.getFontSize()));

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

    private HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        Button previous = new Button("Previous");
        previous.setPrefSize(100, 20);

        next = new Button("Next populutaion");
        next.setOnAction(this);
        next.setPrefSize(100, 20);
        hbox.getChildren().addAll(previous, next);

        return hbox;
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == next){
            controller.callForNextGeneration();
        }
    }
}
