package geneticOperators;

import java.math.BigInteger;
import java.util.Random;

import population.BinaryIndividual;
import population.Individual;
import population.Population;

/**
 * Mutates the binary representation of a population by flipping bits.
 * @author helen
 *
 */
public class BitMutation extends Mutation<BigInteger>
{
	/**
	 * Empty constructor to allow the factor pattern to work. (class.newInstance())
	 */
	public BitMutation(){/* empty constructor */}
	
	/**
	 * @param probability
	 */
	public BitMutation(int probability)
	{
		this.probability = probability;
	}

	@Override
	public Population<BigInteger> mutatePopulation(Population<BigInteger> population) 
	{
		Random rand = new Random();
		population.resetIterator();
		int numberOfBits = Individual.bitSetLength;
		
		while(population.hasNext())
		{
			BinaryIndividual binaryIndividual = (BinaryIndividual) population.next();
			for(int i = 0; i < numberOfBits; i++)
			{
				if(rand.nextInt(this.probability*(i+1)) == 1)
				{
					binaryIndividual.setEncodedIndividual(binaryIndividual.getEncodedIndividual().flipBit(i));
					if(!BinaryIndividual.isPossible(binaryIndividual.getEncodedIndividual()))
					{
						binaryIndividual.setEncodedIndividual(binaryIndividual.getEncodedIndividual().flipBit(i));
					}
					else
					{
						System.out.println("Mutate bit : " + i);
						binaryIndividual.produceDencodedIndividual();
					}
				}
			}
		}
		return population;
	}
}
