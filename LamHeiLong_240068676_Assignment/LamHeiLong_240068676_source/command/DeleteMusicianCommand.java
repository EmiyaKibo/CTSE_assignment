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
        
        List<Musician> musicianList = new ArrayList<>();
        Iterator<Musician> musiciansBeforeExecute = ensemble.getMusicians();
        while (musiciansBeforeExecute.hasNext()) {
            musicianList.add(musiciansBeforeExecute.next());
        }
        EnsembleCaretaker.createMemento(ensemble.getEnsembleID(), musicianList, ensemble.getName());
        
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
        Map<String, Ensemble> ensembles = MEMS.getEnsembles();
        Map<String, Musician> musicians = MEMS.getMusicians();
        
        // Restore the ensemble state (this adds musicians back to ensemble)
        EnsembleCaretaker.restoreMemento();
        
        // Also restore the deleted musician to the global musicians map
        Ensemble ensemble = ensembles.get(ensembleId);
        if (ensemble != null) {
            Iterator<Musician> it = ensemble.getMusicians();
            while (it.hasNext()) {
                Musician m = it.next();
                // Re-add all musicians from restored ensemble to global map
                if (!musicians.containsKey(m.getMID())) {
                    musicians.put(m.getMID(), m);
                }
            }
        }
        
        return true;
    }
    
    public String getDescription()
    {
        return String.format("Delete musician, %s", musicianId);
    }
}
