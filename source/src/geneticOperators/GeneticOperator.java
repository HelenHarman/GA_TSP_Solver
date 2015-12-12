package geneticOperators;

import population.Individual;
import population.Population;

/**
 * Holds and calls the genetic operations
 * 
 * @author helen
 *
 * @param <T> The type used to represent the individuals.
 */
public class GeneticOperator<T>
{
	private Selection<T> selection;
	private Crossover<T> crossover;
	private Mutation<T> mutation;
	private MultiReservedStrategy<T> multiReservedStrategy;
	
	private int populationSize;
	
	/**
	 * Constructor
	 * @param selection
	 * @param crossover
	 * @param mutation
	 */
	public GeneticOperator(Selection<T> selection, Crossover<T> crossover, Mutation<T> mutation, int populationSize)
	{
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		
		this.multiReservedStrategy = null;
		
		this.populationSize = populationSize;
	}
	
	/**
	 * Constructor with for using the multi-reserved strategy
	 * @param selection
	 * @param crossover
	 * @param mutation
	 * @param multiReservedStrategy
	 */
	public GeneticOperator(Selection<T> selection, Crossover<T> crossover, Mutation<T> mutation, int populationSize, MultiReservedStrategy<T> multiReservedStrategy)
	{
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		
		this.multiReservedStrategy = multiReservedStrategy;
		
		this.populationSize = populationSize;
	}
	
	/**
	 * Performs the genetic operations
	 * 
	 * @param population The population containing an ordered list of individuals. (Fittest individual first)
	 * @return The new generation
	 */
	public Population<T> generateNextGeneration(Population<T> population)
	{
		if(this.multiReservedStrategy != null)
		{
			population = performGeneticOperationsWithReserveSelection(population);
		}
		else
		{
			population = performGeneticOperations(population, this.populationSize);
		}
		return population;
	}
	
	/**
	 * 
	 * @param population The population containing an ordered list of individuals. (Fittest individual first)
	 * @return The new generation
	 */
	private Population<T> performGeneticOperationsWithReserveSelection(Population<T> population)
	{
		System.out.println("Performing Genetic Operations With Reserve Selection");
		this.multiReservedStrategy.calculateReservedPopulation(population);
		int size = this.populationSize / 2; // half the population will be created using the reserved list
											// the other half will be created using the non reserved list
		Population<T> populationNonReserved = this.performGeneticOperations(this.multiReservedStrategy.getNonReservedPopultation(), size);
		Population<T> populationReserved = this.performGeneticOperations(this.multiReservedStrategy.getReservedPopulation(), size);
		
		population = populationReserved;
		population.addAllIndividuals(populationNonReserved);

		return population;
	}
	
	/**
	 * 
	 * @param population The population containing an ordered list of individuals. (Fittest individual first)
	 * @param requieredPopulationSize
	 * @return
	 */
	private Population<T> performGeneticOperations(Population<T> population, int requieredPopulationSize)
	{
		if (this.crossover != null)
		{
			population = this.performCrossover(population, requieredPopulationSize);
		}
		
		if (this.mutation != null)
		{
			population = this.performMutation(population);
			
		}
		//population.resetIterator();
		//while(population.hasNext()) System.out.println("getDecodedIndividual : " + population.next().getDecodedIndividual().toString());
		population.handleNonUniqueIndividuals();
		return population;
	}
	
	/**
	 * 
	 * @param population The population containing an ordered list of individuals. (Fittest individual first)
	 * @param requieredPopulationSize
	 * @return The population after crossover has been performed
	 */
	private Population<T> performCrossover(Population<T> population, int requieredPopulationSize)
	{
		Individual<T>[][] selectedIndividuals = this.selection.selectIndividuals(population, requieredPopulationSize/2);
		return this.crossover.performCrossover(selectedIndividuals);
	}

	/**
	 * 
	 * @param population The population containing an ordered list of individuals. (Fittest individual first)
	 * @return The population after mutation has been performed
	 */
	private Population<T> performMutation(Population<T> population)
	{
		if (this.mutation != null)
		{
			return mutation.mutatePopulation(population);
		}
		return population;
	}
}
