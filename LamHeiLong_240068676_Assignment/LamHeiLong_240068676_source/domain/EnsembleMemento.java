import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * EnsembleMemento - stores a snapshot of an ensemble's state
 * Part of the Memento pattern for undo/redo functionality
 * 
 * This class saves the ensemble's musicians list and name so we can
 * restore it later if the user wants to undo changes.
 */
public class EnsembleMemento
{
	private List<Musician> musicians;  // Saved list of musicians
	private String name;                // Saved ensemble name
	
	// Constructor - saves the current state
	// Makes a DEEP COPY of musicians so changes don't affect the saved state
	public EnsembleMemento(Iterator<Musician> musicians, String name)
	{
		// Create a new list and copy each musician
		// This is important! If we just copy the list reference, changes would affect our saved state
		this.musicians = new Vector<>();
		while (musicians.hasNext())
		{
			Musician m = musicians.next();
			Musician copy = new Musician(m.getMID());
			copy.setName(m.getName());
			copy.setRole(m.getRole());
			this.musicians.add(copy); // Add the copied musician to our saved list
		}
		this.name = name;
	}
	
	// Getter methods to retrieve saved state
	public List<Musician> getMusicians() { return musicians; }
	public String getName() { return name; }
}
