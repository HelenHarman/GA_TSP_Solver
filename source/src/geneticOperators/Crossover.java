package geneticOperators;

import population.Individual;
import population.Population;

/**
 * All types of crossover should inherit from this class
 * 
 * @author helen
 *
 * @param <T> The type used to represent the individuals.
 */
public abstract class Crossover<T> 
{
	int probabilityOfCrossover;
	
	/**
	 * 
	 * @param selectedIndividuals A list of pairs of individuals that will be crossed over.
	 * @return The population after the crossovers have been performed
	 */
	public abstract Population<T> performCrossover(Individual<T>[][] selectedIndividuals);
	
	/////////////////////////
	// setters and getters //
	
	public int getProbabilityOfCrossover() 
	{
		return probabilityOfCrossover;
	}
	
	public void setProbabilityOfCrossover(int probabilityOfCrossover) 
	{
		this.probabilityOfCrossover = probabilityOfCrossover;
	}

}
