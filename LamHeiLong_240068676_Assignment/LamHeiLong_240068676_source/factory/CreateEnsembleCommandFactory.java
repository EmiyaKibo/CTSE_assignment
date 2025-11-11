public class CreateEnsembleCommandFactory implements CommandFactory
{
	public Command createCommand()
	{
		return new CreateEnsembleCommand();
	}
}
