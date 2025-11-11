/**
 * OrchestraEnsembleFactory - creates orchestra ensembles
 * Part of the Factory pattern implementation
 */
public class OrchestraEnsembleFactory implements EnsembleFactory
{
	// Create a new orchestra ensemble
	public Ensemble createEnsemble(String eID)
	{
		return new OrchestraEnsemble(eID);
	}
	
	// Return the type name for display
	public String getEnsembleType() { return "orchestra"; }
}
