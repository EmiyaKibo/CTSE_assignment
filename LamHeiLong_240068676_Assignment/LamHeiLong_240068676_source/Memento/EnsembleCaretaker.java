import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class EnsembleCaretaker 
{
    private static Stack<EnsembleMemento> mementoStack = new Stack<EnsembleMemento>();
    // Memento pattern methods - for undo/redo functionality
	// Save current state
	public static synchronized void createMemento(String Id, List<Musician> musicians, String eName)
	{
 		mementoStack.push(new EnsembleMemento(Id, musicians.iterator(), eName));
	}
	
	// Restore previous state
	public static synchronized void restoreMemento()
    {
        // Clear current musicians and restore from memento
        // This replaces the current state with the saved state
        // Note: We assume Ensemble has methods to clear and add musicians
        
        //Get the latest memento
        EnsembleMemento memento = mementoStack.pop();

        //Get the Ensemble related to the latest memento
        Map<String, Ensemble> ensembles = MEMS.getEnsembles();
        Ensemble ensembleToRestore = ensembles.get(memento.getEID()); 

        //collect all current musicians into a list to avoid ConcurrentModificationException
        List<Musician> currentMusicians = new ArrayList<>();
        Iterator<Musician> it = ensembleToRestore.getMusicians();
        while (it.hasNext())
        {
            currentMusicians.add(it.next());
        }
        
        // Now remove them (won't throw ConcurrentModificationException)
        for (Musician m : currentMusicians)
        {
            ensembleToRestore.dropMusician(m);
        }
        
        // Restore musicians from memento
        for (Musician m : memento.getMusicians())
        {
            ensembleToRestore.addMusician(m);
            System.out.println("Restored musician: " + m.getName());
        }
        ensembleToRestore.setName(memento.getName());

        // Restore the current ensemble
        MEMS.setCurrentEnsembleId(memento.getEID());
    } 
}
