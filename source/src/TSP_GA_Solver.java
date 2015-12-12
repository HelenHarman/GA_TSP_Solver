import java.math.BigInteger;
import java.util.List;

import geneticOperators.BitMutation;
import geneticOperators.Crossover;
import geneticOperators.GeneticOperator;
import geneticOperators.MultiReservedStrategy;
import geneticOperators.Mutation;
import geneticOperators.BitSetOnePointCrossover;
import geneticOperators.OnePointCrossover;
import geneticOperators.PathMutation;
import geneticOperators.RandomSelection;
import geneticOperators.RankSelection;
import geneticOperators.Selection;
import population.BinaryIndividual;
import population.Individual;
import population.PathIndividual;
import population.Population;
import population.RandomPopulationGenerator;

/**
 * Contains the main.
 * 
 * Allows different experiments to be performed, using Genetic Algorithms to solve the Traveling Salesman Problem (TSP)
 * 
 * @author helen
 *
 */
public class TSP_GA_Solver 
{
	private TSP tsp;	
	private int numberOfNodes;
	private int initialPopulationSize;
	private int gridSize;
	
	private int maxGeneration;
	private int generation; // current generation
	
	private final int NUMBER_OF_RUNS = 10;
	
	// Parameters used by the MultiReservedStrategy class
	private int reservePercentage, keptFitnessThreshold, notKeptFitnessThreshold;
	private double keptDistanceThreshold;
	
	// If the number of generations where the fitness has not decreased goes above this the GA will stop.
	private int noFitnessDecreaseStoppingCriteria;
	
	private int iCrossover = 0;
	private int[] crossoverProbabilites = {10};//5,10,15,20}; // 1 in n 
	private int iMutation = 0;
	private int[] mutationProbabilites = {500};//400,450,500,550,600};// 1 in n chance (in the case of binary: 
					// 1 in n chance the 1st bit will be flipped, 1 in n*2 chance the second bit will be flipped, ect.
					//Because the 1st bit has the smallest effect on the order, whereas the Nth bit will have a very large effect)

	/**
	 * Sets the default values
	 */
	public TSP_GA_Solver()
	{
		this.numberOfNodes = 20;//Integer.parseInt(args[0]);
		this.initialPopulationSize = 1000;// Integer.parseInt(args[1]);
		this.gridSize = 200;
		this.maxGeneration = 5000;// Integer.parseInt(args[2]);
		this.generation = 1;
		
		//this.probabilityOfCrossover = 10; 
		//this.probabilityOfMutation = 500;
		
		this.reservePercentage = 10;
		this.keptFitnessThreshold = 20;
		this.notKeptFitnessThreshold = 100;
		this.keptDistanceThreshold = 0.5; // between 0 and 1
		
		this.noFitnessDecreaseStoppingCriteria = 10;
	}
	
	/**
	 * Either runs the GA using the values the user enters,
	 * or runs the experiments.
	 * 
	 * The experiments run the multiple representations and selection algorithms NUMBER_OF_RUNS times
	 */
	public void initialiseSolver()
	{
		// checks if the user wants to enter values or just run the default experiments.
		UserInteraction userInteraction = new UserInteraction();
		boolean useUserInteraction = userInteraction.getUserInput();
		if(useUserInteraction) { grabValuesUserEntered(userInteraction); }
		
		////// Generate the problem ////// 		
		this.tsp = new TSP(this.numberOfNodes, this.gridSize, this.gridSize);
		this.tsp.generateProblem();
		
		// Creates the random values on initialization, so that same values can be used in multiple experiments
		RandomPopulationGenerator randomPopulationGenerator = new RandomPopulationGenerator(this.numberOfNodes, this.initialPopulationSize);
		
		
		if (!useUserInteraction)
		{ // Run experiments using binary or path data; with MultiReservedStrategy (true) or RankSelection (false)
			runBinaryExperiments(randomPopulationGenerator, false);
			runBinaryExperiments(randomPopulationGenerator, true);
			runPathEncodedExperiments(randomPopulationGenerator, false);
			runPathEncodedExperiments(randomPopulationGenerator, true);
		}
		else // Use the information entered by the user
		{ 
			if(userInteraction.isUseBinary())
			{
				Output<BigInteger> ouput = new Output<BigInteger>();
				Fitness<BigInteger> fitness = geneticOperationsSetup(randomPopulationGenerator, BinaryIndividual.class, BitSetOnePointCrossover.class, BitMutation.class, userInteraction.isUseMultiReservedStrategy());
				ouput.handleResults(fitness, this.tsp, this.generation, "Binaryresults");
			}
			else // use ordered list of cities (Path representation)
			{
				Output<List<Integer>> ouput = new Output<List<Integer>>();
				Fitness<List<Integer>> fitness = geneticOperationsSetup(randomPopulationGenerator, PathIndividual.class, OnePointCrossover.class, PathMutation.class, userInteraction.isUseMultiReservedStrategy());
				ouput.handleResults(fitness, this.tsp, this.generation, "PathResults");
			}
		}
	}
	
