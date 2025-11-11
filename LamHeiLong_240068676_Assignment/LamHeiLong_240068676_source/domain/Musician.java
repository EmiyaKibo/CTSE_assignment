/**
 * Musician class - represents a musician in the system
 * Each musician has an ID, name, and role (instrument they play)
 * 
 * Role numbers mean different things for different ensembles:
 * - Orchestra: 1 = violinist, 2 = cellist
 * - Jazz Band: 1 = pianist, 2 = saxophonist, 3 = drummer
 */
public class Musician
{
	private String musicianID;  // Unique ID for this musician
	private String mName;        // Musician's name
	private int role;            // What instrument they play (1, 2, or 3)
	
	// Constructor - creates a new musician with just an ID
	public Musician(String mID)
	{
		this.musicianID = mID;
		this.mName = "";
		this.role = 0;
	}
	
	// Getter methods
	public String getMID() { return musicianID; }
	public int getRole() { return role; }
	public String getName() { return mName; }
	
	// Setter methods
	public void setRole(int role) { this.role = role; }
	public void setName(String mName) { this.mName = mName; }
	
	
}
