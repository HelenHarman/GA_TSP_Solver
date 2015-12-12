package geneticOperators;

import population.Individual;
import population.Population;

/**
 * Used to perform the multi-reserved strategy. 
 * This selects individuals based on their fitness and distance to other individuals.
 * 
 * Based on : http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.567.8784&rep=rep1&type=pdf
 * 
 * @author helen
 *
 * @param <T>
 */
public class MultiReservedStrategy<T> 
{
	private int reservePercentage;
	
	private Population<T> reservedPopulation;
	private Population<T> nonReservedPopultation;
	
	private int keptFitnessThreshold;
	private int notKeptFitnessThreshold;
	private double keptDistanceThreshold; // max distance is 1
	
	/**
	 * Constructor 
	 * 
	 * @param reservePercentage Percentage of the top individuals that will be considered for the reserved set
	 * @param keptFitnessThreshold 
	 * @param notKeptFitnessThreshold
	 * @param keptDistanceThreshold
	 */
	public MultiReservedStrategy(int reservePercentage, int keptFitnessThreshold, int notKeptFitnessThreshold, double keptDistanceThreshold)
	{
		this.reservePercentage = reservePercentage;
		this.keptFitnessThreshold = keptFitnessThreshold;
		this.notKeptFitnessThreshold = notKeptFitnessThreshold;
		this.keptDistanceThreshold = keptDistanceThreshold;
	}
	
	/**
	 * Creates the reserved and non-reserved lists
	 * @param population Contains the list of individuals, in order of fitness
	 */
	public void calculateReservedPopulation(Population<T> population)
	{
		int populationSize = population.getIndividuals().size();
		int reservedPopulationSize = (populationSize / 100)*reservePercentage;
		
		this.reservedPopulation = new Population<T>();
		this.nonReservedPopultation = new Population<T>();
		population.resetIterator();
			
		Individual<T> fitestIndividual = population.next();
		this.reservedPopulation.addIndividual(fitestIndividual);
		
		// create the reserved population
		for (int i = 1; i < reservedPopulationSize; i++)
		{
			Individual<T> individual = population.next();
			if((fitestIndividual.getFitness() - individual.getFitness()) < this.keptFitnessThreshold)
			{
				this.reservedPopulation.addIndividual(individual);
			}
			else if ((fitestIndividual.getFitness() - individual.getFitness()) > this.notKeptFitnessThreshold)
			{
				this.nonReservedPopultation.addIndividual(individual);
			}
			else if(this.getSmallestDistanceToIndividual(this.reservedPopulation, individual) > this.keptDistanceThreshold)
			{
				this.reservedPopulation.addIndividual(individual);
			}
			else
			{
				this.nonReservedPopultation.addIndividual(individual);
			}
		}
		
		// add the rest of the population to the nonReservedPopultation list
		while(population.hasNext())
		{
			this.nonReservedPopultation.addIndividual(population.next());
		}
		
	}
	
	/**
	 * Only individuals that are far away from those already in the reserved list should be added to the reserved list
	 * @param population
	 * @param individual
	 * @return distance between 0 and 1.
	 */
	private double getSmallestDistanceToIndividual(Population<T> population, Individual<T> individual)
	{
		double smallestDistance = 0;
		population.resetIterator();
		while(population.hasNext())
		{
			double distance = population.next().getDistanceTo(individual);
			if (distance < smallestDistance)
			{
				smallestDistance = distance;
			}
		}
		return smallestDistance;
	}
	
	////////////////
	/// getters  //

	public Population<T> getReservedPopulation() {
		return reservedPopulation;
	}

	public Population<T> getNonReservedPopultation() {
		return nonReservedPopultation;
	}
}
