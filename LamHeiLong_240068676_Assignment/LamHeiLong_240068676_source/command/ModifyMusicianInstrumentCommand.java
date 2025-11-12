import java.util.Map;
import java.util.Scanner;

public class ModifyMusicianInstrumentCommand implements Command
{
	private String ensembleId;
	private MusicianMemento memento;	// Memento of the last modified musician, use for description
	
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
		
		EnsembleCaretaker.createMemento(ensemble.getEnsembleID(), ensemble.getMusicians(), ensemble.getName());

		ensemble.updateMusicianRole();
		this.memento = MusicianCaretaker.popMemento();

		return true;
	}
	
	public boolean undo()
	{
		EnsembleCaretaker.restoreMemento();
		return true;
	}
	
	public String getDescription()
	{

		return String.format("Modify musician's instrument, %s, %s", memento.getMID(), memento.getRoleName());
	}
}
