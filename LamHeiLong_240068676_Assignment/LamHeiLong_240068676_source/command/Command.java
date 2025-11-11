import java.util.Scanner;

/**
 * Command interface - part of the Command pattern
 * All commands (like add musician, delete musician, etc.) implement this interface
 * 
 * This pattern lets us:
 * - Treat all operations the same way
 * - Support undo/redo by storing command history
 * - Separate what we do from how we do it
 * - Commands handle their own user input (Open-Closed Principle)
 */
public interface Command
{
	void readInput(Scanner scanner);  // Read user input for this command
	boolean execute();                // Do the operation
	boolean undo();                   // Reverse the operation
	String getDescription();          // Get a description for display
}