	/**
	 * Get the GA parameters from the UserInteraction object
	 * @param userInteraction
	 */
	private void grabValuesUserEntered(UserInteraction userInteraction)
	{
		this.numberOfNodes = userInteraction.getNumberOfNodes();
		this.initialPopulationSize = userInteraction.getPopulationSize();
		
		//this.probabilityOfCrossover = userInteraction.getProbabilityOfCrossover();
		crossoverProbabilites[0] = userInteraction.getProbabilityOfCrossover();
		//this.probabilityOfMutation = userInteraction.getProbabilityOfMutation();
		mutationProbabilites[0] = userInteraction.getProbabilityOfMutation();
		
		this.reservePercentage = userInteraction.getReservePercentage();
		this.keptFitnessThreshold = userInteraction.getKeptFitnessThreshold();
		this.notKeptFitnessThreshold = userInteraction.getNotKeptFitnessThreshold();
		this.keptDistanceThreshold = userInteraction.getKeptDistanceThreshold();
	}
	
	/**
	 * Performs the binary representation experiments for each mutation and crossover probability, NUMBER_OF_RUNS times.
	 * 
	 * @param randomPopulationGenerator Contains the values used to generate the same initial population for each run
	 * @param useMultiReservedStrategy true if the multi-reserved strategy is being used. (Else rank selection is used)
	 */
	private void runBinaryExperiments(RandomPopulationGenerator randomPopulationGenerator, boolean useMultiReservedStrategy)
	{
		for (iCrossover = 0; iCrossover < crossoverProbabilites.length; iCrossover++)
		{
			for (iMutation = 0; iMutation < mutationProbabilites.length; iMutation++)
			{
				Output<BigInteger> ouput = new Output<BigInteger>();
				for (int numberOfRuns = 0; numberOfRuns < this.NUMBER_OF_RUNS; numberOfRuns++)
				{
					Fitness<BigInteger> fitness = geneticOperationsSetup(randomPopulationGenerator, BinaryIndividual.class, BitSetOnePointCrossover.class, BitMutation.class, useMultiReservedStrategy);
					ouput.handleResults(fitness, this.tsp, this.generation, "Binary" + useMultiReservedStrategy + Integer.toString(numberOfRuns));
				}
				ouput.writeInformationToFile("Binary" + useMultiReservedStrategy + mutationProbabilites[iMutation] + crossoverProbabilites[iCrossover]);
			}
		}
	}
	
