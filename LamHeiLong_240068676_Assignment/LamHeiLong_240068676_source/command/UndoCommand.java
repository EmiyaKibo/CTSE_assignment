import java.util.Scanner;

/**
 * UndoCommand - wrapper command that undoes the last undoable command
 * Part of the Command pattern implementation for undo functionality
 * 
 * Delegates the actual work to HistoryManager
 */
public class UndoCommand implements Command
{
	public UndoCommand()
	{
	}
	
	public void readInput(Scanner scanner)
	{
		// No input needed for undo
	}
	
	public boolean execute()
	{
		HistoryManager historyManager = MEMS.getHistoryManager();
		
		// Save current ensemble before undo
		String previousEnsembleId = MEMS.getCurrentEnsembleId();
		
		String description = historyManager.undo();
		
		if (description != null)
		{
			System.out.println("Command (" + description + ") is undone.");
			
			// Check if ensemble context changed during undo
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
			System.out.println("Nothing to undo.");
			return false;
		}
	}
	
	public boolean undo()
	{
		// Cannot undo an undo command itself
		return false;
	}
	
	public String getDescription()
	{
		return "Undo";
	}
}
