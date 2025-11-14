import java.util.Iterator;
import java.util.Scanner;

/**
 * OrchestraEnsemble - represents an orchestra type ensemble
 * Extends the Ensemble base class and implements orchestra-specific behavior
 * 
 * Orchestra has two types of musicians:
 * - Violinist (role = 1)
 * - Cellist (role = 2)
 */
public class OrchestraEnsemble extends Ensemble
{
	// Role constants for orchestra
	private final int VIOLINIST_ROLE = 1;
	private final int CELLIST_ROLE = 2;
	
	// Constructor
	public OrchestraEnsemble(String eID)
	{
		super(eID);
	}
	
	// Update musician's role - only allows valid orchestra roles (1 or 2)
	public void updateMusicianRole()
	{
		Scanner scanner = MEMS.getScanner();
		
		String musicianId;
		int newRole;

		System.out.print("Please input musician ID:- ");
		musicianId = scanner.nextLine().trim();
		if (musicianId.isEmpty()) {
			
			throw new IllegalArgumentException("Musician ID cannot be empty");
		}
		
		Musician m = MEMS.getMusicians().get(musicianId);
		if( m == null ) {
			
			throw new IllegalArgumentException("Musician with ID " + musicianId + " does not exist");
		}
		
		// Check if the musician belongs to this ensemble
		boolean isInEnsemble = false;
		Iterator<Musician> it = getMusicians();
		while (it.hasNext()) {
			if (it.next().getMID().equals(musicianId)) {
				isInEnsemble = true;
				break;
			}
		}
		if (!isInEnsemble) {
			throw new IllegalArgumentException("Musician " + musicianId + " is not in this ensemble");
		}

		int currentRole = m.getRole();
		if(currentRole == VIOLINIST_ROLE || currentRole == CELLIST_ROLE)
		{
			String roleName = "";
			switch (currentRole) 
			{
				case VIOLINIST_ROLE:
					roleName = "violinist";
					break;
				case CELLIST_ROLE:
					roleName = "cellist";
					break;
			}
			MusicianCaretaker.createMemento(m, roleName);
		}
		System.out.print("Instrument (1 = violinist | 2 = cellist ):- ");
		String roleInput = scanner.nextLine().trim();
		if (roleInput.isEmpty()) {
			
			throw new IllegalArgumentException("Instrument selection cannot be empty");
		}
		newRole = Integer.parseInt(roleInput);

		if (newRole == VIOLINIST_ROLE || newRole == CELLIST_ROLE)
		{
			m.setRole(newRole);
			System.out.println("Instrument is updated.");
		}
		else
		{
			System.out.println("Invalid instrument selection. No changes made.");
		}
	}
	
	// Display the orchestra and all its musicians grouped by instrument
	public void showEnsemble()
	{
		System.out.println("Orchestra Ensemble " + getName() + " (" + getEnsembleID() + ")");
		
		// Display all violinists
		System.out.println("Violinist:");
		Iterator<Musician> it = getMusicians();
		boolean hasViolinist = false;
		while (it.hasNext())
		{
			Musician m = it.next();
			if (m.getRole() == VIOLINIST_ROLE)
			{
				System.out.println(m.getMID() + ", " + m.getName());
				hasViolinist = true;
			}
		}
		if (!hasViolinist)
		{
			System.out.println("NIL");  // Show NIL if no violinists
		}
		
		// Display all cellists
		System.out.println("Cellist:");
		it = getMusicians();
		boolean hasCellist = false;
		while (it.hasNext())
		{
			Musician m = it.next();
			if (m.getRole() == CELLIST_ROLE)
			{
				System.out.println(m.getMID() + ", " + m.getName());
				hasCellist = true;
			}
		}
		if (!hasCellist)
		{
			System.out.println("NIL");  // Show NIL if no cellists
		}
	}
}
