import java.util.Stack;

public class MusicianCaretaker 
{
    private static Stack<MusicianMemento> mementoStack = new Stack<MusicianMemento>();
    // Memento pattern methods - for undo/redo functionality
	// Save current state
	public static void createMemento(Musician musician, String roleName)
	{
		mementoStack.push(new MusicianMemento(musician, roleName));
	}

    // Because the command needs to show the modified musician info in description
    // The memento will be restored by the command's undo method
    // Pop the last saved state, the memento will be restore by the caller
    public static MusicianMemento popMemento()
    {
        if (!mementoStack.isEmpty()) {
            return mementoStack.pop();
        }
        return null;
    }
}
