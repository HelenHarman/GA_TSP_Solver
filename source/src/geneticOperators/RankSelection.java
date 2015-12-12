package geneticOperators;

import java.util.List;
import java.util.Random;

import population.Individual;
import population.Population;

/**
 * Performs rank selection as described in :
 * https://books.google.co.uk/books?hl=en&lr=&id=lI17AgAAQBAJ&oi=fnd&pg=PA101&dq=genetic+algorithm+rank+selection+baker+1985&ots=0Kn294S73A&sig=_fECaGvkwe2WC7QUfa7caZPIeP0#v=onepage&q&f=false
 * 
 * @author helen
 *
 * @param <T> The type used to represent the individuals.
 */
public class RankSelection<T> extends Selection<T>
{
	@SuppressWarnings("unchecked") // Due to 2D Array and genetic types being used the selectedIndividuals variable gives unchecked type warnings
	@Override
	public Individual<T>[][] selectIndividuals(Population<T> population, int numberOfSelections) 
	{
		Random rand = new Random();
		List<Individual<T>> individuals = population.getIndividuals();
		int maxRandomNumber = individuals.size() * individuals.size();
		Individual<T>[][] selectedIndividuals = new Individual[numberOfSelections][2];
		
		for(int i = 0; i < numberOfSelections; i++)
		{
			selectedIndividuals[i] = new Individual[2]; 
			//System.out.println("individuals.size()-((int)Math.sqrt(rand.nextInt(maxRandomNumber)))-1 : " + (individuals.size()-((int)Math.sqrt(rand.nextInt(maxRandomNumber)))-1) );
			selectedIndividuals[i][0] = individuals.get(individuals.size()-((int)Math.sqrt(rand.nextInt(maxRandomNumber)))-1);
			selectedIndividuals[i][1] = individuals.get(individuals.size()-((int)Math.sqrt(rand.nextInt(maxRandomNumber)))-1);
		}
		return selectedIndividuals;
	}
}
