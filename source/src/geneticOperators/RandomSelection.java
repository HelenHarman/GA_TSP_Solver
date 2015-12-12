package geneticOperators;

import java.util.List;
import java.util.Random;

import population.Individual;
import population.Population;

/**
 * Used when multiReservedStrategy is being used, to create the pairs that will be passed to the crossover operation.
 * 
 * @see MultiReservedStrategy
 * @author helen
 *
 * @param <T> The type used to represent the individuals.
 */
public class RandomSelection<T> extends Selection<T>
{
	@SuppressWarnings("unchecked") // Due to 2D Array and genetic types being used the selectedIndividuals variable gives unchecked type warnings
	@Override
	public Individual<T>[][] selectIndividuals(Population<T> population, int numberOfSelections) 
	{
		Random rand = new Random();
		List<Individual<T>> individuals = population.getIndividuals();
		Individual<T>[][] selectedIndividuals = new Individual[numberOfSelections][2];
		for(int i = 0; i < numberOfSelections; i++)
		{
			selectedIndividuals[i] = new Individual[2]; 
			selectedIndividuals[i][0] = individuals.get(rand.nextInt(individuals.size()));
			selectedIndividuals[i][1] = individuals.get(rand.nextInt(individuals.size()));
		}
		return selectedIndividuals;
	}
}
