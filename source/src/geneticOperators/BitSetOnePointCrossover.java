package geneticOperators;

import java.math.BigInteger;
import java.util.Random;

import population.BinaryIndividual;
import population.Individual;
import population.Population;

/**
 * Performs one point crossover on the population
 * Based on :
 * http://www.mitpressjournals.org/doi/pdf/10.1162/evco.1998.6.3.231 (section 4)
 * @author helen
 *
 */
public class BitSetOnePointCrossover extends Crossover<BigInteger>
{
	/**
	 * Empty constructor to allow the factor pattern to work. (class.newInstance())
	 */
	public BitSetOnePointCrossover(){/* empty constructor */}
	
	/**
	 * 
	 * @param probabilityOfCrossover
	 */
	public BitSetOnePointCrossover(int probabilityOfCrossover)
	{
		this.probabilityOfCrossover = probabilityOfCrossover;
	}
	
	
	@Override
	public Population<BigInteger> performCrossover(Individual<BigInteger>[][] selectedIndividuals) 
	{
		Random rand = new Random();
		Population<BigInteger> population = new Population<BigInteger>();
		
		for (int i = 0; i < selectedIndividuals.length; i++)
		{
			int crossoverIndex = rand.nextInt(Individual.bitSetLength * this.probabilityOfCrossover)+1;
			if (crossoverIndex < Individual.bitSetLength)
			{
				System.out.println("crossover " + i + " at : " + crossoverIndex);
				BigInteger bitSet1 = selectedIndividuals[i][0].getEncodedIndividual();
				BigInteger bitSet2 = selectedIndividuals[i][1].getEncodedIndividual();
				for(int j = 0; j < crossoverIndex; j++) // crossover each bit
				{
					// crossover bitSet1 with bitSet2
					if(bitSet1.testBit(j) != selectedIndividuals[i][1].getEncodedIndividual().testBit(j))
					{
						if(selectedIndividuals[i][1].getEncodedIndividual().testBit(j))
						{ // set bit to 1
							bitSet1 = bitSet1.setBit(j);
						}
						else
						{// set bit to 0
							bitSet1 = bitSet1.clearBit(j);
						}
					}
					// crossover bitSet2 with bitSet1
					if(bitSet2.testBit(j) != selectedIndividuals[i][0].getEncodedIndividual().testBit(j))
					{
						if(selectedIndividuals[i][0].getEncodedIndividual().testBit(j))
						{
							bitSet2 = bitSet2.setBit(j);
						}
						else
						{
							bitSet2 = bitSet2.clearBit(j);
						}
					}
				}
				// check if the individuals produced are possible
				if(BinaryIndividual.isPossible(bitSet1))
				{
					population.addIndividual(new BinaryIndividual(bitSet1));
				}
				else
				{
					population.addIndividual(selectedIndividuals[i][0]);
				}
				
				if(BinaryIndividual.isPossible(bitSet2))
				{
					population.addIndividual(new BinaryIndividual(bitSet2));
				}
				else
				{
					population.addIndividual(selectedIndividuals[i][1]);
				}
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
