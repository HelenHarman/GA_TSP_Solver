import java.util.Random;

/**
 * Generates and holds the randomly placed cities.
 * Calculates the distance between cities
 * 
 * @author helen
 *
 */
public class TSP 
{
	private int numberOfNodes;
	private int maxX;
	private int maxY;
	
	private int[][] nodes;
	
	/**
	 * Constructor
	 * @param numberOfNodes
	 * @param maxX
	 * @param maxY
	 */
	public TSP(int numberOfNodes, int maxX, int maxY)
	{
		this.numberOfNodes = numberOfNodes;
		this.maxX = maxX;
		this.maxY = maxY;
	}	
	
	/**
	 * Randomly generate the cities
	 */
	public void generateProblem()
	{
		Random rand = new Random();
		nodes = new int[this.numberOfNodes][];
		for(int i = 0; i < this.numberOfNodes; i++)
		{
			nodes[i] = new int[2];
			nodes[i][0] = rand.nextInt(maxX);
			nodes[i][1] = rand.nextInt(maxY);
		}
	}
	
	/**
	 * 
	 */
	public void printProblem()
	{
		for(int i = 0; i < this.numberOfNodes; i++)
		{
			System.out.println("i : " + nodes[i][0] + ", " + nodes[i][1]);
		}
	}
	
	/**
	 * Calculate the distance between two cities
	 * @param node1Index
	 * @param node2Index
	 * @return
	 */
	public int getDistance(int node1Index, int node2Index)
	{
		double val = Math.max(nodes[node1Index][0], nodes[node2Index][0]) - Math.min(nodes[node1Index][0], nodes[node2Index][0]);
		val = val * val;
		double distance = Math.max(nodes[node1Index][1], nodes[node2Index][1]) - Math.min(nodes[node1Index][1], nodes[node2Index][1]);
		distance = distance * distance;
		return (int)Math.sqrt(val + distance);
	}
	

	/////////////////////////////////
	///// Getters and Setters //////
	
	/**
	 * 
	 * @return
	 */
	public int getNumberOfNodes() 
	{
		return numberOfNodes;
	}

	/**
	 * 
	 * @param numberOfNodes
	 */
	public void setNumberOfNodes(int numberOfNodes) 
	{
		this.numberOfNodes = numberOfNodes;
	}

	/**
	 * 
	 * @return
	 */
	public int getMaxX() 
	{
		return maxX;
	}

	/**
	 * 
	 * @param maxX
	 */
	public void setMaxX(int maxX) 
	{
		this.maxX = maxX;
	}

	/**
	 * 
	 * @return
	 */
	public int getMaxY() 
	{
		return maxY;
	}

	/**
	 * 
	 * @param maxY
	 */
	public void setMaxY(int maxY) 
	{
		this.maxY = maxY;
	}

	/**
	 * 
	 * @return
	 */
	public int[][] getNodes() 
	{
		return nodes;
	}

	/**
	 * 
	 * @param nodes
	 */
	public void setNodes(int[][] nodes) 
	{
		this.nodes = nodes;
	}
}
