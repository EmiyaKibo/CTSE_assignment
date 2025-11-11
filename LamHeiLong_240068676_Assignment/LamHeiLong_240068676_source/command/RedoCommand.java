import java.util.Scanner;

/**
 * RedoCommand - wrapper command that redoes the last undone command
 * Part of the Command pattern implementation for redo functionality
 * 
 * Delegates the actual work to HistoryManager
 */
public class RedoCommand implements Command
{
	public RedoCommand()
	{
	}
	
	public void readInput(Scanner scanner)
	{
		// No input needed for redo
	}
	
	public boolean execute()
	{
		HistoryManager historyManager = MEMS.getHistoryManager();
		
		// Save current ensemble before redo
		String previousEnsembleId = MEMS.getCurrentEnsembleId();
		
		String description = historyManager.redo();
		
		if (description != null)
		{
			System.out.println("Command (" + description + ") is redone.");
			
			// Check if ensemble context changed during redo
			String newEnsembleId = MEMS.getCurrentEnsembleId();
			if (previousEnsembleId != null && !previousEnsembleId.equals(newEnsembleId))
			{
				if (newEnsembleId != null)
				{
					Ensemble newEnsemble = MEMS.getEnsembles().get(newEnsembleId);
					if (newEnsemble != null)
					{
						System.out.println("The current ensemble is changed to " + newEnsembleId + " " + newEnsemble.getName() + ".");
					}
				}
			}
			return true;
		}
		else
		{
			System.out.println("Nothing to redo.");
			return false;
		}
	}
	
	public boolean undo()
	{
		// Cannot undo a redo command itself
		return false;
	}
	
	public String getDescription()
	{
		return "Redo";
	}
}
