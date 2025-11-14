import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

        if (!musicians.containsKey(musicianId)) 
        {
            throw new IllegalArgumentException("Musician ID '" + musicianId + "' does not exist!");
        }

        Iterator<Musician> it = ensemble.getMusicians();

        while (it.hasNext()) {
            Musician m = it.next();
            if (m.getMID().equals(musicianId)) 
            {
                // Save state BEFORE deletion - create memento before any modifications
                List<Musician> musicianList = new ArrayList<>();
                Iterator<Musician> musiciansBeforeExecute = ensemble.getMusicians();
                while (musiciansBeforeExecute.hasNext()) 
                {
                    musicianList.add(musiciansBeforeExecute.next());
                }

                EnsembleCaretaker.createMemento(ensemble.getEnsembleID(), musicianList, ensemble.getName());
                
                // Now perform the deletion
                Musician musician = musicians.get(musicianId);
                if (musician != null)
                {
                    musicians.remove(musicianId);
                    ensemble.dropMusician(musician);
                    System.out.println("Musician is deleted.");
                    return true;
                }
                break;
            }
        }

        throw new IllegalArgumentException("Musician ID '" + musicianId + "' does not exist in the current ensemble!");
    }
    
    public boolean undo()
    {
        Map<String, Ensemble> ensembles = MEMS.getEnsembles();
        Map<String, Musician> musicians = MEMS.getMusicians();
        
        // Restore the ensemble state (this adds musicians back to ensemble)
        EnsembleCaretaker.restoreMemento();
        
        // After restoring, get the ensemble and re-add all musicians to the global musicians map
        Ensemble ensemble = ensembles.get(ensembleId);
        if (ensemble != null) {
            Iterator<Musician> it = ensemble.getMusicians();
            while (it.hasNext()) {
                Musician m = it.next();
                // Add musician back to global map
                musicians.put(m.getMID(), m);
            }
        }
        
        return true;
    }
    
    public String getDescription()
    {
        return String.format("Delete musician, %s", musicianId);
    }
}
