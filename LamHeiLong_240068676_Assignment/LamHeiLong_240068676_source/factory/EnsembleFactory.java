/**
 * EnsembleFactory interface - part of the Factory pattern
 * Used to create different types of ensembles (Orchestra, Jazz Band, etc.)
 * 
 * This pattern helps with the Open-Closed Principle:
 * - We can add new ensemble types without changing existing code
 * - Just create a new factory class that implements this interface
 */
public interface EnsembleFactory
{
	Ensemble createEnsemble(String eID);  // Create a new ensemble
	String getEnsembleType();              // Get the type name (e.g., "orchestra")
}
