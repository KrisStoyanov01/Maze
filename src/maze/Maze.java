package maze;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *	Class handling creation and usage of the mazes.
 *	@author	Christyan	Stoyanov
 *	@version	1.1,	6th	May	2021
 */
public class Maze implements Serializable{
    private Tile entrance;
    private Tile exit;
    private List<List<Tile>> tiles = new ArrayList<>();

    /**
     * An Enum with 4 possible String states, handling the neighbour tile in each of the possible 4 directions - up,down,right,left
     */
    public enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    /**
     * An inner class which provides with the ability to set coordinates for each Tile in a given Maze
     */
    public static class Coordinate {
        private int x;
        private int y;

        /**
         * A public constructor for creating new Objects of type Coordinate using 2 parameters
         * @param x - integer, responsible for the index of the column the Tile is in the Maze(column with index 0 is the most lefties)
         * @param y - integer, responsible for the index of the row the Tile is in the Maze(row with index 0 is the most bottom one)
         */
        public Coordinate(int x, int y){
            this.x = x;
            this.y = y;
        }

        /**
         * A public getter which provides us with the index of the column the Tile is in the Maze
         * @return Returns an Integer, equal to the index of the column of the Tile
         */
        public int getX() {
            return this.x;
        }

        /**
         * A public getter which provides us with the index of the row the Tile is in the Maze
         * @return Returns an Integer, equal to the index of the row of the Tile
         */
        public int getY() {
            return this.y;
        }

        /**
         * A method which provides us the x and y coordinates of a given Tile
         * @return Returns an String, created in a specific way by combining the x and the y coordinate
         */
        public String toString(){
            return "(" + this.x + ", " + this.y + ")";
        }
    }

    private Maze() {
    }

    /**
     * Creates a new Maze using a path to a txt containing a specific String.
     * A private function is called which returns a List of Strings
     * using as a parameter a File which is created by the given path.
     * After the List of Strings is extracted from the file, it is being processed bottom to top and based on each char
     * a specific Tile is created in Maze. After this the exit and the entrance are set.
     * @param path Path to a .txt file which contains a specific grid of characters
     * @return Returns the newly created Maze after all of its properties are given a value
     * @throws RaggedMazeException Indicates a problem with the .txt file - not all rows are of an equal size
     * @throws IOException Indicates a problem with the FileReader's ability to read lines of Text
     * @throws InvalidSymbolException Indicates a problem with the .txt file - there is a character different from {# . e x}
     * @throws MultipleEntranceException Indicates a problem with the .txt file - there are two 'e' symbols in it
     * @throws MultipleExitException Indicates a problem with the .txt file - there are two 'x' symbols in it
     * @throws NoEntranceException Indicates a problem with the .txt file - there isn't a 'e' symbol in it
     * @throws NoExitException Indicates a problem with the .txt file - there isn't a 'e' symbol in it
     */
    public static Maze fromTxt(String path) throws RaggedMazeException, IOException, InvalidSymbolException, MultipleEntranceException, MultipleExitException, NoEntranceException, NoExitException  {
        File file = new File(path);

        Maze createdMaze = new Maze();
        List<String> lines = getMazeTextLines(file);

        for (int i = 0; i < lines.size(); i++) {
            int entranceIndex = -1;
            int exitIndex = -1;

            int index = lines.size() - 1 - i;
            String line = lines.get(index);
            List<Tile> tilesInRow = new ArrayList<>();
            for (int j = 0; j < line.toCharArray().length; j++) {
                Character c = line.charAt(j);
                Tile tile = Tile.fromChar(c);
                if (tile.getType() == Tile.Type.ENTRANCE) {
                    entranceIndex = j;
                }
                if (tile.getType() == Tile.Type.EXIT) {
                    exitIndex = j;
                }
                tilesInRow.add(tile);
            }

            createdMaze.tiles.add(tilesInRow);
            if (entranceIndex != -1) {
                createdMaze.setEntrance(tilesInRow.get(entranceIndex));
            }
            if (exitIndex != -1) {
                createdMaze.setExit(tilesInRow.get(exitIndex));
            }
        }
        if (createdMaze.getEntrance() == null) {
            throw new NoEntranceException("ERROR: Maze doesn't have an entrance!");
        } else {
            if (createdMaze.getExit() == null) {
                throw new NoExitException("ERROR: Maze doesn't have an exit!");
            } else {
                return createdMaze;
            }
        }
    }

    private static List<String> getMazeTextLines(File file) throws IOException, RaggedMazeException {
        BufferedReader br = null;
        br = new BufferedReader(new FileReader(file));
        String st;
        Integer symbolsOnRowCount = -1;
        List<String> lines = new ArrayList<>();
        while ((st = br.readLine()) != null) {
            if(symbolsOnRowCount == -1){
                symbolsOnRowCount = st.length();
            }else{
                if(symbolsOnRowCount != st.length()){
                    throw new RaggedMazeException("ERROR: Maze is ragged!");
                }
            }
            lines.add(st);
        }
        return lines;
    }

