/**
 * ShowEnsembleCommandFactory - creates ShowEnsembleCommand objects
 */
public class ShowEnsembleCommandFactory implements CommandFactory
{
	public Command createCommand()
	{
		return new ShowEnsembleCommand();
	}
}
