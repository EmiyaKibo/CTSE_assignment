public class ModifyMusicianInstrumentCommandFactory implements CommandFactory
{
	public Command createCommand()
	{
		return new ModifyMusicianInstrumentCommand();
	}
}
