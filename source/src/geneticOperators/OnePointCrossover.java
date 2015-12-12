package geneticOperators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import population.Individual;
import population.PathIndividual;
import population.Population;

/**
 * Performs one point crossover of order lists of cities (Path representation).
 * @author helen
 *
 */
public class OnePointCrossover extends Crossover<List<Integer>>
{
	/**
	 * Empty constructor to allow the factor pattern to work. (class.newInstance())
	 */
	public OnePointCrossover(){/* empty constructor */}
	
	/**
	 * 
	 * @param probabilityOfCrossover
	 */
	public OnePointCrossover(int probabilityOfCrossover)
	{
		this.probabilityOfCrossover = probabilityOfCrossover;
	}
	
	
	@Override
	public Population<List<Integer>> performCrossover(Individual<List<Integer>>[][] selectedIndividuals) 
	{
		Random rand = new Random();
		int length = selectedIndividuals[0][0].getEncodedIndividual().size();
		Population<List<Integer>> population = new Population<List<Integer>>();
		
		for (int i = 0; i < selectedIndividuals.length; i++)
		{
			int crossoverIndex = rand.nextInt(length * this.probabilityOfCrossover)+1;
			if (crossoverIndex < length)
			{
				System.out.println("crossover " + i + " at : " + crossoverIndex);
				List<Integer> individual1 = new ArrayList<Integer>(selectedIndividuals[i][0].getEncodedIndividual());
				List<Integer> individual2 = new ArrayList<Integer>(selectedIndividuals[i][1].getEncodedIndividual());
				for(int j = 0; j < crossoverIndex; j++)
				{
					Integer temp = individual1.get(j);
					individual1.set(j, individual2.get(j));
					individual2.set(j, temp);
				}
				// the PathIndividual constructor will make sure that the solution is possible
				population.addIndividual(new PathIndividual(individual1));
				population.addIndividual(new PathIndividual(individual2));
			}
			else
			{
				// add individuals to population with no crossover
				population.addIndividual(selectedIndividuals[i][0]);
				population.addIndividual(selectedIndividuals[i][1]);
			}
		}
		return population;
	}

}
