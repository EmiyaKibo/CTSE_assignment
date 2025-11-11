import java.util.Iterator;
import java.util.Scanner;

/**
 * JazzBandEnsemble - represents a jazz band type ensemble
 * Extends the Ensemble base class and implements jazz band-specific behavior
 * 
 * Jazz Band has three types of musicians:
 * - Pianist (role = 1)
 * - Saxophonist (role = 2)
 * - Drummer (role = 3)
 */
public class JazzBandEnsemble extends Ensemble
{
	// Role constants for jazz band
	private final int PIANIST_ROLE = 1;
	private final int SAXOPHONIST_ROLE = 2;
	private final int DRUMMER_ROLE = 3;
	
	// Constructor
	public JazzBandEnsemble(String eID)
	{
		super(eID);
	}
	
	// Update musician's role - only allows valid jazz band roles (1, 2, or 3)
	public void updateMusicianRole()
	{
		Scanner scanner = MEMS.getScanner();
		
		String musicianId;
		int newRole;

		System.out.print("Please input musician ID:- ");
		musicianId = scanner.nextLine().trim();
		if (musicianId.isEmpty()) {
			scanner.close();
			throw new IllegalArgumentException("Musician ID cannot be empty");
		}
		
		Musician m = MEMS.getMusicians().get(musicianId);
		if( m == null ) {
			scanner.close();
			throw new IllegalArgumentException("Musician with ID " + musicianId + " does not exist");
		}

		System.out.print("Instrument (1 = pianist | 2 = saxophonist | 3 = drummer ):- ");
		String roleInput = scanner.nextLine().trim();
		if (roleInput.isEmpty()) {
			scanner.close();
			throw new IllegalArgumentException("Instrument selection cannot be empty");
		}
		newRole = Integer.parseInt(roleInput);

		if (newRole == PIANIST_ROLE || newRole == SAXOPHONIST_ROLE || newRole == DRUMMER_ROLE)
		{
			m.setRole(newRole);
			String roleName = "";
			switch (newRole) 
			{
				case PIANIST_ROLE:
					roleName = "pianist";
					break;
				case SAXOPHONIST_ROLE:
					roleName = "saxophonist";
					break;
				case DRUMMER_ROLE:
					roleName = "drummer";
					break;
			}
			System.out.println("Instrument is updated.");
			MEMS.setLastModifiedMusician(m, roleName);
		}
		else
		{
			System.out.println("Invalid instrument selection. No changes made.");
		}
	}
	
	// Display the jazz band and all its musicians grouped by instrument
	public void showEnsemble()
	{
		System.out.println("Jazz Band Ensemble " + getName() + " (" + getEnsembleID() + ")");
		
		// Display all pianists
		System.out.println("Pianist:");
		Iterator<Musician> it = getMusicians();
		boolean hasPianist = false;
		while (it.hasNext())
		{
			Musician m = it.next();
			if (m.getRole() == PIANIST_ROLE)
			{
				System.out.println(m.getMID() + ", " + m.getName());
				hasPianist = true;
			}
		}
		if (!hasPianist)
		{
			System.out.println("NIL");
		}
		
		// Display saxophonists
		System.out.println("Saxophonist:");
		it = getMusicians();
		boolean hasSaxophonist = false;
		while (it.hasNext())
		{
			Musician m = it.next();
			if (m.getRole() == SAXOPHONIST_ROLE)
			{
				System.out.println(m.getMID() + ", " + m.getName());
				hasSaxophonist = true;
			}
		}
		if (!hasSaxophonist)
		{
			System.out.println("NIL");
		}
		
		// Display drummers
		System.out.println("Drummer:");
		it = getMusicians();
		boolean hasDrummer = false;
		while (it.hasNext())
		{
			Musician m = it.next();
			if (m.getRole() == DRUMMER_ROLE)
			{
				System.out.println(m.getMID() + ", " + m.getName());
				hasDrummer = true;
			}
		}
		if (!hasDrummer)
		{
			System.out.println("NIL");
		}
	}
}
