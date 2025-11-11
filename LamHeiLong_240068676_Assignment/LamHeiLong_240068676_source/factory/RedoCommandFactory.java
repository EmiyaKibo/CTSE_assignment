/**
 * RedoCommandFactory - creates RedoCommand objects
 * Part of the Factory pattern implementation
 */
public class RedoCommandFactory implements CommandFactory
{
	public Command createCommand()
	{
		return new RedoCommand();
	}
}