	/**
	 * Performs the path representation experiments for each mutation and crossover probability, NUMBER_OF_RUNS times.
	 * 
	 * @param randomPopulationGenerator Contains the values used to generate the same initial population for each run
	 * @param useMultiReservedStrategy true if the multi-reserved strategy is being used. (Else rank selection is used)
	 */
	private void runPathEncodedExperiments(RandomPopulationGenerator randomPopulationGenerator, boolean useMultiReservedStrategy)
	{
		for (iCrossover =0; iCrossover < crossoverProbabilites.length; iCrossover++)
		{
			for (iMutation =0; iMutation < mutationProbabilites.length; iMutation++)
			{
				Output<List<Integer>> ouput = new Output<List<Integer>>();
				for (int numberOfRuns = 0; numberOfRuns < this.NUMBER_OF_RUNS; numberOfRuns++)
				{
					Fitness<List<Integer>> fitness = geneticOperationsSetup(randomPopulationGenerator, PathIndividual.class, OnePointCrossover.class, PathMutation.class, useMultiReservedStrategy);
					ouput.handleResults(fitness, this.tsp, this.generation, "Path" + useMultiReservedStrategy + Integer.toString(numberOfRuns));
				}
				ouput.writeInformationToFile("Path" + useMultiReservedStrategy + mutationProbabilites[iMutation] + crossoverProbabilites[iCrossover]);
			}
		}
	}
	
	
	/**
	 * Creates instances of the different genetic operators. 
	 * This method has been produced to prevent repeated code.
	 * 
	 * @param randomPopulationGenerator Contains the values used to generate the same initial population for each experiment
	 * @param individualClass 
	 * @param crossoverClass
	 * @param mutationClass
	 * @param useMultiReservedStrategy
	 * @return Fitness<T> which contains the final generation, the fittest individual and a record of the fitness for each generation
	 */
	private <T, IndividualT extends Individual<T>, CrossoverT extends Crossover<T>, MutationT extends Mutation<T>> Fitness<T> geneticOperationsSetup(RandomPopulationGenerator randomPopulationGenerator, Class<IndividualT> individualClass, Class<CrossoverT> crossoverClass, Class<MutationT> mutationClass, boolean useMultiReservedStrategy)
	{
		Selection<T> selection = null;
		Crossover<T> crossover = null;
		Mutation<T> mutation = null;
		MultiReservedStrategy<T> multiReservedStrategy = null;
		this.generation = 0;
		
		////// Get the initial population and fitness function /////
		Population<T> population = randomPopulationGenerator.createPopulation(individualClass);				
		Fitness<T> fitness = new Fitness<T>(tsp);	
		
		////// Set-up Genetic Operators ////// 
		
		if (useMultiReservedStrategy)
		{
			multiReservedStrategy = new MultiReservedStrategy<T>(reservePercentage, keptFitnessThreshold, notKeptFitnessThreshold, keptDistanceThreshold);
			selection = new RandomSelection<T>();
		}
		else { selection = new RankSelection<T>(); }		
		
		try
		{
			crossover = crossoverClass.newInstance();
			crossover.setProbabilityOfCrossover(crossoverProbabilites[iCrossover]);
			
			mutation = mutationClass.newInstance();
			mutation.setProbability(mutationProbabilites[iMutation]);//probabilityOfMutation);
		} 
		catch (InstantiationException e) { e.printStackTrace(); } 
		catch (IllegalAccessException e) { e.printStackTrace(); }
		
		GeneticOperator<T> geneticOperator = new GeneticOperator<T>(selection, crossover, mutation, this.initialPopulationSize, multiReservedStrategy);
			
		////// Run the solver ////// 
		this.runSolver(population, fitness, geneticOperator);
		
		return fitness;
	}
	
	/**
	 * 
	 * @param population
	 * @param fitness
	 * @param geneticOperator
	 */
	public <T> void runSolver(Population<T> population, Fitness<T> fitness, GeneticOperator<T> geneticOperator)
	{
		System.out.println("run solver");
		while(!this.shouldStop(fitness))
		{
			population = fitness.evaluatePopulation(population);
			population = geneticOperator.generateNextGeneration(population);
			this.generation ++;
		}	
		
	}
	
	/**
	 * Checks if the GA has converged.
	 * @param fitness
	 * @return true : stop; false : evaluate a new generation
	 */
	public <T> boolean shouldStop(Fitness<T> fitness)
	{
		if((this.generation >= this.maxGeneration) || this.hasNotDecreased(fitness))
		{
			return true;
		}
		else { return false; }
	}
	
	/**
	 * Used by shouldStop()
	 * @param fitness
	 * @return
	 */
	public <T> boolean hasNotDecreased(Fitness<T> fitness)
	{
		if(fitness.getAverageFitnessOvertime().size() <= this.noFitnessDecreaseStoppingCriteria)
		{
			return false;
		}
		for(int i = fitness.getAverageFitnessOvertime().size()-1; i > fitness.getAverageFitnessOvertime().size() - this.noFitnessDecreaseStoppingCriteria; i--)
		{
			if(fitness.getAverageFitnessOvertime().get(i) < fitness.getAverageFitnessOvertime().get(i-1))
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		TSP_GA_Solver tSP_GA_Solver = new TSP_GA_Solver();
		tSP_GA_Solver.initialiseSolver();
	}
}


//////////////////////////////////////////////////////
//////// Some methods that I used for testing ////////

/*
public void testBinaryIndividual()
{
	BigInteger big = BigInteger.ZERO;
	big = big.setBit(3);
	big = big.setBit(1);
	big = big.setBit(5);
	big = big.setBit(67);
	System.out.println(big.toString());
	System.out.println(big.bitCount());
	System.out.println(big.bitLength());
}

public void testBinaryIndividual()
{
	BitSet bitSet = new BitSet();
	bitSet.set(0);
	bitSet.set(1);
	bitSet.set(2);

	BinaryIndividual.generateInitialList(3);
	
	System.out.println(BinaryIndividual.isPossible(bitSet));
	System.out.println("bitSetToBigInteger : " + BinaryIndividual.bitSetToBigInteger(bitSet).toString());
	
	BinaryIndividual binaryIndividual = new BinaryIndividual(bitSet);
	System.out.println(binaryIndividual.getDecodedIndividual().toString());
	System.out.println(binaryIndividual.getEncodedIndividual().toString());
}*/
