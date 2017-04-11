import java.util.Random;


/**
 * 
 * @author Brandon Chambers and Brendan Crawford
 *
 *         contains a list of characters from the alphabet set, representing a
 *         string in your world
 */
public class Genome
{
	String firstHalf;
	String secondHalf;
	protected Double mutationRate;// = 0.05;
	String DNA;
	static final int targetLength = Population.getTarget().length();
	static final char[] SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ -'".toCharArray();
	//Genome gene;
	static Random random = new Random(System.currentTimeMillis());

	// a constructor that initializes a Genome with value 'A' and assigns the
	// internal mutation rate
	// a double between 0 and 1.
	public Genome(double mutationRate)
	{
		this.mutationRate = mutationRate;
		DNA = "A";
	}

	// a copy constructor that initializes a Genome with the same values as the
	// input gene.
	public Genome(Genome gene)
	{
		// gene = new Genome(mutationRate);
		mutationRate = gene.mutationRate;
		DNA = gene.DNA;
	}

	// mutates the string in this Genome using the following rules:
	// --with mutationRate chance add a randomly selected character to a
	// randomly selected position in the string.
	// --with mutationRate chance delete a single character from a randomly
	// selected position of the string but do
	// this only if the string has length of at least 2
	// --for each character in the string:
	// --with mutationRate chance the character is replaced by a randomly
	// selected character
	void mutate()
	{
		// this randomly picks a character from our set of possible characters
		char randChar;
		// choose a random index of our current DNA String to mutate
		int randIndex;

		// with mutationRate chance, add random character to random index in
		// string
		if (random.nextDouble() <= mutationRate)
		{
			randIndex = random.nextInt(DNA.length()+1);
			randChar = SET[random.nextInt(SET.length)];
			firstHalf = DNA.substring(0, randIndex);
			secondHalf = DNA.substring(randIndex, DNA.length());
			DNA = (firstHalf + randChar + secondHalf);
			//System.out.println("DNA after mutate version 1  ---   " + DNA);
		}
		// with mutationRate chance, delete a single character from random
		// index, only if
		// string has length at least of 2
		if (random.nextDouble() <= mutationRate && DNA.length() > 2)
		{
			randIndex = random.nextInt(DNA.length());
			StringBuilder sb = new StringBuilder(DNA);
			sb.delete(randIndex, randIndex + 1);
			DNA = sb.toString();
			//System.out.println("DNA after mutate version 2   ---   " + DNA);
		}

		// for each character in string: with mutationRate chance, each char is
		// replaced
		// by a randomly selected character
//		StringBuilder sb = new StringBuilder(DNA);
//		for (int i = 0; i < sb.length(); i++)
//		{
//			if (random.nextDouble() <= mutationRate)
//			{
//				randChar = SET[random.nextInt(SET.length)];
//				sb.replace(i, i + 1, Character.toString(randChar));
//				System.out.println("DNA after mutate version 3   ---   " + DNA);
//			}
//		}
//
//		// add all mutations to DNA for current genome, gene is now
//		// mutated...??hopefully
//		DNA = sb.toString();
		if(random.nextDouble()<=mutationRate && DNA.length() > 0)
		{
			StringBuilder sb = new StringBuilder(DNA);
			randIndex=random.nextInt(DNA.length());
			randChar=SET[random.nextInt(SET.length)];
			sb.replace(randIndex, randIndex+1, Character.toString(randChar));
			DNA = sb.toString();
		}
	}

	// will update the current Genome by crossing it over with 'other'
	void crossover(Genome other)
	{
		String tmpDNA="";
		boolean temp;
		
		int maxSize;
		if (DNA.length() > other.DNA.length())
		{
			maxSize = DNA.length() - 1;
		}
		else
		{
			maxSize = other.DNA.length() - 1;
		}
		
		for (int i = 0; i < maxSize; i++)
		{
			try
			{
				temp = random.nextBoolean();
				if (temp)
				{
					tmpDNA+= DNA.charAt(i);
				}
				else
				{
					tmpDNA+=other.DNA.charAt(i);
				}
			}
			catch (Exception StringIndexOutOfBoundsException)
			{
				break;
			}
		}
		
		this.DNA+=tmpDNA;
		//System.out.println("Inside of crossover() -- DNA AFTER the crossover -- "+DNA);
		//Population.geneList.add(offspring);

	}

	// returns the fitness of the Genome calculated using the following
	// algorithm:
	/*
	 * --- Let n be the length of the current string. Let m be the length of the
	 * target string. --- Let x be the max( n, m ). --- Let f be initialized to
	 * |m - n| --- For each character position add one to f if the character 1
	 * <= i <= x in the current string is different from the character in the
	 * target string (or if one of the two characters does not exist). Otherwise
	 * add nothing to f . --- Return f .
	 */
	public Integer fitness()
	{
		Integer n = DNA.length();
		Integer m = targetLength;
		Integer fitness = Math.abs(m - n);

		for (int i = 0; i < n && i < m; i++)
		{
			if (DNA.charAt(i) != Population.getTarget().charAt(i))
			{
				//System.out.println("inside fitness loop" + fitness);
				fitness++;
			}
		}
		return fitness;
	}

	// (OPTIONAL)
	/*
	 * Integer fitness()
	 * ---------------------------------------------------------- instead of the
	 * algorithm above use the WagnerFischer algorithm for calculating
	 * Levenshtein edit distance :
	 * 
	 * Let n be the length of the current string. Let m be the length of the
	 * target string.
	 * 
	 * Create an (n + 1) x (m + 1) matrix D initialized with 0s.
	 * 
	 * Fill the first row of the matrix with the column indices and fill the
	 * first column of the matrix with the row indices.
	 * 
	 * Implement this nested loop to fill in the rest of the matrix.
	 * 
	 * for row from 1 to n for column from 1 to m if ( current [ row - 1 ] ==
	 * target [ column - 1 ]) D [ i , j ] = D [ i - 1 , j - 1] else D [ i , j ]
	 * = min ( D [ i - 1 , j ]+ 1 , D [ i , j - 1 ]+ 1 , D [ i - 1 , j - 1 ]+ 1)
	 * 
	 * Return the value stored in D[n,m] + (abs(n - m)+ 1) / 2 . (Use integer
	 * arithmetic.)
	 */

	// will display the Genome's character string and fitness in an easy to read
	// format
	public String toString()
	{
		return "(\"" + DNA + "\", "  + Population.mostFit.fitness()+ ")";
	}

}