    /**
     * A method which returns the adjacent tile in a given Direction if it exists, or returns null
     * We extract x and y coordinates of the Tile using getTileLocation
     * Depending on the Direction a call to getTileAtLocation with parameters which slightly differ is made
     * @param tile The tile for whose neighbours we are interested in
     * @param direction An Enum - the direction of adjacent tile - can be either NORTH, SOUTH, EAST or WEST
     * @return Returns the object that is given by getTileAtLocation. The returned object may be null.
     */
    public Tile getAdjacentTile(Tile tile, Direction direction){
        //todo:: WARNING: Slight chance to have to replace + and -
        //todo:: WARNING: Big chance that this will produce a lot of errors for trying to move to invalid tiles
        int x = getTileLocation(tile).x;
        int y = getTileLocation(tile).y;

        switch (direction){
            case NORTH:
                return this.getTileAtLocation(new Coordinate(x, y + 1));
            case SOUTH:
                return this.getTileAtLocation(new Coordinate(x, y - 1));
            case EAST :
                return this.getTileAtLocation(new Coordinate(x + 1, y));
            case WEST :
                return this.getTileAtLocation(new Coordinate(x - 1, y));
        }
        return this.getTileAtLocation(new Coordinate(x, y));
    }

    /**
     * Provides us with the entrance of the Maze
     * @return Tile object, called entrance, serving as the entrance of the Maze
     */
    public Tile getEntrance() {
        return this.entrance;
    }

    /**
     * Provides us with the exit of the Maze
     * @return Tile object, called exit, serving as the exit of the Maze
     */
    public Tile getExit() {
        return this.exit;
    }

    /**
     * Provides us with the Tile which is at specific coordinates in the Maze
     * We get the x and y coordinates after processing the coordinate parameter
     * Then we check if both of them are invalid - less than 0 or bigger than the corresponding side of the Maze
     * If they are invalid we return null, else we get the Tile which is at index x in the List, which is at index y in tiles
     * @param coordinate Coordinate of the needed tile
     * @return Returns null if the coordinates are invalid, or the needed Tile object if they are valid
     */
    public Tile getTileAtLocation(Coordinate coordinate){
        int x = coordinate.getX();
        int y = coordinate.getY();
        if(x < 0 || y < 0 || x >= this.tiles.get(0).size() || y >= this.tiles.size()) {
            return null;
            //todo:: add an Exception throw
        }
        return this.tiles.get(coordinate.y).get(coordinate.x);
    }

    /**
     * Provides us with the Coordinate class which has values equal to the indexes of a given Tile in the Maze
     * We create two integers x and y which should keep the value of the indexes.
     * We initialize them as -1 and -1 - because we are sure that there no Tiles have these coordinates
     * For each Tile in each List of Strings in Maze, we check if the tile is equal to the provided tile,
     * and if true we set x and y to be equal to the indexes of the Tile and of the String.
     * If there isn't a tile which is equal, an exception would be thrown
     * @param tile The Tile for which we are going to search the Maze for
     * @return Returns a newly created Coordinate object with parameters equal to the x and y coordinates of the Tile
     * (or -1 and -1) if the tile is not found
     */
    public Coordinate getTileLocation(Tile tile){
        int x = -1, y = -1;
        for (List<Tile> tileList : tiles) {
            for (Tile tile1 : tileList) {
                if (tile == tile1) {
                    x = tileList.indexOf(tile1);
                    y = this.getTiles().indexOf(tileList);
                }
            }
        }

        return new Coordinate(x, y);
    }

    /**
     * Provides us with the List of Lists of type Tiles, which are all of the Tiles in the Maze
     * @return List of Lists of Tiles tiles
     */
    public List<List<Tile>> getTiles() {
        return this.tiles;
    }

    private void setEntrance(Tile entrance) throws MultipleEntranceException {
        if(this.entrance != null){
            throw new MultipleEntranceException("Maze has multiple entrances!");
        }
        Boolean isInMaze = false;
        for (List<Tile> tileRow : this.tiles) {
            if(tileRow.contains(entrance)){
                isInMaze = true;
            }
        }
        if(isInMaze){
            this.entrance = entrance;
        }else {
            throw new IllegalArgumentException("Trying to set an entrance which is not in maze");
        }
    }

    private void setExit(Tile exit) throws MultipleExitException {
        if(this.exit != null){
            throw new MultipleExitException("Maze has multiple exits!");
        }
        Boolean isInMaze = false;
        for (List<Tile> tileRow : this.tiles) {
            if(tileRow.contains(exit)){
                isInMaze = true;
            }
        }
        if(isInMaze){
            this.exit = exit;
        }else {
            throw new IllegalArgumentException("Trying to set an exit which is not in maze");
        }
    }

    /**
     * Trasforms the Maze to a String.
     * For each List of Tiles, starting bottom to top, we process which individual Tile and add it to a StringBuilder,
     * depending on its Type. Then, before moving to the next List of Tiles we add a new line to the String Builder.
     * We use a StringBuilder because it is much faster than the ordinary String.
     * @return Returns the toString method of the StringBuilder.
     */
    public String toString(){
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < this.tiles.size(); i++) {
            List<Tile> tileInRow = this.tiles.get(this.tiles.size() - i - 1);
            for (Tile tile : tileInRow) {
                output.append(tile.toString());
            }
            output.append("\n");
        }
        return output.toString();
    }
}
