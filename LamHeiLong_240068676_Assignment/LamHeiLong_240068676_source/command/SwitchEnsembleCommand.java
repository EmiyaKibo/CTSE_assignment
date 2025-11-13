import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * SwitchEnsembleCommand - switches the current active ensemble
 */
public class SwitchEnsembleCommand implements Command
{
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
		Map<String, Ensemble> ensembles = MEMS.getEnsembles();
		Ensemble ensemble = ensembles.get(MEMS.getCurrentEnsembleId());
		List<Musician> musicianList = new ArrayList<>();
		Iterator<Musician> musiciansBeforeUndo = ensemble.getMusicians();
		while (musiciansBeforeUndo.hasNext()) {
			musicianList.add(musiciansBeforeUndo.next());
		}
		EnsembleCaretaker.createMemento(ensemble.getEnsembleID(), musicianList, ensemble.getName());
		ensemble = ensembles.get(ensembleId);

		if (ensemble == null)
		{
			System.out.println("Ensemble " + ensembleId + " is not found!!");
			return false;
		}
		MEMS.setCurrentEnsembleId(ensembleId);
		return true;
	}
	
	public boolean undo()
	{
		EnsembleCaretaker.restoreMemento();
		return true;
	}
	
	public String getDescription()
	{
		return "Switch to ensemble " + ensembleId;
	}
}
