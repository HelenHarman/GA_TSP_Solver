/**
 * 
 */
package population;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to represent a ordered list of cities. (Path representation)
 * 
 * @author helen
 *
 */
public class PathIndividual extends Individual<List<Integer>>
{
	public PathIndividual(){ /*Empty constructor*/ } // Using Factory pattern (class.getInstance()),
	   //  so need empty constructor, and then to call create(..)
	
	/**
	 * Copy constructor
	 * @param pathIndividual
	 */
	public PathIndividual(PathIndividual pathIndividual)
	{
		this.individual = pathIndividual.getDecodedIndividual();
	}
	
	/**
	 * Used by genetic operators to produce new individuals
	 * @param individual
	 */
	public PathIndividual(List<Integer> individual) 
	{
		this.generateInitialList(individual.size());
		
		this.individual = individual;
		changeIfImpossible();
	}
	
	/* (non-Javadoc)
	 * @see population.Individual#create(int, java.math.BigInteger)
	 */
	@Override
	public void create(int numberOfNodes, BigInteger order) 
	{
		this.generateInitialList(numberOfNodes);
		produceDencodedIndividual(order);
	}
	
	/**
	 * Changes any repeated cities to cities missing from the individual.
	 */
	public void changeIfImpossible()
	{
		// find all the cities the individual does not contain
		List<Integer> doesNotContain  = new ArrayList<Integer>(Individual.initialList);
		for(int i = 0; i < individual.size(); i++)
		{
			doesNotContain.remove(individual.get(i));
		}
		if(doesNotContain.isEmpty())
		{
			return;
		}
		
		// change any repeated to a city that the individual does not contain.
		for(int i = 0; i < individual.size(); i++)
		{
			for(int j = i+1; j < individual.size(); j++)
			{
				if(individual.get(i).equals(individual.get(j)))
				{
					individual.set(i, doesNotContain.remove(0));
					
					if(doesNotContain.isEmpty())
					{
						return;
					}
					break;
				}
			}
		}
	}	

	/* (non-Javadoc)
	 * @see population.Individual#getEncodedIndividual()
	 */
	@Override
	public List<Integer> getEncodedIndividual() 
	{
		return individual;
	}


	/* (non-Javadoc)
	 * @see population.Individual#getDistanceTo(population.Individual)
	 */
	@Override
	public double getDistanceTo(Individual<List<Integer>> otherIndividual) 
	{ 
		BigInteger number = BigInteger.ZERO;
		List<Integer> placesToVisit = new ArrayList<Integer>(otherIndividual.getDecodedIndividual());
		
		for (int i = 1; i < this.individual.size(); i++)
		{
			int index = placesToVisit.indexOf(individual.get(i-1));
			number = number.add( Individual.getFactorial(this.individual.size() - i).multiply(BigInteger.valueOf(index) ) );
			placesToVisit.remove(index);
		}
		//System.out.println("BigInt as double : " + number.doubleValue());
		//System.out.println("BigInt as double : " + number.divide(Individual.getFactorial(this.individual.size())).doubleValue());
		return number.divide(Individual.getFactorial(this.individual.size())).doubleValue();
	}

	/* (non-Javadoc)
	 * @see population.Individual#setEncodedIndividual(java.lang.Object)
	 */
	@Override
	public void setEncodedIndividual(List<Integer> encodedIndividual) 
	{
		this.individual = encodedIndividual;
	}
}
