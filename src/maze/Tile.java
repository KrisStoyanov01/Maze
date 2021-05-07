package maze;

import java.io.Serializable;

/**
 *	Class handling creation and usage of the tiles.
 *	@author	Christyan	Stoyanov
 *	@version 1.1,	6th	May	2021
 */
public class Tile implements Serializable {
    /**
     * Inner Enum which handles different possible types of a Tile.
     * It can and has to be either a CORRIDOR, an ENTRANCE, an EXIT or a WALL.
     */
    public enum Type {
        CORRIDOR,
        ENTRANCE,
        EXIT,
        WALL
    }

    private Type type;

    private Tile(Type type) {
        this.type = type;
    }

    /**
     * Sets the correct Type of the Tile depending on the parameter input.
     * 'e' is for ENTRANCE
     * '.' is for CORRIDOR
     * '#' is for WALL
     * 'X' is for EXIT
     * If a different char is found an Exception should be thrown because that means that we are trying to created a Maze from invalid Text
     *
     * @param input A Character which is later examined to check if it equals a specific pattern
     * @return Returns a newly created Tile. It's Type is determined by the parameter
     * @throws InvalidSymbolException Indicates a problem with the character - the character is not of the 4 valid ones
     */
    protected static Tile fromChar(char input) throws InvalidSymbolException {
        switch (input) {
            case 'e':
                return new Tile(Type.ENTRANCE);
            case '.':
                return new Tile(Type.CORRIDOR);
            case '#':
                return new Tile(Type.WALL);
            case 'x':
                return new Tile(Type.EXIT);
            default:
                throw new InvalidSymbolException("Maze has an invalid symbol");
        }
    }

    /**
     * Provides us with the Type of the Tile
     *
     * @return Type - an ENUM
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Provides us with information whether the Tile is navigable.
     * All Tiles except the ones with Type WALL are navigable
     *
     * @return Boolean - True value of type is ENTRANCE, CORRIDOR or EXIT; False if type is WALL.
     * Default is present just to be sure in case something goes wrong
     */
    public boolean isNavigable() {
        switch (this.type) {
            case ENTRANCE:
            case CORRIDOR:
            case EXIT:
                return true;
            case WALL:
            default:
                return false;
        }
    }

    /**
     * Provides us with the String representation of the Tile depending on its Type
     *
     * @return Returns "e" if Type is ENTRANCE, "x" if Type is EXIT, "#" if Type is WALL, "." if Type is WALL,
     * or "~" if Type is something else, which will throw an exception later.
     */
    public String toString() {
        switch (this.type) {
            case ENTRANCE:
                return "e";
            case EXIT:
                return "x";
            case WALL:
                return "#";
            case CORRIDOR:
                return ".";
            default:
                return "~";
        }
    }
}
