package maze;

/**
 * Handles the abstract class InvalidMazeException which extends Exception.
 * Supposed to be extended by other classes.
 *	@author	Christyan	Stoyanov
 *	@version 1.1,	6th	May	2021
 */
public abstract class InvalidMazeException extends Exception{
    /**
     * Constructor which makes a call to the constructor of Exception with a message as a parameter
     * @param message - String which represents the message of the exception
     */
    public InvalidMazeException(String message){
        super(message);
    }
}
