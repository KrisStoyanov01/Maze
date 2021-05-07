package maze;

/**
 * Handles the class NoEntranceException which extends InvalidMazeException.
 * Responsible for Exceptions thrown because there are no Characters 'e' present in the Text, used for creating a Maze.
 *	@author	Christyan	Stoyanov
 *	@version 1.1,	6th	May	2021
 */
public class NoEntranceException extends InvalidMazeException{
    /**
     * Constructor which makes a call to the constructor of InvalidMazeException with a message as a parameter
     * @param message - String which represents the message of the exception
     */
    public NoEntranceException(String message) {
        super(message);
    }
}
