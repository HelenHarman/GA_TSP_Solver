package geneticOperators;

import population.Population;

/**
 * All types of selection should inherit from this class
 * 
 * @author helen
 *
 * @param <T> The type used to represent the individuals.
 */
public abstract class Mutation<T>
{
	/**
	 * The probability that mutation will take place
	 */
	int probability;	

	/**
	 * 
	 * @param population The population containing an ordered list of individuals. (Fittest individual first)
	 * @return The mutated population
	 */
	public abstract Population<T> mutatePopulation(Population<T> population);
	
	
	/////// Getters and Setters ///////
	
	public int getProbability() 
	{
		return probability;
	}

	public void setProbability(int probability) 
	{
		this.probability = probability;
	}

}
