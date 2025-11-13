import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
		
		List<Musician> musicianList = new ArrayList<>();
		Iterator<Musician> musiciansBeforeUndo = ensemble.getMusicians();
		while (musiciansBeforeUndo.hasNext()) {
			musicianList.add(musiciansBeforeUndo.next());
		}
		EnsembleCaretaker.createMemento(ensemble.getEnsembleID(), musicianList, ensemble.getName());
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
