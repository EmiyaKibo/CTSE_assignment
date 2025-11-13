import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * MEMS - Musical Ensembles Management System
 * Main application class that provides a command-line interface for managing ensembles and musicians
 * 
 * This system demonstrates several design patterns:
 * - Factory Pattern: for creating ensembles and musicians
 * - Command Pattern: for undo/redo functionality
 * - Memento Pattern: for saving/restoring ensemble state
 * - Template Method Pattern: in the Ensemble hierarchy
 * - Open-Closed Principle: can add new ensemble types without modifying existing code
 * 
 * Command Routing: Uses a list of CommandEntry objects for flexible command matching
 * without switch statements. To add a new command, just add an entry to the list.
 * 
 * Supported commands:
 * c  = create ensemble
 * s  = set current ensemble
 * a  = add musician
 * m  = modify musician's instrument
 * d  = delete musician
 * se = show ensemble
 * sa = display all ensembles
 * cn = change ensemble's name
 * u  = undo
 * r  = redo
 * l  = list undo/redo
 * x  = exit system
 */
public class MEMS
{
	// Simplified data storage - no registry pattern needed
	private Map<String, Ensemble> ensembles;        // All ensembles by ID
	private Map<String, Musician> musicians;        // All musicians by ID
	private String currentEnsembleId;               // Current ensemble ID (null if none)
	private FactoryRegistry factoryRegistry;        // Manages factories
	private HistoryManager historyManager;          // Handles undo/redo
	private Scanner scanner;                        // For user input
	private List<CommandEntry> availableCommands;   // List of all available commands
		
	// Static instance for command access (supports OCP by avoiding parameter passing)
	private static MEMS instance;
	
	// Static accessors for commands to get dependencies without constructor injection
	// This allows all factories to use the same signature: createCommand()
	public static Map<String, Ensemble> getEnsembles() { return instance.ensembles; }
	public static Map<String, Musician> getMusicians() { return instance.musicians; }
	public static String getCurrentEnsembleId() { return instance.currentEnsembleId; }
	public static void setCurrentEnsembleId(String id) { instance.currentEnsembleId = id; }
	public static FactoryRegistry getFactoryRegistry() { return instance.factoryRegistry; }
	public static HistoryManager getHistoryManager() { return instance.historyManager; }
	public static Scanner getScanner() { return instance.scanner; }
	
	// Constructor - initializes all components
	public MEMS()
	{
		instance = this;  // Set static instance for command access
		this.ensembles = new HashMap<>();
		this.musicians = new HashMap<>();
		this.currentEnsembleId = null;  // No ensemble selected initially
		this.factoryRegistry = new FactoryRegistry();
		this.historyManager = new HistoryManager();
		this.scanner = new Scanner(System.in);
		this.availableCommands = new ArrayList<>();
		
		// Initialize the command list
		initializeCommands();
	}
	
	// Initialize all available commands
	// ALL commands use the Command pattern
	private void initializeCommands()
	{
		// Commands that modify state (undoable) - go into history
		availableCommands.add(new CommandEntry(
			new CreateEnsembleCommandFactory(), true, false, "create", "create ensemble", "c"));
		availableCommands.add(new CommandEntry(
			new AddMusicianCommandFactory(), true, true, "add", "add musician", "a"));
		availableCommands.add(new CommandEntry(
			new ModifyMusicianInstrumentCommandFactory(), true, true, "modify", "modify musician's instrument", "m"));
		availableCommands.add(new CommandEntry(
			new DeleteMusicianCommandFactory(), true, true, "remove", "delete musician", "d"));
		availableCommands.add(new CommandEntry(
			new ChangeEnsembleNameCommandFactory(), true, true, "rename", "change ensemble's name", "cn"));
		availableCommands.add(new CommandEntry(
			new SwitchEnsembleCommandFactory(), true, false, "switch", "set current ensemble", "s"));
		
		// Undo/Redo commands (special - delegate to HistoryManager)
		availableCommands.add(new CommandEntry(
			new UndoCommandFactory(), false, false, "undo", "undo", "u"));
		availableCommands.add(new CommandEntry(
			new RedoCommandFactory(), false, false, "redo", "redo", "r"));
		
		// Commands that don't modify state (non-undoable, but still use Command pattern)
		availableCommands.add(new CommandEntry(
			new ShowEnsembleCommandFactory(), false, true, "show", "show ensemble", "se"));
		availableCommands.add(new CommandEntry(
			new ListEnsemblesCommandFactory(), false, false, "list", "display all ensembles", "sa"));
		availableCommands.add(new CommandEntry(
			new ListHistoryCommandFactory(), false, false, "history", "list undo/redo", "l"));
		
		// Utility commands
		availableCommands.add(new CommandEntry(
			new ExitCommandFactory(), false, false, "quit", "exit system", "x"));
	}

