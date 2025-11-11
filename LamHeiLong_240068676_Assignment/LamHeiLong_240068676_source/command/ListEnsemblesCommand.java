import java.util.Map;
import java.util.Scanner;

/**
 * ListEnsemblesCommand - displays all ensembles in the system
 * This is a non-undoable command (doesn't go in history)
 */
public class ListEnsemblesCommand implements Command
{
	public ListEnsemblesCommand()
	{
	}
	
	public void readInput(Scanner scanner)
	{
		// No input needed
	}
	
	public boolean execute()
	{
		Map<String, Ensemble> ensembles = MEMS.getEnsembles();
		
		if (ensembles.isEmpty())
		{
			System.out.println("No ensembles found.");
		}
		else
		{
			for (Ensemble e : ensembles.values())
			{
				e.showEnsemble();
				System.out.println();
			}
		}
		return true;
	}
	
	public boolean undo()
	{
		// Non-undoable command
		return false;
	}
	
	public String getDescription()
	{
		return "List all ensembles";
	}
}
