import java.util.List;
import java.util.Scanner;

/**
 * ListHistoryCommand - displays undo and redo command history
 * This is a non-undoable command (view-only, doesn't modify state)
 */
public class ListHistoryCommand implements Command
{
	public ListHistoryCommand()
	{
	}
	
	public void readInput(Scanner scanner)
	{
		// No input needed for this command
	}
	
	public boolean execute()
	{
		HistoryManager historyManager = MEMS.getHistoryManager();
		
		// Display undo list
		System.out.println("Undo List");
		List<String> undoList = historyManager.getUndoList();
		if (undoList.isEmpty())
		{
			System.out.println("-- End of undo list --");
		}
		else
		{
			for (String description : undoList)
			{
				System.out.println(description);
			}
			System.out.println("-- End of undo list --");
		}
		
		System.out.println();
		
		// Display redo list
		System.out.println("Redo List");
		List<String> redoList = historyManager.getRedoList();
		if (redoList.isEmpty())
		{
			System.out.println("-- End of redo list --");
		}
		else
		{
			for (String description : redoList)
			{
				System.out.println(description);
			}
			System.out.println("-- End of redo list --");
		}
		
		return true;
	}
	
	public boolean undo()
	{
		// Non-undoable command (view-only)
		return false;
	}
	
	public String getDescription()
	{
		return "List undo/redo history";
	}
}