	// To add a new command, just call this method!
	public void addNewCommand(CommandEntry newCommand)
	{
		availableCommands.add(newCommand);
	}
	
	// Main loop - displays menu once, then processes commands until user exits
	public void run()
	{
		System.out.println("=".repeat(70));
		System.out.println("    Music Ensembles Management System (MEMS)");
		System.out.println("=".repeat(70));
		
		while (true)
		{
			displayPrompt();
			String command = scanner.nextLine().trim();
			System.out.println();
			if (!processCommand(command))
			{
				System.out.println("Goodbye!");
				break;
			}
		}
		scanner.close();
	}
	
	// Display current ensemble context and prompt
	private void displayPrompt()
	{
		System.out.println();
		showHelpMenu();
		if (currentEnsembleId != null)
		{
			Ensemble ensemble = ensembles.get(currentEnsembleId);
			if (ensemble != null)  // Ensemble might be deleted/undone
			{
				System.out.println("The current ensemble is " + ensemble.getEnsembleID() + " " + ensemble.getName() + ".");
			}
		}
		System.out.print("Please enter command [ c | s | a | m | d | se | sa | cn | u | r | l | x ] :- ");
	}
	
	// Show help - displays all available commands
	public static void showHelpMenu()
	{
		System.out.println("============================================");
		System.out.println("Available commands:");
		for (CommandEntry entry : instance.availableCommands)
		{
			String aliases = entry.getAliasesFormatted();
			String desc = entry.getDescription();
			System.out.println(aliases + " = " + desc + ", ");
		}
		System.out.println("============================================");
		System.out.println();
	}
	
	private boolean processCommand(String commandInput)
	{
		// Find matching command in the list
		CommandEntry matchedEntry = findCommand(commandInput);
		
		if (matchedEntry == null) {
			System.out.println("Invalid command!");
			return true;
		}
		
		// Check if current ensemble is required
		if (matchedEntry.requiresEnsemble() && currentEnsembleId == null) {
			System.out.println("Error: This command requires an ensemble to be set!");
			System.out.println("Use 'switch' to select an ensemble first.");
			return true;
		}
		
		// ALL commands use Command pattern
		// Retry loop - automatically retries on error
		boolean success = false;
		while (!success) {
			try {
				// Create the command using factory
				Command command = matchedEntry.getCommandFactory().createCommand();
				
				// Read input
				command.readInput(scanner);
				
				// Execute - add to history if undoable
				if (matchedEntry.isUndoable()) {
					historyManager.executeCommand(command);
				} else {
					command.execute();
				}
				
				success = true;  // If we get here, command succeeded
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Error: Invalid input format. Please try again.\n");
				// Auto-retry - loop continues
			} catch (NumberFormatException e) {
				System.out.println("Error: Invalid number format. Please try again.\n");
				// Auto-retry - loop continues
			} catch (NullPointerException e) {
				System.out.println("Error: Invalid selection or missing data.");
				if (e.getMessage() != null && !e.getMessage().isEmpty()) {
					System.out.println("Details: ");
					e.printStackTrace();
				}
				System.out.println("Please try again.\n");
				// Auto-retry - loop continues
			} catch (Exception e) {
				System.out.print("Error: ");
				if (e.getMessage() != null && !e.getMessage().isEmpty()) {
					System.out.println(e.getMessage());
				} else {
					System.out.println(e.getClass().getSimpleName() + " occurred");
					System.out.println("Stack trace:");
					e.printStackTrace();
				}
				System.out.println("Please try again.\n");
				// Auto-retry - loop continues
			}
		}
		
		return true;
	}
	
	// Find a command that matches the user input
	private CommandEntry findCommand(String input)
	{
		for (CommandEntry entry : availableCommands) {
			if (entry.matches(input)) {
				return entry;
			}
		}
		return null;  // No match found
	}
	
	public static void main(String[] args)
	{
		MEMS system = new MEMS();
		system.run();
	}
}
