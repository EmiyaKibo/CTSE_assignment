import java.util.Map;
import java.util.Scanner;

/**
 * CreateEnsembleCommand - creates a new ensemble and sets it as current
 * Part of the Command pattern for undo/redo support with Memento pattern
 * 
 * When executed: saves state via memento, creates ensemble, adds to map, and sets as current
 * When undone: removes ensemble and restores previous state from memento
 */
public class CreateEnsembleCommand implements Command
{
	private EnsembleFactory factory;            // Factory to create the ensemble (set by readInput)
	private String ensembleId;                  // ID for the new ensemble (set by readInput)
	private String name;                        // Name for the new ensemble (set by readInput)
	private String ensembleType;                // Type name for description (set by readInput)
	private Ensemble ensemble;                  // The created ensemble (saved for undo)
	private CreateEnsembleMemento memento;      // Memento to save state before creation
	
	// Inner class - Memento to capture state before ensemble creation
	private static class CreateEnsembleMemento
	{
		private String previousCurrentEnsembleId;
		
		public CreateEnsembleMemento(String previousCurrentEnsembleId)
		{
			this.previousCurrentEnsembleId = previousCurrentEnsembleId;
		}
		
		public String getPreviousCurrentEnsembleId()
		{
			return previousCurrentEnsembleId;
		}
	}
	
	// Constructor - no parameters (gets dependencies via static MEMS accessors)
	public CreateEnsembleCommand()
	{
	}
	
	// Read user input for this command
	public void readInput(Scanner scanner)
	{
		FactoryRegistry factoryRegistry = MEMS.getFactoryRegistry();
		
		System.out.print("Enter music type (o = orchestra | j = jazz band) :- ");
		String type = scanner.nextLine().trim();
		this.factory = factoryRegistry.getEnsembleFactory(type);
		if (this.factory == null) {
			throw new IllegalArgumentException("Invalid ensemble type. Please enter 'o' for orchestra or 'j' for jazz band.");
		}
		this.ensembleType = factory.getEnsembleType();
		
		System.out.print("Ensemble ID:- ");
		this.ensembleId = scanner.nextLine().trim();
		if (this.ensembleId.isEmpty()) {
			throw new IllegalArgumentException("Ensemble ID cannot be empty");
		}
		
		// Check if ID already exists
		Map<String, Ensemble> ensembles = MEMS.getEnsembles();
		if (ensembles.containsKey(this.ensembleId)) {
			throw new IllegalArgumentException("Ensemble ID '" + this.ensembleId + "' already exists! Please use a different ID.");
		}
		
		System.out.print("Ensemble Name:- ");
		this.name = scanner.nextLine().trim();
		if (this.name.isEmpty()) {
			throw new IllegalArgumentException("Ensemble name cannot be empty");
		}
	}
	
	// Execute - create the ensemble and set it as current
	public boolean execute()
	{
		Map<String, Ensemble> ensembles = MEMS.getEnsembles();
		
		// Create memento to save current state before making changes
		this.memento = new CreateEnsembleMemento(MEMS.getCurrentEnsembleId());
		
		this.ensemble = factory.createEnsemble(ensembleId);
		ensemble.setName(name);
		ensembles.put(ensembleId, ensemble);
		MEMS.setCurrentEnsembleId(ensembleId);
		System.out.println(Character.toUpperCase(ensembleType.charAt(0)) + ensembleType.substring(1) + " ensemble is created.");
		System.out.println("Current ensemble is changed to " + ensembleId + ".");
		return true;
	}
	
	// Undo - remove the ensemble and restore previous state from memento
	public boolean undo()
	{
		Map<String, Ensemble> ensembles = MEMS.getEnsembles();
		
		// Remove the created ensemble
		ensembles.remove(ensembleId);
		
		// Restore state from memento
		String previousEnsembleId = memento.getPreviousCurrentEnsembleId();
		MEMS.setCurrentEnsembleId(previousEnsembleId);
		
		return true;
	}
	
	// Get description for display in undo/redo list
	public String getDescription()
	{
		return String.format("Create %s ensemble, %s, %s", ensembleType, ensembleId, name);
	}
}
