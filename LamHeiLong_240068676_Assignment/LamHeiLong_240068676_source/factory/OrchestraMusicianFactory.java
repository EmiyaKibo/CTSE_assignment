/**
 * OrchestraMusicianFactory - creates musicians for orchestra ensembles
 * Part of the Factory pattern implementation
 * Validates that musicians have valid orchestra roles (violinist or cellist)
 */
public class OrchestraMusicianFactory implements MusicianFactory
{
	// Valid roles for orchestra
	private final int VIOLINIST_ROLE = 1;
	private final int CELLIST_ROLE = 2;
	
	// Create a musician with validation
	// Throws exception if role is invalid
	public Musician createMusician(String mID, String name, int role)
	{
		if (!isValidRole(role))
		{
			throw new IllegalArgumentException("Invalid role for orchestra: " + role);
		}
		Musician m = new Musician(mID);
		m.setName(name);
		m.setRole(role);
		return m;
	}
	
	// Check if a role number is valid for orchestra
	public boolean isValidRole(int role)
	{
		return role == VIOLINIST_ROLE || role == CELLIST_ROLE;
	}
	
	// Get the name of a role for display
	public String getRoleName(int role)
	{
		switch (role)
		{
			case VIOLINIST_ROLE: return "violinist";
			case CELLIST_ROLE: return "cellist";
			default: return "unknown";
		}
	}
	
	// Get the role selection prompt (OCP compliant)
	public String getRolePrompt()
	{
		return "Instrument (1 = violinist | 2 = cellist ):- ";
	}
}
