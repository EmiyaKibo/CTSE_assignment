/**
 * JazzBandMusicianFactory - creates musicians for jazz band ensembles
 * Part of the Factory pattern implementation
 * Validates that musicians have valid jazz band roles (pianist, saxophonist, or drummer)
 */
public class JazzBandMusicianFactory implements MusicianFactory
{
	// Valid roles for jazz band
	private final int PIANIST_ROLE = 1;
	private final int SAXOPHONIST_ROLE = 2;
	private final int DRUMMER_ROLE = 3;
	
	// Create a musician with validation
	// Throws exception if role is invalid
	public Musician createMusician(String mID, String name, int role)
	{
		if (!isValidRole(role))
		{
			throw new IllegalArgumentException("Invalid role for jazz band: " + role);
		}
		Musician m = new Musician(mID);
		m.setName(name);
		m.setRole(role);
		return m;
	}
	
	// Check if a role number is valid for jazz band
	public boolean isValidRole(int role)
	{
		return role == PIANIST_ROLE || role == SAXOPHONIST_ROLE || role == DRUMMER_ROLE;
	}
	
	// Get the name of a role for display
	public String getRoleName(int role)
	{
		switch (role)
		{
			case PIANIST_ROLE: return "pianist";
			case SAXOPHONIST_ROLE: return "saxophonist";
			case DRUMMER_ROLE: return "drummer";
			default: return "unknown";
		}
	}
	
	// Get the role selection prompt (OCP compliant)
	public String getRolePrompt()
	{
		return "Instrument (1 = pianist | 2 = saxophonist | 3 = drummer):- ";
	}
}
