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
	public void readInput(Scanner scanner);  // Read user input for this command
	public boolean execute();                // Do the operation
	public boolean undo();                   // Reverse the operation
	public String getDescription();          // Get a description for display
}
