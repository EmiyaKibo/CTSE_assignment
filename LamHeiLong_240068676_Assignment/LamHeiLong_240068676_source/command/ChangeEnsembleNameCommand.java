import java.util.Map;
import java.util.Scanner;

public class ChangeEnsembleNameCommand implements Command
{
	private String ensembleId;
	private String newName;
	private EnsembleMemento memento;
	private String previousEnsembleId;
	
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
		
		this.memento = EnsembleCaretaker.createMemento(ensemble.getMusicians(), ensemble.getName());
		this.previousEnsembleId = MEMS.getCurrentEnsembleId();
		ensemble.setName(newName);
		System.out.println("Ensemble's name is updated.");
		return true;
	}
	
	public boolean undo()
	{
		Map<String, Ensemble> ensembles = MEMS.getEnsembles();
		
		Ensemble ensemble = ensembles.get(ensembleId);
		EnsembleCaretaker.restoreMemento(ensemble, memento);
		
		// Restore current ensemble context
		MEMS.setCurrentEnsembleId(previousEnsembleId);
		
		return true;
	}
	
	public String getDescription()
	{
		return String.format("Change ensemble's name, %s, %s", ensembleId, newName);
	}
}
