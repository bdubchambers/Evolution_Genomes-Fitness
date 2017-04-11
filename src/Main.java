/*
You will also provide a Main class for control and testing of your evolutionary algorithm.
void main(String[] args) 
	-this method should instantiate a population and call day()
		until the target string is part of the population.
 	-The target string has fitness zero so the loop should repeat until the most fit
		genome has fitness zero.
 	-After each execution of day() output the most fit genome.
 	-To measure performance output the number of generations (i.e times day() is
		called) and the execution time.
void testGenome() 
	this method tests the Genome class.
void testPopulation() 
	this method tests the Population class.

Include any other methods used to test components of your Genome and Population classes
 */
/**
 * 
 * @author Brandon Chambers and Brendan Crawford
 *
 */
public class Main
{
	// instantiated population -- our initial group of Genomes
	public static Population population;

	public static Genome gene1;// for testGenome()
	public static Genome gene2;// for testGenome()

	public static void main(String[] args)
	{
		// start the stopwatch for running time
		long startTime = System.nanoTime();

		population = new Population(20, 0.05);
		//testPopulation();
		//testGenome();
		breedingCycle();
		
		System.out.println("Generations: "+Population.dayCounter);

		// stop the running time stopwatch and display it at the end of output
		long estimatedTime = System.nanoTime() - startTime;
		System.out.println("Running Time: " + estimatedTime / 1000000
		        + " milliseconds");
	}

	private static void breedingCycle()
	{
		// call day() til the target string is found in population of genomes
		while (Population.mostFit.fitness() > 0)
		{
			population.day();
			if(Population.dayCounter%200==0)
			{
				System.out.println(Population.mostFit);
			}
			
		}
		System.out.println(Population.mostFit);
	}

	static void testGenome()
	{
		gene1 = new Genome(0.05);
		gene2 = new Genome(gene1);
		System.out.println(gene1.fitness());
		gene2.crossover(gene1);
		System.out.println("after crossover   ---   " + gene2);
	}

	static void testPopulation()
	{
		population = new Population(20, 0.05);
		// test output
		// gene1.fitness();
		for (char c : Genome.SET)
			System.out.print(c);

		for (Genome g : Population.geneList)
			System.out.println(g.toString());

		for (Genome g : Population.geneList)
		{
			g.mutate();
		}
		for (Genome g : Population.geneList)
			System.out.println(g);

	}

}
