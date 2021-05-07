package maze;

/**
 * Handles the class RaggedMazeException which extends InvalidMazeException.
 * Responsible for Exceptions thrown because not all lines in the Text, used for creating a Maze, are of equal length.
 *	@author	Christyan	Stoyanov
 *	@version 1.1,	6th	May	2021
 */
public class RaggedMazeException extends InvalidMazeException {
    /**
     * Constructor which makes a call to the constructor of InvalidMazeException with a message as a parameter
     * @param message - String which represents the message of the exception
     */
    public RaggedMazeException(String message) {
        super(message);
    }
}
