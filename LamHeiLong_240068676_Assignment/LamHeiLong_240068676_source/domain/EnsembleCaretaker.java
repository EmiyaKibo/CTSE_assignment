import java.util.Iterator;

public class EnsembleCaretaker 
{

    // Memento pattern methods - for undo/redo functionality
	// Save current state
	public static EnsembleMemento createMemento(Iterator<Musician> musicians, String eName)
	{
		return new EnsembleMemento(musicians, eName);
	}
	
	// Restore previous state
	public static void restoreMemento(Ensemble ensemble, EnsembleMemento memento)
    {
        // Clear current musicians and restore from memento
        // This replaces the current state with the saved state
        // Note: We assume Ensemble has methods to clear and add musicians
        
        // First, collect all current musicians into a list to avoid ConcurrentModificationException
        java.util.List<Musician> currentMusicians = new java.util.ArrayList<>();
        Iterator<Musician> it = ensemble.getMusicians();
        while (it.hasNext())
        {
            currentMusicians.add(it.next());
        }
        
        // Now remove them (won't throw ConcurrentModificationException)
        for (Musician m : currentMusicians)
        {
            ensemble.dropMusician(m);
        }
        
        // Restore musicians from memento
        for (Musician m : memento.getMusicians())
        {
            ensemble.addMusician(m);
        }
        ensemble.setName(memento.getName());
    } 
}
