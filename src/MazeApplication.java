import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.*;
import maze.InvalidSymbolException;
import maze.Maze;
import maze.routing.RouteFinder;
import java.io.File;

import maze.visualisation.MazeGridChanger;

/**
 *	Class handling creation of the User IO.
 *  Uses JavaFx.
 *	@author	Christyan	Stoyanov
 *	@version 1.1,	6th	May	2021
 */
public class MazeApplication extends Application {

    private RouteFinder routeFinder;

    /**
     * Basic JavaFX main class.
     * Launches the loaded args.
     * @param args String[] that has to be launched
     */
    public static void main(String args[]) {
        launch(args);
    }

    /**
     * Creates a VerticalBox, attaches several buttons to it, as well as a GridPane.
     * The GridPane represents the Visualisation of the Maze.
     * Makes calls to a private void function called processMazeVisual() which makes the images that are displayed.
     * Buttons handle loading mazes, saving and loading routes, as well as the step function of the RouteFinder
     * @param stage The Stage object that is needed for visualising the VBox and the Grid
     */
    @Override
    public void start(Stage stage) {
        VBox pane = new VBox(0);
        GridPane mazeGrid = new GridPane();
        pane.setAlignment(Pos.TOP_LEFT);

        //--------------------------------------------------------------------------------------------------------------------------
        Button bLoadMaze = new Button("Select File");
        bLoadMaze.setOnAction(new EventHandler<ActionEvent>(){
            /**
             * Button handles the choosing of a path for a Maze that has to be visualised, by using a FileChooser.
             * Initializes the RouteFinder with using a Maze generated from the selected path
             * Clears the grid before displaying the newly selected Maze.
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    final FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Select Maze File");
                    File file = fileChooser.showOpenDialog(stage);
                    routeFinder = new RouteFinder(Maze.fromTxt(file.getPath()));
                    mazeGrid.getChildren().clear();
                    processMazeVisual(mazeGrid, routeFinder.getMaze().toString());
                } catch (Exception e) {
                    System.out.println("Error: Could not open ");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR: PATH IS INVALID");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
        });
        //--------------------------------------------------------------------------------------------------------------------------

        Button bSaveRoute = new Button("Save Route");
        bSaveRoute.setOnAction(new EventHandler<ActionEvent>(){
            /**
             * Button asks the User to choose a Path where the current state of the RouteFinder to be saved.
             * After choosing a legal file, it saves the RouteFinder object to the path
             */
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("./"));
                fileChooser.setTitle("Save Route");
                File file = fileChooser.showOpenDialog(stage);
                if(!file.getAbsolutePath().equals("")){
                    try{
                        routeFinder.save(file.getAbsolutePath());

                    }
                    catch (Exception e){
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR: PATH IS INVALID");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            }
        });

        //--------------------------------------------------------------------------------------------------------------------------

        Button bLoadRoute = new Button("Load Route");
        bLoadRoute.setOnAction(new EventHandler<ActionEvent>(){
            /**
             * Button handles the choosing of a path for a Route that has to be visualised, by using a FileChooser.
             * Initializes the RouteFinder with using a RouteFinder object
             * generated from the selected path by using the function RouteFinder.load(path)
             * Clears the grid before displaying the newly selected Maze.
             */
            @Override
            public void handle(final ActionEvent AE){
                final FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("./"));
                fileChooser.setTitle("Save Route Path");
                File file = fileChooser.showOpenDialog(stage);
                if(!file.getAbsolutePath().equals("")){
                    try{
                        mazeGrid.getChildren().clear();
                        routeFinder = RouteFinder.load(file.getAbsolutePath());
                        Maze maze = routeFinder.getMaze();
                        processMazeVisual(mazeGrid, routeFinder.toString());
                    }
                    catch (Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR: PATH IS INVALID");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
            }
        });

        //--------------------------------------------------------------------------------------------------------------------------


        /**
         * Upon clicking executes the step() function of the RouteFinder.
         * Then the currentState of the RouteFinder is visualised.
         */
        Button bStep = new Button("Step");
        bStep.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(final ActionEvent AE){
                if(!routeFinder.isFinished()){
                    routeFinder.step();
                    try {
                        processMazeVisual(mazeGrid, routeFinder.toString());
                    } catch (InvalidSymbolException e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println("END");
                }
            }
        });
        //-------------------------------------------------------------------------------------------------------

        pane.getChildren().addAll(bLoadMaze, bLoadRoute, bSaveRoute, mazeGrid, bStep);


        Scene scene = new Scene(pane, 600, 400, Color.WHITE);
        stage.setScene(scene);
        stage.show();
    }

    private void processMazeVisual(GridPane mazeGrid, String mazeText) throws InvalidSymbolException {
        MazeGridChanger mazeGridChanger = new MazeGridChanger(mazeGrid, mazeText);
        mazeGridChanger.change();
    }
}
