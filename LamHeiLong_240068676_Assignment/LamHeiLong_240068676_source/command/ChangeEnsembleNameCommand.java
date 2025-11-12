import java.util.Map;
import java.util.Scanner;

public class ChangeEnsembleNameCommand implements Command
{
	private String ensembleId;
	private String newName;
	
	public ChangeEnsembleNameCommand()
	{
	}
	
	public void readInput(Scanner scanner)
	{
		this.ensembleId = MEMS.getCurrentEnsembleId();
		if (this.ensembleId == null) {
			throw new IllegalArgumentException("No current ensemble set. Please create or switch to an ensemble first.");
		}
		System.out.print("Please input new name of the current ensemble:- ");
		this.newName = scanner.nextLine().trim();
		if (this.newName.isEmpty()) {
			throw new IllegalArgumentException("Ensemble name cannot be empty");
		}
	}
	
	public boolean execute()
	{
		Map<String, Ensemble> ensembles = MEMS.getEnsembles();
		
		Ensemble ensemble = ensembles.get(ensembleId);
		if (ensemble == null) {
			throw new IllegalStateException("Ensemble " + ensembleId + " no longer exists!");
		}
		
		EnsembleCaretaker.createMemento(ensemble.getEnsembleID(), ensemble.getMusicians(), ensemble.getName());
		ensemble.setName(newName);
		System.out.println("Ensemble's name is updated.");
		return true;
	}
	
	public boolean undo()
	{
		EnsembleCaretaker.restoreMemento();
		return true;
	}
	
	public String getDescription()
	{
		return String.format("Change ensemble's name, %s, %s", ensembleId, newName);
	}
}
