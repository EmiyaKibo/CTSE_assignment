import java.util.Map;
import java.util.Scanner;

public class DeleteMusicianCommand implements Command
{
	private String musicianId;
	private String ensembleId;

	public DeleteMusicianCommand()
	{
	}
	
	public void readInput(Scanner scanner)
	{
		this.ensembleId = MEMS.getCurrentEnsembleId();
		if (this.ensembleId == null) {
			throw new IllegalArgumentException("No current ensemble set. Please create or switch to an ensemble first.");
		}
		System.out.print("Please input musician ID:- ");
		this.musicianId = scanner.nextLine().trim();
		if (this.musicianId.isEmpty()) {
			throw new IllegalArgumentException("Musician ID cannot be empty");
		}
	}
	
	public boolean execute()
	{
		Map<String, Ensemble> ensembles = MEMS.getEnsembles();
		Map<String, Musician> musicians = MEMS.getMusicians();
		
		Ensemble ensemble = ensembles.get(ensembleId);
		if (ensemble == null) {
			throw new IllegalStateException("Ensemble " + ensembleId + " no longer exists!");
		}
		
		EnsembleCaretaker.createMemento(ensemble.getEnsembleID(), ensemble.getMusicians(), ensemble.getName());
		Musician musician = musicians.get(musicianId);
		if (musician != null)
		{
			ensemble.dropMusician(musician);
			musicians.remove(musicianId);
			System.out.println("Musician is deleted.");
			return true;
		}
		return false;
	}
	
	public boolean undo()
	{
		EnsembleCaretaker.restoreMemento();
		return true;
	}
	
	public String getDescription()
	{
		return String.format("Delete musician, %s", musicianId);
	}
}
