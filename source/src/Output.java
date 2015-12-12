import java.awt.Container;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import indiji.mlplot.MLPlot;
import indiji.mlplot.MLPlot.Style;
import indiji.mlplot.MLPlot.Symbol;

/**
 * Allows a solution to the TSP to be shown 
 * Produces the graphs of how well the population performs
 * 
 * @author helen
 *
 */
public class Output<T> extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private final String outputDir = "results/";
	
	int[][] points;
	List<Integer> path;
	PanelComponent panel;
	
	private List<long[]> results;
	private final String[] labels = {"average fitness : ", "least fitest : ", "fitest : ", "Overall fitest : ", "generations : "};
	
	/**
	 * 
	 */
	public Output()
	{
		results = new ArrayList<long[]>();
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		File resultsDirctory = new File(outputDir);
		if(!resultsDirctory.exists())
		{
			resultsDirctory.mkdir();
		}
	}
	
	/**
	 * Shows the shortest found tour and adds the results to the results list.
	 * 
	 * @param fitness The fitness function containing the results overtime
	 * @param tsp The TSP containing the final generation.
	 * @param generations Number of generations GA took to converge
	 * @param id Unique name of experiment. Will be used as the name of the file.
	 */
	public void handleResults(Fitness<T> fitness, TSP tsp, int generations, String id)
	{
		System.out.println("End Results : ");
		tsp.printProblem();
		System.out.println("generations : " + generations);
		System.out.println("getAverageFitnessOvertime : " + fitness.getAverageFitnessOvertime().toString());
		System.out.println("getFitestOvertime : " + fitness.getFittestOvertime().toString());
		System.out.println("getLeastFitestOvertime : " + fitness.getLeastFittestOvertime().toString());
		System.out.println("Best tour : " + fitness.getFitestIndividual().getDecodedIndividual().toString());

	
		this.showsPoints(tsp.getNodes());
		this.showsPath(fitness.getFitestIndividual().getDecodedIndividual());
		this.saveTour(this.outputDir + id);	
		
		this.plotResults(fitness.getFittestOvertime(), fitness.getAverageFitnessOvertime(), fitness.getLeastFittestOvertime(), "Graph" + id);
		
	
		long[] result = new long[labels.length];
		result[0] = fitness.getAverageFitnessOvertime().get(fitness.getAverageFitnessOvertime().size()-1);
		result[1] = fitness.getLeastFittestOvertime().get(fitness.getAverageFitnessOvertime().size()-1);
		result[2] = fitness.getFittestOvertime().get(fitness.getAverageFitnessOvertime().size()-1);
		result[3] = fitness.getFitestIndividual().getFitness();
		result[4] = generations;
		results.add(result);
	}
	
	/**
	 * Save the results of the experiment to a text file
	 * @param fileName
	 */
	public void writeInformationToFile(String fileName)
	{
		PrintWriter writer;
		try {
			writer = new PrintWriter(outputDir + fileName + ".txt", "UTF-8");
			long[] averageResults = new long[results.get(0).length];
						
			// prints list of results for each run
			for(int i = 0; i < results.size(); i++)
			{
				writer.println("Results for run " + i + " : ");
				for(int j = 0; j < results.get(0).length; j++)
				{
					writer.println(labels[j] + (results.get(i))[j]);
					averageResults[j] = averageResults[j] + (results.get(i)[j]);
				}
			}
			
			// prints the average result for the experiment
			writer.println("Average results : ");
			for(int i = 0; i < averageResults.length; i++)
			{
				averageResults[i] = averageResults[i] / results.size();
				writer.println(averageResults[i]);
			}
			writer.close();
		} 
		catch (FileNotFoundException e) { e.printStackTrace(); } 
		catch (UnsupportedEncodingException e) { e.printStackTrace(); }
	}
	
	
	/**
	 * Show the cities/nodes 
	 * @param points An array of the x,y locations of the nodes
	 */
	public void showsPoints(int[][] points)
	{
		this.points = points;
		setSize(500, 400);

		panel = new PanelComponent();
		Container container = getContentPane();
		container.add(panel);	      

		setVisible(true);
		repaint();
	}
	
	/**
	 * A list of the nodes in the order they are visited.
	 * @param path
	 */
	public void showsPath(List<Integer> path)
	{
		this.path = path;
		panel.removeAll();
		repaint();
	}

	/**
	 * Plots the graphs of how the fitness of the population has changed overtime
	 * 
	 * @param results A list of the values to be plotted.
	 * @param name The title of the graph
	 */
	public void plotResults(List<Integer> resultsBest, List<Integer> resultsAverage, List<Integer> resultsWorest, String fileName)
	{
		MLPlot p=new MLPlot();		
		double[] x = new double[resultsBest.size()];
		
		// plot fittest individual
		double[] resultsBestD = new double[resultsBest.size()];
		for(int i = 0; i < resultsBest.size(); i++) 
		{
			resultsBestD[i] = resultsBest.get(i);
			x[i] = i;
		}
		p.linePlot(x, resultsBestD, "black", Style.Solid, Symbol.Circle,"Smallest");
		
		// plot average fitness
		double[] resultsAverageD = new double[resultsAverage.size()];
		for(int i = 0; i < resultsAverage.size(); i++) 
		{
			resultsAverageD[i] = resultsAverage.get(i);
		}
		p.linePlot(x, resultsAverageD, "blue", Style.Solid, Symbol.Circle,"Average");
		
		// plot least fit individual
		double[] resultsWorestD = new double[resultsWorest.size()];
		for(int i = 0; i < resultsWorest.size(); i++) 
		{
			resultsWorestD[i] = resultsWorest.get(i);
		}
		p.linePlot(x, resultsWorestD, "red", Style.Solid, Symbol.Circle,"Biggest");
		
		p.setTitle("Fitness levels for each generation");
		p.setxLabel("Generation");
		p.setyLabel("Fitness");
		p.setLegendPos("NE");
		p.save(new File(outputDir + fileName + ".svg"));
	}
	
	/**
	 * Saves the tour that is displayed in the JPanel
	 * @param filePathName
	 */
	public void saveTour(String filePathName) 
	{
	    BufferedImage image = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);	    
	    panel.paint(image.getGraphics());
	    try 
	    {
			ImageIO.write(image, "png", new File(filePathName + ".png"));
		} 
	    catch (IOException e) { e.printStackTrace(); }
	}
	
	/**
	 * The panel to show the path and points
	 * 
	 * @author helen
	 *
	 */
	public class PanelComponent extends JPanel
	{	
		private static final long serialVersionUID = 1L;
		
		/**
		 * Shows each of the nodes/cities
		 * Draws lines between the cities to show the path followed.
		 */
		public void paintComponent(Graphics graphics)
		{	      
			for (int i = 0; i < points.length; i++)
			{
				graphics.fillOval(points[i][0]-3, points[i][1]-3, 6, 6);
			}
			
			for (int i = 0; i < path.size()-1; i++)
			{
				graphics.drawLine(points[path.get(i)][0], points[path.get(i)][1], points[path.get(i+1)][0], points[path.get(i+1)][1]);
			}
			if(path.size() > 1)
			{
				graphics.drawLine(points[path.get(0)][0], points[path.get(0)][1], points[path.get(path.size()-1)][0], points[path.get(path.size()-1)][1]);
			}
			
			
		}
	}
}