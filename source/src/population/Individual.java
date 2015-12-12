package population;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * All types of representing indivduals should inherit from this class
 * 
 * @author helen
 *
 * @param <T> The type used to represent the individuals.
 */
public abstract class Individual<T> implements Comparator<Individual<T>>
{
	private int fitness;
	static List<Integer> initialList; // static for optimization, 
	  								  // don't need to re-create on it all new instances of this class.
	public static int bitSetLength;
    List<Integer> individual; // list of cities in the order the are visited
	
    /**
     * 
     * @param numberOfNodes
     * @param order
     */
	public abstract void create(int numberOfNodes, BigInteger order);
	
	/**
	 * Used in initial random population generation
	 * @return
	 */
	public abstract T getEncodedIndividual();
	
	/**
	 * Used by the Multi-Resered Strategy
	 * @param individual
	 * @return The distance between this individual and the individual pass as a parameter.
	 */
	public abstract double getDistanceTo(Individual<T> individual);
	
	/**
	 * Setter
	 * @param encodedIndividual
	 */
	public abstract void setEncodedIndividual(T encodedIndividual);
	
	
	/**
	 * Populates the initialList of cites. (With for example 1,2,3,4...)
	 * @param numberOfNodes
	 */
	void generateInitialList(int numberOfNodes)
	{
		if (Individual.initialList == null)
		{
			bitSetLength = Individual.getFactorial(numberOfNodes).bitLength();
			Individual.initialList = new ArrayList<Integer>();
			for(int i = 0; i < numberOfNodes; i++)
			{
				Individual.initialList.add(i);
			}
		}
	}
	
	
	/**
	 * Based on : http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.323.9947&rep=rep1&type=pdf
	 * @param binaryTour
	 */
    void produceDencodedIndividual(BigInteger binaryTour)
	{
		List<Integer> placesToVisit = new ArrayList<Integer>(Individual.initialList);
		int numberOfNodes  = Individual.initialList.size();
		this.individual = new ArrayList<Integer>();
		for (int i = 1; i < numberOfNodes; i++)
		{
			Integer node = placesToVisit.get((binaryTour.divide(Individual.getFactorial(numberOfNodes - i)).intValue()));
			placesToVisit.remove(node); 
			individual.add(node);
			binaryTour = binaryTour.mod(Individual.getFactorial(numberOfNodes - i));
		}
		individual.add(placesToVisit.get(0));
	}

    /**
     * 
     * @return
     */
	public int getFitness() {
		return fitness;
	}
	
	public void setFitness(int fitness) {
		this.fitness = fitness;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Integer> getDecodedIndividual() 
	{
		return individual;
	}
	
	@Override
	public int hashCode()
	{
		return individual.hashCode();
	}
	
	/**
	 * A binary sort is used to order the individuals therefore the individual needs to be a Comparator
	 */
	public int compare(Individual<T> individual1, Individual<T> individual2) 
	{
		if (individual1.getFitness() < individual2.getFitness()) return -1;
        if (individual1.getFitness() > individual2.getFitness()) return 1;
        return 0;
	}
	
	/**
	 * 
	 * @param value
	 * @return Factorial of the value
	 */
	public static BigInteger getFactorial(int value)
	{ // bad OO but I dislike repeated code even more, and to have extra class for one function seemed overkill.
		BigInteger factor = BigInteger.ONE;
        for (int i = 1; i <= value; i++) 
        {
        	factor = factor.multiply(BigInteger.valueOf(i));
        }
        return factor;
	}
}
