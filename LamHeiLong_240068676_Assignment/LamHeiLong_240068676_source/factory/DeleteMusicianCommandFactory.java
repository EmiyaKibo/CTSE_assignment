public class DeleteMusicianCommandFactory implements CommandFactory
{
	public Command createCommand()
	{
		return new DeleteMusicianCommand();
	}
}
