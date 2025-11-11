public class AddMusicianCommandFactory implements CommandFactory
{
	public Command createCommand()
	{
		return new AddMusicianCommand();
	}
}
