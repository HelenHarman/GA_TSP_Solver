package population;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates a random population
 * @author helen
 *
 */
public class RandomPopulationGenerator
{
	private int numberOfNodes;
	private int populationSize;
	private List<BigInteger> randomValuesForPopulation;
	
	/**
	 * 
	 * @param numberOfNodes The number of cities
	 * @param populationSize
	 */
	public RandomPopulationGenerator(int numberOfNodes, int populationSize)
	{
		this.numberOfNodes = numberOfNodes;
		this.populationSize = populationSize;
		
		randomValuesForPopulation = new ArrayList<BigInteger>();
	}
	
	/**
	 * Creates a random list of number which will become the population
	 */
	private void generateRandomValuesForPopulation()
	{
		//System.out.println("generateRandomValuesForPopulation");
		BigInteger factor = Individual.getFactorial(numberOfNodes);
		Random rand = new Random();	
		for (int i = 0; i < populationSize; i++)
		{
			BigInteger bigRandomInt = new BigInteger(factor.bitLength(), rand);

			while(bigRandomInt.compareTo(factor) >= 0)
			{
				bigRandomInt = new BigInteger(factor.bitLength(), rand);
			}
			randomValuesForPopulation.add(bigRandomInt);
		}
	}
	
	/**
	 * Creates the population using the random number produced by generateRandomValuesForPopulation().
	 * (Having the random generation of number in a separate function allows the same initial population 
	 *  to be used across multiple experiment.)
	 * @param classIndividual
	 * @return
	 */
	public <T, Y extends Individual<T>> Population<T> createPopulation(Class<Y> classIndividual)
	{
		Population<T> population = new Population<T>();
		
		if(randomValuesForPopulation.isEmpty())
		{
			generateRandomValuesForPopulation();
		}
		
		for (int i = 0; i < populationSize; i++)
		{
			try 
			{	
				Individual<T> individual = (Individual<T>) classIndividual.newInstance();
				individual.create(this.numberOfNodes, randomValuesForPopulation.get(i));
	
				population.addIndividual(individual);
			} // catch everything that might go wrong when using factory pattern.
			catch (InstantiationException e) 
			{
				e.printStackTrace();
			} 
			catch (IllegalAccessException e) 
			{
				e.printStackTrace();
			}
		}
		return population;
	}

	
	//////////////////////////////
	//   getters and setters    // 

	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	public void setNumberOfNodes(int numberOfNodes) {
		this.numberOfNodes = numberOfNodes;
	}

	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

}
