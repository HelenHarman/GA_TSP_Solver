import java.util.Scanner;

/**
 * Allows the user to select the probabilities and algorithms used.
 * @author helen
 *
 */
public class UserInteraction 
{
	private int numberOfNodes;
	private int populationSize;
	
	private boolean useBinary;
	private boolean useMultiReservedStrategy;
	
	private int probabilityOfCrossover;
	private int probabilityOfMutation;
	
	private int reservePercentage, keptFitnessThreshold, notKeptFitnessThreshold;
	private double keptDistanceThreshold;
	
	/**
	 * 
	 * @return false : run the default experiments; true : use user selected options
	 */
	public boolean getUserInput()
	{
		@SuppressWarnings("resource") // don't close System.in
		Scanner keyboard = new Scanner(System.in);
		
		System.out.print("Would you like to select options (y or n)? ");
	
		String in = keyboard.nextLine();
		if(in.equals("n"))
		{
			return false;
		}
		
		System.out.print("Number of nodes/cities : ");
		numberOfNodes = keyboard.nextInt();
		
		System.out.print("Population size : ");
		populationSize = keyboard.nextInt();
		
		
		System.out.println("Select encoding of inputs : ");
		System.out.println("   1. Binary representation");
		System.out.println("   2. Path representation");
		int inInt = keyboard.nextInt();
		if(inInt == 1)
		{
			useBinary = true;
		}
		else
		{
			useBinary = false;
		}
		
		System.out.print("Use multi reservered strategy (y or n)?");
		in = keyboard.next();
		if(in.equals("n"))
		{
			useMultiReservedStrategy = false;
		}
		else
		{
			useMultiReservedStrategy = true;
			System.out.println("Multi-reserved Strategy settings : ");
			System.out.print(" Reserve population percentage (e.g. 10) : ");
			reservePercentage = keyboard.nextInt();
			
			System.out.println(" Reserved population ...");
			System.out.print("  keep individuals with (fitness(I) - fitness(Best)) <  (e.g. 20) : ");
			keptFitnessThreshold = keyboard.nextInt();
			
			System.out.print("  DON'T keep individuals with (fitness(I) - fitness(Best)) > (e.g. 100) : ");
			notKeptFitnessThreshold = keyboard.nextInt();
			
			System.out.print("  keep individuals who's nearest node, already in the reserved list, is further than (double between 0 and 1) (e.g. 0.5): ");
			keptDistanceThreshold = keyboard.nextDouble();
		}
		
		System.out.print("Probability of crossover ( 1 in (e.g. 10) chance ): ");
		probabilityOfCrossover = keyboard.nextInt();
		
		System.out.print("Probability of mutation ( 1 in (e.g. 500) chance ): ");
		probabilityOfMutation = keyboard.nextInt();
		
		return true;
	}
	
	//////////////
	// getters  //


	public int getNumberOfNodes() 
	{
		return numberOfNodes;
	}


	public int getPopulationSize() 
	{
		return populationSize;
	}


	public boolean isUseBinary() 
	{
		return useBinary;
	}


	public boolean isUseMultiReservedStrategy() 
	{
		return useMultiReservedStrategy;
	}


	public int getProbabilityOfCrossover() 
	{
		return probabilityOfCrossover;
	}


	public int getProbabilityOfMutation() 
	{
		return probabilityOfMutation;
	}


	public int getReservePercentage() 
	{
		return reservePercentage;
	}


	public int getKeptFitnessThreshold() 
	{
		return keptFitnessThreshold;
	}


	public int getNotKeptFitnessThreshold() 
	{
		return notKeptFitnessThreshold;
	}


	public double getKeptDistanceThreshold() 
	{
		return keptDistanceThreshold;
	}
	

}
