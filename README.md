# Using Genetic Algorithms to Solve the Traveling Salesman Problem

This code has been produced as part of Helen Harman’s (heh14) SEM6120 assignment.

## Contents
- source : The source code produced (eclipse project)
- TSP_GA_Solver.jar : Runnable jar file
- results : Will be created when the program is ran

## Running the project
To run the project use the following command :
	 
	 > java -jar TSP_GA_Solver.jar


## User interface
A command line interface has been provided to allow the different GA representations, selection operators and genetic operator parameters to be selected. 

If you choice not to enter options the following experiments will be ran 10 times :
- binary representation with rank selection
- binary representation with multi-reserved strategy
- path representation with rank selection
- path representation with multi-reserved strategy

(The rest of the parameters used can be seen in source/src/TSP_GA_Solver.java)

The shortest tour each experiment finds will be displayed and stored within the results folder. The graphs (and text files containing the average results) will also be stored in the results folder.



## External Libraries 
This project uses MLPlot which is available from : http://sourceforge.net/projects/mlplot/ and is under the GNU General Public License (GPL).



 
