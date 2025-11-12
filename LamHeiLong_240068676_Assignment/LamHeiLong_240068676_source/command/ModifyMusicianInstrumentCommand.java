import java.util.Map;
import java.util.Scanner;

public class ModifyMusicianInstrumentCommand implements Command
{
	private String ensembleId;
	private String previousEnsembleId;
	
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
		this.previousEnsembleId = MEMS.getCurrentEnsembleId();

		ensemble.updateMusicianRole();
		return true;
	}
	
	public boolean undo()
	{
		EnsembleCaretaker.restoreMemento();
		
		// Restore current ensemble context
		MEMS.setCurrentEnsembleId(previousEnsembleId);
		
		return true;
	}
	
	public String getDescription()
	{
		String musicianId = MEMS.getLastModifiedMusician().getMID();
		String roleName = MEMS.getLastModifiedRoleName();
		return String.format("Modify musician's instrument, %s, %s", musicianId, roleName);
	}
}
