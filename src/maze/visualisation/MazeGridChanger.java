package maze.visualisation;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.Arrays;
import java.util.List;

/**
 *	Handles updating the Grid which is used for displaying the Maze.
 *  Uses JavaFx.
 *	@author	Christyan	Stoyanov
 *	@version 1.1,	7th	May	2021
 */
public class MazeGridChanger {
    private GridPane mazeGrid;
    private String mazeText;

    /**
     * Simple constructor for creating new Objects of Type MazeGridChanger
     * @param mazeGrid GridPane - needed, because it will be updated
     * @param mazeText String - provides the needed information for correctly updating the GridPane
     */
    public MazeGridChanger(GridPane mazeGrid, String mazeText) {
        this.mazeGrid = mazeGrid;
        this.mazeText = mazeText;
    }

    /**
     * Handles changing the GridPane. Sets Alignment, padding, adds a Border.
     * Then for each different Character in the String named mazeText
     * a new Square or a Circle is added to the GridPane called MazeGrid.
     */
    public void change(){
        mazeGrid.setAlignment(Pos.TOP_LEFT);
        mazeGrid.setHgap(0);
        mazeGrid.setVgap(0);
        mazeGrid.setPadding(new Insets(5, 5, 5, 5));
        mazeGrid.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        List<String> mazeLines = Arrays.asList(mazeText.split("\n"));
        for (int i = 0; i < mazeLines.size(); i++) {
            String line = mazeLines.get(i);
            for (int j = 0; j < line.length(); j++) {
                Character tileChar = line.charAt(j);

                if(tileChar == '#') {
                    Rectangle rectangle = new Rectangle();
                    rectangle.setWidth(20);
                    rectangle.setHeight(20);
                    rectangle.setFill(Color.BLACK);
                    mazeGrid.add(rectangle, j, i);
                }
                if(tileChar == 'x') {
                    Rectangle rectangle = new Rectangle();
                    rectangle.setWidth(17);
                    rectangle.setHeight(17);
                    rectangle.setFill(Color.GREEN);
                    mazeGrid.add(rectangle, j, i);
                }
                if(tileChar == 'e') {
                    Rectangle rectangle = new Rectangle();
                    rectangle.setWidth(17);
                    rectangle.setHeight(17);
                    rectangle.setFill(Color.GREEN);
                    mazeGrid.add(rectangle, j, i);
                }
                if(tileChar == '*') {
                    Circle circle = new Circle();
                    circle.setRadius(5);
                    circle.setFill(Color.BLUE);
                    mazeGrid.add(circle, j, i);
                }
                if(tileChar == '-') {
                    Circle circle = new Circle();
                    circle.setRadius(5);
                    circle.setFill(Color.RED);
                    mazeGrid.add(circle, j, i);
                }
            }
        }
    }
}
