package population;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Contains the population for a single generation.
 * 
 * @author helen
 *
 * @param <T>
 */
public class Population<T> implements Iterator<Individual<T>>
{
	private List<Individual<T>> individuals;
	private int nextGeneIndex;
	
	public Population()
	{
		this.nextGeneIndex = 0;
		this.individuals = new ArrayList<Individual<T>>();
	}
	

	

	/**
	 * During initial experiments all individuals were becoming identical,
	 * this function will prevent this from happening.
	 */
	public void handleNonUniqueIndividuals()
	{
		List<Individual<T>> nonUniqueIndividual = new ArrayList<Individual<T>>();
		for(int i = 0; i < individuals.size(); i++)
		{
			for(int j = i+1; j < individuals.size(); j++)
			{
				if(individuals.get(i).equals(individuals.get(j)))
				{
					nonUniqueIndividual.add(individuals.get(i));
					break;
				}
			}
		}
		
		for(int i = 0; i < nonUniqueIndividual.size(); i++)
		{
			individuals.remove(nonUniqueIndividual.get(i));
		}
		
	}
	
	///////////////////////
	// Iterator methods //
	
	public void resetIterator()
	{
		this.nextGeneIndex = 0;
	}
	
	@Override
	public boolean hasNext() 
	{
		if ((individuals.isEmpty()) || (nextGeneIndex == individuals.size()))
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	@Override
	public Individual<T> next() 
	{
		if (this.hasNext())
		{
			this.nextGeneIndex++;
			return individuals.get(nextGeneIndex-1);
		}
		else
		{
			return null;
		}
	}
	
	///////////////////////////////
	///// getters and setters /////

	public List<Individual<T>> getIndividuals() 
	{
		return individuals;
	}

	public void setIndividuals(List<Individual<T>> individuals) 
	{
		this.individuals = individuals;
	}
	
	public void addAllIndividuals(List<Individual<T>> individuals)
	{
		this.individuals.addAll(individuals);
	}
	
	public void addAllIndividuals(Population<T> population)
	{
		this.addAllIndividuals(population.getIndividuals());
	}
	
	public void addIndividual(Individual<T> individual)
	{
		this.individuals.add(individual);
	}

}
