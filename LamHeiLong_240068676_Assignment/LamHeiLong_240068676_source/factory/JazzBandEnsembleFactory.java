/**
 * JazzBandEnsembleFactory - creates jazz band ensembles
 * Part of the Factory pattern implementation
 */
public class JazzBandEnsembleFactory implements EnsembleFactory
{
	// Create a new jazz band ensemble
	public Ensemble createEnsemble(String eID)
	{
		return new JazzBandEnsemble(eID);
	}
	
	// Return the type name for display
	public String getEnsembleType() { return "jazz band"; }
}
