import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class ModifyMusicianInstrumentCommand implements Command
{
	private String ensembleId;
	private MusicianMemento memento;	// Memento of the last modified musician, use for description
	boolean hasUndo = false;
	
	public ModifyMusicianInstrumentCommand()
	{
	}
	
	public void readInput(Scanner scanner)
	{
		Map<String, Ensemble> ensembles = MEMS.getEnsembles();

		this.ensembleId = MEMS.getCurrentEnsembleId();
		Ensemble ensemble = ensembles.get(ensembleId);
		
		if (ensemble == null) {
			throw new IllegalArgumentException("No current ensemble set. Please create or switch to an ensemble first.");
		}
	}
	
	public boolean execute()
	{
		Map<String, Ensemble> ensembles = MEMS.getEnsembles();
		
		Ensemble ensemble = ensembles.get(ensembleId);
		if (ensemble == null) {
			throw new IllegalStateException("Ensemble " + ensembleId + " no longer exists!");
		}

		
		
		if(hasUndo) 
		{
			System.out.println("Restoring role from last undo.");
			EnsembleCaretaker.restoreMemento();
			hasUndo = false;
			List<Musician> musicianList = new ArrayList<>();
			Iterator<Musician> musiciansBeforeUndo = ensemble.getMusicians();
			while (musiciansBeforeUndo.hasNext()) 
			{
				musicianList.add(musiciansBeforeUndo.next());
			}
			EnsembleCaretaker.createMemento(ensemble.getEnsembleID(), musicianList, ensemble.getName());
			return true;
		}
	
		List<Musician> musicianList = new ArrayList<>();
		Iterator<Musician> musiciansBeforeUndo = ensemble.getMusicians();
		while (musiciansBeforeUndo.hasNext()) {
			musicianList.add(musiciansBeforeUndo.next());
		}
		EnsembleCaretaker.createMemento(ensemble.getEnsembleID(), musicianList, ensemble.getName());

		ensemble.updateMusicianRole();
		this.memento = MusicianCaretaker.popMemento();

		return true;
	}
	
	public boolean undo()
	{
		Map<String, Ensemble> ensembles = MEMS.getEnsembles();
		Ensemble ensemble = ensembles.get(ensembleId);
		
		Iterator<Musician> musiciansBeforeUndo = ensemble.getMusicians();
		List<Musician> musicianList = new ArrayList<>();
		while (musiciansBeforeUndo.hasNext()) {
			musicianList.add(musiciansBeforeUndo.next());
		}

		EnsembleCaretaker.restoreMemento();
		
		EnsembleCaretaker.createMemento(ensemble.getEnsembleID(), musicianList, ensemble.getName());

		hasUndo = true;
		return true;
	}
	
	public String getDescription()
	{

		return String.format("Modify musician's instrument, %s, original role: %s", memento.getMID(), memento.getRoleName());
	}
}
