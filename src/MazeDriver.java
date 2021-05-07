import maze.*;
import maze.routing.RouteFinder;

import java.io.IOException;

/**
 *	Class handling the creation and usage of the Maze Driver - used for manual testing purposes and debugging.
 *	@author	Christyan	Stoyanov
 *	@version 1.1,	6th	May	2021
 */
public class MazeDriver {
    /**
     * Used for manual testing and debugging.
     * Create a Maze using the Maze.fromTxt() function.
     * Create a RouteFinder using the RouteFinder constructor and the Maze as a parameter.
     * Execute the step() method several times and observe the results.
     * Execute the load and save functionality and observe the results.
     * @throws RaggedMazeException Indicates a problem with the .txt file - not all rows are of an equal size
     * @throws IOException Indicates a problem with the FileReader's ability to read lines of Text
     * @throws InvalidSymbolException Indicates a problem with the .txt file - there is a character different from {# . e x}
     * @throws MultipleEntranceException Indicates a problem with the .txt file - there are two 'e' symbols in it
     * @throws MultipleExitException Indicates a problem with the .txt file - there are two 'x' symbols in it
     * @throws NoEntranceException Indicates a problem with the .txt file - there isn't a 'e' symbol in it
     * @throws NoExitException Indicates a problem with the .txt file - there isn't a 'e' symbol in it
     */
    public static void main(String args[]) throws RaggedMazeException, IOException, InvalidSymbolException, MultipleEntranceException, MultipleExitException, NoEntranceException, NoExitException {
        Maze maze = Maze.fromTxt("I:\\Maze\\resources\\mazes\\maze1.txt");
        if(maze == null){
            System.out.println("Maze is invalid!");
        }else {
            System.out.println(maze.toString());
            RouteFinder routeFinder = new RouteFinder(maze);

            while(!routeFinder.step()){
                System.out.println(routeFinder.toString());
            }
            routeFinder.save("I:\\Maze\\resources\\mazes\\solved.txt");

            routeFinder = RouteFinder.load("I:\\Maze\\resources\\mazes\\solved.txt");

            System.out.println(routeFinder.toString());
            for (Tile tile : routeFinder.getRoute()) {
                System.out.println(routeFinder.getMaze().getTileLocation(tile));
            }
        }
    }
}
