import java.util.ArrayList;
import java.util.List;

import population.Individual;
import population.Population;

/**
 * Calculates the fitness of the individuals in the population.
 * Keeps a record of how the fitness of the population has evolved.
 * 
 * @author helen
 *
 * @param <T>
 */
public class Fitness<T>
{
	private TSP tsp;
	private Individual<T> fittestIndividual;
	private List<Integer> fittestOvertime;
	private List<Integer> averageFitnessOvertime;
	private List<Integer> leastFittestOvertime;
		// Could keep list of population and just these as individual values in each Population,
		// 	but that would have a larger unnecessary memory overhead.

	/**
	 * Creates the empty lists for the fittest overtime, average fittest 
	 * @param tsp
	 */
	public Fitness(TSP tsp)
	{
		this.tsp = tsp;
		this.fittestOvertime = new ArrayList<Integer>();
		this.averageFitnessOvertime = new ArrayList<Integer>();
		this.leastFittestOvertime = new ArrayList<Integer>();
	}
	
	/**
	 * Runs the fitness function on each of the individuals in the population
	 * 
	 * @param population 
	 * @return The population containing the Individuals with their fitness values' set; 
	 * 				the individuals are sorted in order of their fitness (best first).
	 */
	public Population<T> evaluatePopulation(Population<T> population)
	{
		System.out.println("evaluatePopulation");
		int averageFitness = 0;
		int currentFittest = Integer.MAX_VALUE;
		int leastFittest = 0;
		population.resetIterator();
		
		List<Individual<T>> sortedIndividuals = new ArrayList<Individual<T>>();   
		
		while(population.hasNext())
		{
			Individual<T> individual = population.next();
			int fitness = this.evaluateIndividual(individual);
			if (fitness < currentFittest) // check if fittest for this generation
			{
				currentFittest = fitness;
				
				// check if individual is the fittest overall generations
				if (fittestIndividual == null)
				{
					fittestIndividual = individual;
				}
				else if (fitness < fittestIndividual.getFitness())
				{
					fittestIndividual = individual;
				}
			}
			else if (fitness > leastFittest)  // check if least fittest for this generation
			{
				leastFittest = fitness;
			}
			averageFitness = averageFitness + fitness;
			     
           
            // insert the individual using binarySearch
            int index = Math.abs(java.util.Collections.binarySearch(sortedIndividuals, individual, individual)+1);
            if (index < 0)
            {
                index = 0;
            }
            if(index > sortedIndividuals.size())
            {
            	sortedIndividuals.add(individual);
            }
            else
            {
            	sortedIndividuals.add(index, individual);
            }
            
		}
		averageFitness = averageFitness / population.getIndividuals().size();
		fittestOvertime.add(currentFittest);
		averageFitnessOvertime.add(averageFitness);
		leastFittestOvertime.add(leastFittest);
		
		population.setIndividuals(sortedIndividuals);
		
		return population;
	}
	
	/**
	 * Calculates the fitness of an individual
	 * 
	 * @param individual The individual to be evaluated
	 * @return The individual's fitness
	 */
	private int evaluateIndividual(Individual<T> individual)
	{
		int distance = 0;
		List<Integer> route = individual.getDecodedIndividual();
		for (int i = 0; i < route.size()-1; i++)
		{
			distance = distance + tsp.getDistance(route.get(i), route.get(i+1));
		}
		distance = distance + tsp.getDistance(route.get(0), route.get(route.size()-1));
		
		individual.setFitness(distance);
		return distance;
	}
	
	////////////////////////////////////////
	//////// Getters and Setters //////////
	
	/**
	 * 
	 * @return
	 */
	public List<Integer> getFittestOvertime() 
	{
		return fittestOvertime;
	}

	/**
	 * 
	 * @param fitestOvertime
	 */
	public void setFittestOvertime(List<Integer> fittestOvertime) 
	{
		this.fittestOvertime = fittestOvertime;
	}

	/**
	 * 
	 * @return
	 */
	public Individual<T> getFitestIndividual() 
	{
		return fittestIndividual;
	}

	/**
	 * 
	 * @param fitestIndividual
	 */
	public void setFittestIndividual(Individual<T> fittestIndividual) 
	{
		this.fittestIndividual = fittestIndividual;
	}

	/**
	 * 
	 * @return
	 */
	public List<Integer> getAverageFitnessOvertime() 
	{
		return averageFitnessOvertime;
	}

	/**
	 * 
	 * @param averageFitnessOvertime
	 */
	public void setAverageFitnessOvertime(List<Integer> averageFitnessOvertime) 
	{
		this.averageFitnessOvertime = averageFitnessOvertime;
	}

	/**
	 * 
	 * @return
	 */
	public List<Integer> getLeastFittestOvertime() 
	{
		return leastFittestOvertime;
	}

	/**
	 * 
	 * @param leastFitestOvertime
	 */
	public void setLeastFittestOvertime(List<Integer> leastFittestOvertime) 
	{
		this.leastFittestOvertime = leastFittestOvertime;
	}
}
