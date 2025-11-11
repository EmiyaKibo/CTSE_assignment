public class ChangeEnsembleNameCommandFactory implements CommandFactory
{
	public Command createCommand()
	{
		return new ChangeEnsembleNameCommand();
	}
}
