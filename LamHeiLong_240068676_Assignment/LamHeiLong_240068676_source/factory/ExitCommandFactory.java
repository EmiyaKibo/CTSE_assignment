/**
 * ExitCommandFactory - creates ExitCommand objects
 */
public class ExitCommandFactory implements CommandFactory
{
	public Command createCommand()
	{
		return new ExitCommand();
	}
}
