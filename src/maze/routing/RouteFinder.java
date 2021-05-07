package maze.routing;

import maze.Maze;
import maze.Tile;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *	Class handling creation and usage of the Route Finder.
 *  Also handles the load and save route functionality.
 *	@author	Christyan	Stoyanov
 *	@version 1.1,	6th	May	2021
 */
public class RouteFinder implements Serializable{
    private Maze maze;
    private Stack<Tile> route;
    private boolean finished = false;
    private List<Tile> usedTiles;

    /**
     * A public constructor for creating new Objects of type RouteFinder using a Maze object as a parameter
     * @param maze Maze object which is assigned to the private field named maze
     */
    public RouteFinder(Maze maze){
        this.maze = maze;
    }

    /**
     * A method which provides us with the Maze object maze
     * @return Maze object which is assigned at the private field named maze
     */
    public Maze getMaze() {
        return this.maze;
    }

    /**
     * A method which returns the Route which is created until now.
     * It creates a List of Tiles and, if the Stack of Tiles route is non-empty, it adds its elements to the List.
     * @return If route is null, it returns just an empty List of Tile.
     * If route not empty, it returns route, transformed to a List of Tiles.
     */
    public List<Tile> getRoute() {
        List<Tile> tileList = new ArrayList<>();
        if(this.route == null){
            return tileList;
        }
        tileList.addAll(this.route);
        return tileList;
    }

    /**
     * A method which provides us with information whether the RouteFinder has find the exit.
     * @return Boolean value of the private field finished.
     */
    public boolean isFinished() {
        return this.finished;
    }

    /**
     * A void method which saves the current RouteFinder object in a File which is located at a given path.
     * @param path String which shows the location where the File should be saved
     * @throws IOException Indicates a problem with either ObjectOutputStream, FileOutputStream or the function writeObject.
     * This may be caused by providing an illegal path.
     */
    public void save(String path) throws IOException {
        new ObjectOutputStream(new FileOutputStream(path)).writeObject(this);
    }

    /**
     * Provides us with the ability to create a Route Finder object from reading a file which has the needed data.
     * It creates a RouteFinder after a call to the readObject function of a ObjectInputStream, with parameter FileInputStream, which is created
     * with a parameter path. Then it is casted to as RouteFinder.
     * @param path String which indicates the location of the path from where it should load
     * @return Returns null and an exception, if there is a problem with reading the object in the File, located at path.
     * Returns a newly created RouteFinder object with data from the File, located at path, if everything is working
     * properly and the data is valid.
     */
    public static RouteFinder load(String path){
        RouteFinder routeFinder = null;
        try {
            routeFinder = (RouteFinder) new ObjectInputStream(new FileInputStream(path)).readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routeFinder;
    }

    /**
     * A method which generates the next possible state of the route Stack.
     * This handles one iteration of a Depth-first search algorithm.
     * If the current tile on the top of the Stack is a Tile of Type Exit, it returns true,
     * indicating that an exit route is find, and doesn't allow any further changes to the current route.
     * If there are still Tiles in the Stack of Tiles route, it either progresses forward or goes back.
     * If there aren't such Tiles, it indicates that an exit route can't be found.
     * @return Boolean value. True if an exit us found or if it is sure that the exit cannot be reached.
     * False in any other case.
     */
    public boolean step() {
        if(this.isFinished()){
            return true;
        }else {
            if (this.route == null && !this.isFinished()) {
                this.route = new Stack<>();
                this.usedTiles = new ArrayList<>();
                this.route.push(maze.getEntrance());
            } else {
                if (this.route.isEmpty()) {
                    System.out.println("No exit");
                    return true;
                }

                Tile tileToBeAdded;
                tileToBeAdded = this.maze.getAdjacentTile(this.route.peek(), Maze.Direction.NORTH);
                if (adjacentIsValid(tileToBeAdded)) {
                    if (tileToBeAdded.getType() == Tile.Type.EXIT) {
                        this.finished = true;
                        return true;
                    }
                } else {

                    tileToBeAdded = this.maze.getAdjacentTile(this.route.peek(), Maze.Direction.EAST);
                    if (adjacentIsValid(tileToBeAdded)) {
                        if (tileToBeAdded.getType() == Tile.Type.EXIT) {
                            this.finished = true;
                            return true;
                        }
                    } else {

                        tileToBeAdded = this.maze.getAdjacentTile(this.route.peek(), Maze.Direction.SOUTH);
                        if (adjacentIsValid(tileToBeAdded)) {
                            if (tileToBeAdded.getType() == Tile.Type.EXIT) {
                                this.finished = true;
                                return true;
                            }
                        } else {

                            tileToBeAdded = this.maze.getAdjacentTile(this.route.peek(), Maze.Direction.WEST);
                            if (adjacentIsValid(tileToBeAdded)) {
                                if (tileToBeAdded.getType() == Tile.Type.EXIT) {
                                    this.finished = true;
                                    return true;
                                }
                            } else {

                                this.usedTiles.add(route.pop());
                            }
                        }
                    }
                }
            }
            return false;
        }
    }

    /**
     * Private method which checks if a movement to the adjacent Tile is valid
     * @param tile Tile for which we want to check if movement to is valid
     * @return Boolean. If several conditions(indicating that movement is illegal) are not met, the return is False.
     * Otherwise the tile is put to the top of the Stack of Tiles and the return value is True.
     */
    private boolean adjacentIsValid(Tile tile){
        if(tile == null || this.usedTiles.contains(tile) || !tile.isNavigable() || this.route.contains(tile)){
            return false;
        }
        this.route.push(tile);
        return true;
    }

    /**
     * Method which transforms the RouteFinder to a String.
     * An empty StringBuilder which is going to hold the final return value is initialized
     * For each Tile on each List of Tiles in maze field, we check if it is present in the route.
     * If it is, it is added to the StringBuilder, it as a "*", to indicate that it is part of the route.
     * Then the same check for being in the List of Used Tiles, however this time it is represented as a "-".
     * If non of this checks is true, it is put as it is.
     * After a new List of Tiles is started, a new line is added to the StringBuilder.
     * @return Returns the created StringBuilder's function toString().
     */
    public String toString(){
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < this.maze.getTiles().size(); i++){
            List<Tile> tileRow = this.maze.getTiles().get(this.maze.getTiles().size() - 1 - i);
            for(int j = 0; j < tileRow.size(); j++){
                Tile currentTile = tileRow.get(j);
                if(this.route == null){
                    output.append(currentTile.toString());
                }else {
                    if (this.route.contains(currentTile)) {
                        output.append("*");
                    } else {
                        if (this.usedTiles.contains(currentTile)) {
                            output.append("-");
                        } else {
                            output.append(currentTile.toString());
                        }
                    }
                }
            }
            output.append("\n");
        }
        return output.toString();
    }
}
