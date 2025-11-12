/**
 * MusicianMemento - stores a snapshot of a Musician's state
 * Part of the Memento pattern for undo/redo functionality
 * 
 * This class saves the musician's state so we can
 * restore it later if the user wants to undo changes.
 */
public class MusicianMemento
{
	private String musicianID;	//saved Musician's ID
	private String mName;  		// Saved Musician's Name
	private int role;           // Saved Musician's role
	private String roleName;	// The name of role respective to the role int
	
	// Constructor - saves the current state
	// Makes a DEEP COPY of musicians so changes don't affect the saved state
	public MusicianMemento(Musician musician, String roleName)
	{
		this.musicianID = musician.getMID();
		this.mName = musician.getName();
		this.role = musician.getRole();
		this.roleName = roleName;
	}
	
	// Getter methods to retrieve saved state
	public String getMID() { return musicianID; }
	public String getName() { return mName; }
	public int getRole() { return role; }
	public String getRoleName() {return roleName;}
}
