package geneticOperators;

import java.util.List;
import java.util.Random;

import population.PathIndividual;
import population.Population;

/**
 * Mutates the order of cities (path representation) by exchanging two cities.
 * @author helen
 *
 */
public class PathMutation extends Mutation<List<Integer>>
{
	/**
	 * Empty constructor to allow the factor pattern to work. (class.newInstance())
	 */
	public PathMutation(){/* empty constructor */}
	
	/**
	 * 
	 * @param probability
	 */
	public PathMutation(int probability)
	{
		this.probability = probability;
	}

	@Override
	public Population<List<Integer>> mutatePopulation(Population<List<Integer>> population) 
	{
		Random rand = new Random();
		population.resetIterator();
		while(population.hasNext())
		{
			PathIndividual pathIndividual = (PathIndividual) population.next();
			int randNumber = rand.nextInt(this.probability * pathIndividual.getDecodedIndividual().size());		
		
			if(randNumber < pathIndividual.getDecodedIndividual().size())
			{
				//System.out.println("before Mutate bit : " + i + " binaryIndividual.getEncodedIndividual() : " +binaryIndividual.getEncodedIndividual().toString());
				int randNumber2 = rand.nextInt(pathIndividual.getDecodedIndividual().size()-1);
				while(randNumber2 == randNumber)
				{
					randNumber2 = rand.nextInt(pathIndividual.getDecodedIndividual().size()-1);
				}
				Integer temp = pathIndividual.getEncodedIndividual().get(randNumber);
				pathIndividual.getEncodedIndividual().set(randNumber, pathIndividual.getEncodedIndividual().get(randNumber2));
				pathIndividual.getEncodedIndividual().set(randNumber2, temp);
			}
			
		}
		return population;
	}
}
