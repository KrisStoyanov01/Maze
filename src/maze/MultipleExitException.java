package maze;

/**
 * Handles the class MultipleExitException which extends InvalidMazeException.
 * Responsible for Exceptions thrown because an multiple Characters 'x' are present in the Text, used for creating a Maze.
 *	@author	Christyan	Stoyanov
 *	@version 1.1,	6th	May	2021
 */
public class MultipleExitException extends InvalidMazeException{
    /**
     * Constructor which makes a call to the constructor of InvalidMazeException with a message as a parameter
     * @param message - String which represents the message of the exception
     */
    public MultipleExitException(String message) {
        super(message);
    }
}
