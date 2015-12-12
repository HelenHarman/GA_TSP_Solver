package geneticOperators;

import population.Individual;
import population.Population;

/**
 * All types of selection should inherit from this class
 * @author helen
 *
 * @param <T> The type used to represent the individuals.
 */
public abstract class Selection<T> 
{
	/**
	 * 
	 * @param population The population containing an ordered list of individuals. (Fittest individual first)
	 * @param numberOfSelections 
	 * @return An array containing pairs of individuals that should be passed a crossover operation
	 */
	public abstract Individual<T>[][] selectIndividuals(Population<T> population, int numberOfSelections);
}
