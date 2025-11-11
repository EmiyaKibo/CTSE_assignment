import java.util.Map;
import java.util.Scanner;

/**
 * ShowEnsembleCommand - displays the current ensemble details
 * This is a non-undoable command (doesn't go in history)
 */
public class ShowEnsembleCommand implements Command
{
	public ShowEnsembleCommand()
	{
	}
	
	public void readInput(Scanner scanner)
	{
		// No input needed
	}
	
	public boolean execute()
	{
		Map<String, Ensemble> ensembles = MEMS.getEnsembles();
		
		Ensemble ensemble = ensembles.get(MEMS.getCurrentEnsembleId());
		if (ensemble != null)
		{
			ensemble.showEnsemble();
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
		return "Show ensemble details";
	}
}
