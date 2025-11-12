/**
 * CommandEntry - wrapper class that associates command aliases with their factory
 * This allows us to use a list-based command lookup instead of switch statements
 * 
 * Supports multiple aliases for the same command (e.g., "h", "help", "?" all show help)
 * This provides flexibility for both beginners (full names) and power users (short aliases)
 * 
 * ALL commands use the Command pattern (as per assignment requirements)
 */
public class CommandEntry
{
	private String[] aliases;            // All ways to invoke this command (e.g., ["h", "help", "?"])
	private String primaryName;          // Main command name for help display (e.g., "help")
	private String description;          // What this command does
	private boolean requiresEnsemble;    // Does this command need a current ensemble?
	private boolean isUndoable;          // Should this command be added to history?
	private CommandFactory commandFactory;  // Factory to create the command
	
	// Constructor - all commands use Command pattern now
	public CommandEntry(CommandFactory commandFactory, boolean isUndoable, boolean requiresEnsemble,
	                    String primaryName, String description, String... aliases)
	{
		this.primaryName = primaryName;
		this.description = description;
		this.aliases = aliases;
		this.commandFactory = commandFactory;
		this.requiresEnsemble = requiresEnsemble;
		this.isUndoable = isUndoable;
	}
	
	// Check if user input matches any of this command's aliases
	// Currently only single alias is used, but this supports multiple aliases for future flexibility
	public boolean matches(String input)
	{
		for (String alias : aliases)
		{
			if (alias.equalsIgnoreCase(input))
			{
				return true;
			}
		}
		return false;
	}
	
	// Get formatted aliases for display (e.g., "h/help/?")
	// Not used for now, but could be useful for if aliases is implemented later
	public String getAliasesFormatted()
	{
		return String.join("/", aliases);
	}
	
	// Getters
	public boolean requiresEnsemble() { return requiresEnsemble; }
	public boolean isUndoable() { return isUndoable; }
	public CommandFactory getCommandFactory() { return commandFactory; }
	public String getPrimaryName() { return primaryName; }
	public String getDescription() { return description; }
}
