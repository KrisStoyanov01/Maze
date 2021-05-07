package maze;

/**
 * Handles the class NoExitException which extends InvalidMazeException.
 * Responsible for Exceptions thrown because there are no Characters 'x' present in the Text, used for creating a Maze.
 *	@author	Christyan	Stoyanov
 *	@version 1.1,	6th	May	2021
 */
public class NoExitException extends InvalidMazeException{
    /**
     * Constructor which makes a call to the constructor of InvalidMazeException with a message as a parameter
     * @param message - String which represents the message of the exception
     */
    public NoExitException(String message) {
        super(message);
    }
}
