/**
 * MusicianFactory interface - part of the Factory pattern
 * Used to create musicians with proper validation for each ensemble type
 * 
 * Different ensemble types have different valid roles:
 * - Orchestra: 1=violinist, 2=cellist
 * - Jazz Band: 1=pianist, 2=saxophonist, 3=drummer
 */
public interface MusicianFactory
{
	public Musician createMusician(String mID, String name, int role);  // Create musician with validation
	public boolean isValidRole(int role);                                // Check if role is valid
	public String getRoleName(int role);                                 // Get role name for display
	public String getRolePrompt();                                       // Get prompt for role selection (OCP)
}
