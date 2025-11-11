import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * HistoryManager - manages undo/redo functionality using the Command pattern
 * Uses two lists to keep track of commands (LIFO behavior):
 * - undoList: commands that have been executed (can be undone)
 * - redoList: commands that have been undone (can be redone)
 * Using LinkedList for efficient add/remove at the end
 */
public class HistoryManager
{
	private LinkedList<Command> undoList;  // List of commands that can be undone
	private LinkedList<Command> redoList;  // List of commands that can be redone
	
	// Constructor - creates empty lists
	public HistoryManager()
	{
		this.undoList = new LinkedList<>();
		this.redoList = new LinkedList<>();
	}
	
	// Execute a command and add it to undo history
	// Clears redo list because we're starting a new "branch" of history
	public boolean executeCommand(Command command)
	{
		if (command.execute())
		{
			undoList.addLast(command);  // Add to end (most recent)
			redoList.clear();  // Clear redo when new command is executed
			return true;
		}
		return false;
	}
	
	// Undo the last command
	// Removes from the END of undoList (most recently added = last executed command)
	// Moves command from undo list to redo list
	public String undo()
	{
		if (undoList.isEmpty())
		{
			return null;  // Nothing to undo
		}
		Command command = undoList.removeLast();  // Remove from END (most recent command)
		command.undo();
		redoList.addLast(command);
		return command.getDescription();
	}
	
	// Redo the last undone command
	// Moves command from redo list back to undo list
	public String redo()
	{
		if (redoList.isEmpty())
		{
			return null;  // Nothing to redo
		}
		Command command = redoList.removeLast();  // Remove from END
		command.execute();
		undoList.addLast(command);
		return command.getDescription();
	}
	
	// Get list of all commands that can be undone (for display)
	// Display in reverse order so most recent command is first (top of stack)
	public List<String> getUndoList()
	{
		List<String> list = new ArrayList<>();
		for (int i = undoList.size() - 1; i >= 0; i--)
		{
			list.add(undoList.get(i).getDescription());
		}
		return list;
	}
	
	// Get list of all commands that can be redone (for display)
	// Display in reverse order so most recent undo is first
	public List<String> getRedoList()
	{
		List<String> list = new ArrayList<>();
		for (int i = redoList.size() - 1; i >= 0; i--)
		{
			list.add(redoList.get(i).getDescription());
		}
		return list;
	}
}
