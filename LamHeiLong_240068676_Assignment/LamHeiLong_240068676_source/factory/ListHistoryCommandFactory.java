/**
 * ListHistoryCommandFactory - creates ListHistoryCommand objects
 */
public class ListHistoryCommandFactory implements CommandFactory
{
	public Command createCommand()
	{
		return new ListHistoryCommand();
	}
}
