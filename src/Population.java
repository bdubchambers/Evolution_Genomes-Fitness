import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Brandon Chambers and Brendan Crawford
 *
 *         contains a list of Genomes representing all the strings in your world
 */
public class Population
{
	static int dayCounter=0;
	static Random random = new Random();
	// a public data element that is initialized to your name
	public static String target = "BRANDON AND BRENDAN";
	
	public static List<Genome> geneList = new ArrayList<Genome>();

	// a public data element that is equal to the most-fit Genome in the
	// population
	public static Genome mostFit;

	// a constructor that initializes a Population with a number of default
	// genomes
	public Population(Integer numGenomes, Double mutationRate)
	{
		mostFit=new Genome(mutationRate);
		for(int i=0; i<numGenomes;i++)
		{
			//Genome g=new Genome(mostFit);
			geneList.add(new Genome(mostFit));
		}
	}

	// this function is called every breeding cycle and carries out the
	// following steps:
	// update mostFit variable to the most-fit Genome in the population (this is
	// genome with lowest fitness number)
	// delete the least-fit half of the population
	// create new genomes from the remaining population until the number of
	// genomes is restored by doing either of
	// the following with equal chance:
	// 1)pick a remaining genome at random and clone it (with the copy
	// constructor)
	// and mutate the clone. or...
	// 2)pick a remaining genome at random and clone it and then crossover the
	// clone with another remaining
	// genome selected at random and then mutate the result.
	void day()
	{
		dayCounter++;
		Genome clone; //important for evolution process
		int halfOfPopulation=(geneList.size()/2) - 1;//subtract one for the index of removal start
		boolean tmp;
		int numGenesRemoved=0;
		int randIndex;
		int randIndex2;
		
		Collections.sort(geneList, new Comparator<Genome>() {
			   public int compare(Genome g1, Genome g2) {
				   if(g1.fitness()>g2.fitness())
						return 1;
					if(g2.fitness()>g1.fitness())
						return -1;
					else
						return 0;
			   }
			});
		
		//now that the most fit gene is at index 0 in our sorted list, we can update mostFit
		mostFit=geneList.get(0);
		
		//this loop removes the least fit from our population
		if(geneList.size()>1)
		{
			for (int j=halfOfPopulation; j < geneList.size(); j++)
			{
				geneList.remove(j);
				numGenesRemoved++;
			}
		}
		//Now, we create new genomes to replace those that we deleted from geneList (least fit). 
		//flipping a coin, we choose one of two options per iteration:
		for(int n=0; n<numGenesRemoved;n++)
		{
			tmp=random.nextBoolean();//flip the coin per iteration
			randIndex=random.nextInt(geneList.size());//to choose a copy candidate
			randIndex2=random.nextInt(geneList.size());//to choose a copy candidate
			if(tmp)
			{
				clone=new Genome(geneList.get(randIndex));
				clone.mutate();
				geneList.add(clone);
			}
			else
			{
				clone=new Genome(geneList.get(randIndex));
				clone.crossover(geneList.get(randIndex2));
				clone.mutate();
				geneList.add(clone);
			}
		}
	}

	//getter for the target string
	public static String getTarget()
	{
		return target;
	}
}
