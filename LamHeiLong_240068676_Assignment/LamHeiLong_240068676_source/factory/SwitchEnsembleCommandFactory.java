/**
 * SwitchEnsembleCommandFactory - creates SwitchEnsembleCommand objects
 */
public class SwitchEnsembleCommandFactory implements CommandFactory
{
	public Command createCommand()
	{
		return new SwitchEnsembleCommand();
	}
}
