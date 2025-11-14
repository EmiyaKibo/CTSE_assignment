import java.util.Map;
import java.util.Scanner;

/**
 * SwitchEnsembleCommand - switches the current active ensemble
 */
public class SwitchEnsembleCommand implements Command
{
	private String previousEnsembleId;
	private String ensembleId;
	
	public SwitchEnsembleCommand()
	{
	}
	
	public void readInput(Scanner scanner)
	{
		System.out.print("Please input ensemble ID:- ");
		this.ensembleId = scanner.nextLine().trim();
		if (this.ensembleId.isEmpty()) {
			throw new IllegalArgumentException("Ensemble ID cannot be empty");
		}
	}
	
	public boolean execute()
	{
		this.previousEnsembleId = MEMS.getCurrentEnsembleId();
		Map<String, Ensemble> ensembles = MEMS.getEnsembles();
		Ensemble ensemble = ensembles.get(ensembleId);

		if (ensemble == null)
		{
			System.out.println("Ensemble " + ensembleId + " is not found!!");
			throw new IllegalArgumentException("Ensemble ID '" + ensembleId + "' does not exist!");
		}
		MEMS.setCurrentEnsembleId(ensembleId);
		return true;
	}
	
	public boolean undo()
	{
		MEMS.setCurrentEnsembleId(previousEnsembleId);
		return true;
	}
	
	public String getDescription()
	{
		return "Switch to ensemble " + ensembleId;
	}
}
