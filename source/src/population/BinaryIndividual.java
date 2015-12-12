package population;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to represent a Binary individual.
 * @author helen
 *
 */
public class BinaryIndividual extends Individual<BigInteger>
{
	private BigInteger encodedIndividual;
	
	public BinaryIndividual(){ /*Empty constructor*/ } // Using Factory pattern (class.getInstance()),
													   //  so need empty constructor, and then to call create(..)
	

	/**
	 * Copy constructor
	 * @param binaryIndividual
	 */
	public BinaryIndividual(BinaryIndividual binaryIndividual)
	{
		this.individual = binaryIndividual.getDecodedIndividual();
		this.encodedIndividual = binaryIndividual.getEncodedIndividual();
	}

	/**
	 * 
	 * @param encodedIndividual
	 */
	public BinaryIndividual(BigInteger encodedIndividual)
	{
		this.encodedIndividual = encodedIndividual;
		this.produceDencodedIndividual(encodedIndividual);
	}
	
	@Override
	public void create(int numberOfNodes, BigInteger order) // Used in initial random population generation
	{
		this.generateInitialList(numberOfNodes);
		this.produceDencodedIndividual(order);
		
		this.encodedIndividual = order;
	}	
	
	
	/**
	 * Used to produce the binary individual from an order list of tours
	 * Based on : http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.323.9947&rep=rep1&type=pdf
	 */
	public void produceEncodedIndividual()
	{ 
		BigInteger binaryTour = BigInteger.ZERO;
		List<Integer> placesToVisit = new ArrayList<Integer>(Individual.initialList);
		
		for (int i = 1; i < this.individual.size(); i++)
		{
			int index = placesToVisit.indexOf(individual.get(i-1));
			binaryTour = binaryTour.add( Individual.getFactorial(this.individual.size() - i).multiply(BigInteger.valueOf(index)));
			placesToVisit.remove(index);
		}
		this.encodedIndividual = binaryTour;
	}
	
	/**
	 * 
	 */
	public void produceDencodedIndividual()
	{
		this.produceDencodedIndividual(this.encodedIndividual);
	}
	

	@Override
	public BigInteger getEncodedIndividual() {
		return this.encodedIndividual;
	}
	
	/**
	 * Setter
	 * @param encodedIndividual
	 */
	public void setEncodedIndividual(BigInteger encodedIndividual) 
	{
		this.encodedIndividual = encodedIndividual;
	}
	
	/**
	 * Tanimoto distance between this individual and another
	 * Adapted from code I have previously written : https://github.com/joergwicker/scavenger/blob/stable/src/main/java/scavenger/demo/Clustering/Distance/Tanimoto.java
	 * @param individual
	 */
	public double getDistanceTo(Individual<BigInteger> individual)
	{ 
		BigInteger value1 = encodedIndividual; 
		BigInteger value2 = individual.getEncodedIndividual();
	           
        double cardinality1 = value1.bitCount();
        double cardinality2 = value2.bitCount();
        
        BigInteger andValue = value1.and(value2);
        double andCardinality = andValue.bitCount();
            
        double distance = (cardinality1 + cardinality2 - andCardinality);
        if (distance != 0)
        {
            distance = andCardinality / distance;
        }
        return 1-distance;
	}

	/**
	 * Checks that the bigInteger is less than the maximum number of possible tours.
	 * @param bigInteger
	 * @return false : not possible; true : possible
	 */
	public static boolean isPossible(BigInteger bigInteger)
	{	
		if (bigInteger.compareTo(Individual.getFactorial(BinaryIndividual.initialList.size())) >= 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
}
