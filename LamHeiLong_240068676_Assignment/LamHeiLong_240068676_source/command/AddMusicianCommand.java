import java.util.Map;
import java.util.Scanner;

public class AddMusicianCommand implements Command
{
	private Musician musician;
	private String ensembleId;
	private String roleName;
	
	public AddMusicianCommand()
	{
	}
	
	public void readInput(Scanner scanner)
	{
		Map<String, Ensemble> ensembles = MEMS.getEnsembles();
		FactoryRegistry factoryRegistry = MEMS.getFactoryRegistry();
		
		this.ensembleId = MEMS.getCurrentEnsembleId();
		Ensemble ensemble = ensembles.get(ensembleId);
		
		if (ensemble == null) {
			throw new IllegalArgumentException("No current ensemble set. Please create or switch to an ensemble first.");
		}
		
		System.out.print("Please input musician information (id, name):- ");
		String input = scanner.nextLine().trim();
		String[] parts = input.split(",", 2);
		if (parts.length < 2) {
			throw new IllegalArgumentException("Please provide both musician ID and name separated by a comma (e.g., M001, John Doe)");
		}
		String musicianId = parts[0].trim();
		String musicianName = parts[1].trim();
		
		// Check if musician ID already exists
		Map<String, Musician> musicians = MEMS.getMusicians();
		if (musicians.containsKey(musicianId)) {
			throw new IllegalArgumentException("Musician ID '" + musicianId + "' already exists! Please use a different ID.");
		}
		
		// Determine ensemble type - using instanceof is acceptable here
		// The ensemble classes themselves are not modified
		String ensembleType;
		if (ensemble instanceof OrchestraEnsemble)
		{
			ensembleType = "orchestra";
		}
		else  // JazzBandEnsemble
		{
			ensembleType = "jazz";
		}
		
		MusicianFactory musicianFactory = factoryRegistry.getMusicianFactory(ensembleType);
		
		// Use factory method for OCP compliance - no hardcoded prompts
		System.out.print(musicianFactory.getRolePrompt());
		String roleInput = scanner.nextLine().trim();
		if (roleInput.isEmpty()) {
			throw new IllegalArgumentException("Instrument selection cannot be empty");
		}
		int role = Integer.parseInt(roleInput);
		this.roleName = musicianFactory.getRoleName(role);
		
		this.musician = musicianFactory.createMusician(musicianId, musicianName, role);
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

		ensemble.addMusician(musician);
		musicians.put(musician.getMID(), musician);
		System.out.println("Musician is added.");
		return true;
	}
	
	public boolean undo()
	{
		EnsembleCaretaker.restoreMemento();		
		return true;
	}
	
	public String getDescription()
	{
		return String.format("Add musician, %s, %s, %s", musician.getMID(), musician.getName(), roleName);
	}
}
