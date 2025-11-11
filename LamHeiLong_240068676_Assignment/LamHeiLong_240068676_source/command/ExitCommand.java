import java.util.Scanner;

/**
 * ExitCommand - exits the program
 * This is a non-undoable command
 */
public class ExitCommand implements Command
{
	public ExitCommand()
	{
	}
	
	public void readInput(Scanner scanner)
	{
		// No input needed for exit
	}
	
	public boolean execute()
	{
		System.out.println("Goodbye!");
		System.exit(0);
		return true;
	}
	
	public boolean undo()
	{
		return false;  // Cannot undo exit
	}
	
	public String getDescription()
	{
		return "Exit the program";
	}
}
