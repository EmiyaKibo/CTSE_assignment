import java.util.AbstractList;
import java.util.Iterator;
import java.util.Vector;

/**
 * Ensemble class - base class for all types of ensembles (Orchestra, Jazz Band, etc.)
 * This is an abstract class, so you can't create an Ensemble directly.
 * You need to create specific types like OrchestraEnsemble or JazzBandEnsemble.
 * 
 * Uses Template Method pattern - this class has the common code,
 * and subclasses fill in the specific parts (like how to display or validate roles).
 * 
 * Also uses Memento pattern - can save and restore state for undo/redo.
 */
public abstract class Ensemble
{
	private String ensembleID;           // Unique ID for this ensemble
	private String eName;                // Name of the ensemble
	private AbstractList<Musician> musicians;    // List of musicians in this ensemble
	
	// Constructor - creates a new ensemble with an ID
	public Ensemble(String eID)
	{
		this.ensembleID = eID;
		this.eName = "";
		this.musicians = new Vector<>();  // Using Vector to store musicians
	}
	
	// Getter and setter methods
	public String getEnsembleID() { return ensembleID; }
	public String getName() { return eName; }
	public void setName(String eName) { this.eName = eName; }
	
	// Add a musician to this ensemble
	public void addMusician(Musician m)
	{
		musicians.add(m);
	}
	
	// Remove a musician from this ensemble
	public void dropMusician(Musician m)
	{
		musicians.remove(m);
	}
	
	// Get an iterator to loop through all musicians
	public Iterator<Musician> getMusicians()
	{
		return musicians.iterator();
	}
	
	// Abstract methods - subclasses must implement these
	// Each ensemble type has different rules for roles and different display formats
	public abstract void updateMusicianRole();
	public abstract void showEnsemble();
}
