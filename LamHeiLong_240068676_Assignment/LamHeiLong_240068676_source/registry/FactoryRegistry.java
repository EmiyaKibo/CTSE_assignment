import java.util.HashMap;
import java.util.Map;

/**
 * FactoryRegistry - central place to store and retrieve all factories
 * Part of the Factory pattern implementation
 * 
 * This supports the Open-Closed Principle:
 * - To add a new ensemble type, just register new factories here
 * - No need to modify existing code
 */
public class FactoryRegistry
{
	private Map<String, EnsembleFactory> ensembleFactories;   // Stores ensemble factories
	private Map<String, MusicianFactory> musicianFactories;   // Stores musician factories
	private Map<String, CommandFactory> commandFactories;     // Stores command factories (for future use)
	
	// Constructor - sets up the registry and registers default factories
	public FactoryRegistry()
	{
		this.ensembleFactories = new HashMap<>();
		this.musicianFactories = new HashMap<>();
		this.commandFactories = new HashMap<>();
		registerDefaultFactories();  // Register orchestra and jazz band
	}
	
	// Register the built-in ensemble types (orchestra and jazz band)
	// Supports both short names (o, j) and full names (orchestra, jazz)
	private void registerDefaultFactories()
	{
		// Register ensemble factories
		registerEnsembleFactory("orchestra", new OrchestraEnsembleFactory());
		registerEnsembleFactory("o", new OrchestraEnsembleFactory());  // Short form
		registerEnsembleFactory("jazz", new JazzBandEnsembleFactory());
		registerEnsembleFactory("j", new JazzBandEnsembleFactory());   // Short form
		
		// Register musician factories
		registerMusicianFactory("orchestra", new OrchestraMusicianFactory());
		registerMusicianFactory("jazz", new JazzBandMusicianFactory());
	}
	
	// Register a new ensemble factory
	// This is how we can add new ensemble types without changing existing code
	public void registerEnsembleFactory(String type, EnsembleFactory factory)
	{
		ensembleFactories.put(type.toLowerCase(), factory);
	}
	
	// Register a new musician factory
	public void registerMusicianFactory(String type, MusicianFactory factory)
	{
		musicianFactories.put(type.toLowerCase(), factory);
	}
	
	// Register a new command factory (for future extensibility)
	public void registerCommandFactory(String commandType, CommandFactory factory)
	{
		commandFactories.put(commandType, factory);
	}
	
	// Get an ensemble factory by type name
	// Throws exception if type doesn't exist
	public EnsembleFactory getEnsembleFactory(String type)
	{
		EnsembleFactory factory = ensembleFactories.get(type.toLowerCase());
		if (factory == null)
		{
			throw new IllegalArgumentException("Unknown ensemble type: " + type);
		}
		return factory;
	}
	
	// Get a musician factory by type name
	// Throws exception if type doesn't exist
	public MusicianFactory getMusicianFactory(String type)
	{
		MusicianFactory factory = musicianFactories.get(type.toLowerCase());
		if (factory == null)
		{
			throw new IllegalArgumentException("Unknown musician factory type: " + type);
		}
		return factory;
	}
	
	// Get a command factory by command type
	// Throws exception if type doesn't exist
	public CommandFactory getCommandFactory(String commandType)
	{
		CommandFactory factory = commandFactories.get(commandType);
		if (factory == null)
		{
			throw new IllegalArgumentException("Unknown command type: " + commandType);
		}
		return factory;
	}
}
