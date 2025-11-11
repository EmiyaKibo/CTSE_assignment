/**
 * UndoCommandFactory - creates UndoCommand objects
 * Part of the Factory pattern implementation
 */
public class UndoCommandFactory implements CommandFactory
{
	public Command createCommand()
	{
		return new UndoCommand();
	}
}
