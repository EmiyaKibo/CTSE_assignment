/**
 * ListEnsemblesCommandFactory - creates ListEnsemblesCommand objects
 */
public class ListEnsemblesCommandFactory implements CommandFactory
{
	public Command createCommand()
	{
		return new ListEnsemblesCommand();
	}
}
