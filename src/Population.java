import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Population {

	public List<Chromosome> pArr;
	public Chromosome[] p;
	public int tournamentSize = 3;
	public Random rand = new Random();
	float mutation;
	float crossover;
	static int faultNumber;
	int geneLength;
	static int totalTests;
	static HashMap<Integer, int[]> key;

	public Population() {
	}

	public void createPopulation(int populationSize, float crossoverRatio, float mutationRatio, int faultNumber,
			int geneLength, int totalTests) {

		this.crossover = crossoverRatio;
		this.mutation = mutationRatio;
		this.pArr = new ArrayList<Chromosome>();
		this.p=new Chromosome[populationSize];
		this.faultNumber = faultNumber;
		this.geneLength = geneLength;
		this.totalTests = totalTests;

		ArrayList<Integer> pool = fileScanner();
		Chromosome.setData(pool, key, totalTests, faultNumber);
	//	System.out.println("First array");
		for (int i = 0; i < populationSize; i++) {
			this.p[i]=Chromosome.generateRandom(geneLength);
		}
		//p = pArr.toArray();
	//	System.out.println("SecondArray" );
//		for ( int k = 0; k<500; k++){
//			System.out.println(p[k].getGene())	;
//			}
	//	 p.toArray();
		/* Sort in order of fitness */
		Arrays.sort(p);
		//System.exit(0);
	}

	private ArrayList<Integer> fileScanner() {
		ArrayList<Integer> pool = new ArrayList<Integer>();
		key = new HashMap<Integer, int[]>();
		// "fault-matrix-1000.dat"); //"nanoxmltestfaultmatrix.txt");
		String title = "";
		int val;
		try {
			BufferedReader in = new BufferedReader(new FileReader("fault-matrix-1000.dat"));
			for (int j = 0; j < totalTests; j++) {
				ArrayList<Integer> values = new ArrayList<Integer>();
				while (!(title = in.readLine()).contains("unitest")) {

				}
				// System.out.println(title);
				int testNo = Integer.parseInt(title.substring(title.indexOf("unitest") + 7, title.length() - 1));
				// System.out.println(testNo);
				for (int i = 0; i < faultNumber; i++) {
					in.readLine();
					val = Integer.parseInt(in.readLine().trim());
					values.add(val);
				}
				int[] vals = convertIntegers(values);
				// System.out.println(Arrays.toString(vals));
				pool.add(testNo);
				key.put(testNo, vals);

			}
			in.close();

		} catch (IOException e) {
			System.out.println("Error! File not found!");
		}
		return pool;
	}

	/* create copy of current population and return it */
	public Chromosome[] getPopulation() {
		Chromosome[] currentPopulation = new Chromosome[p.length];
		System.arraycopy(p, 0, currentPopulation, 0, p.length);
		return currentPopulation;
	}

	public void evolve() {
		Chromosome[] pDash = new Chromosome[p.length];

		int index = 0;
		// Perform crossover and mutation if size isnt equal
		while (index < pDash.length) {
			// Check to see if we should perform a crossover.
			if (rand.nextFloat() <= crossover) {
				Chromosome[] parents = selectParents();
				Chromosome[] children = parents[0].crossover(parents[1]);
				
//				System.out.println((parents[0].getGene()));
//				System.out.println((parents[1].getGene()));
//				System.out.println((children[0].getGene()));
//				System.out.println((children[1].getGene()));
//				System.exit(0);
				
				pDash[index++] = children[0];
				// If space in array add 2nd child
				if (index < pDash.length) {
					pDash[index] = children[1];
				}
			} else {
				pDash[index] = p[index];
			}
			index++;
		}

		for (int i = 0; i < pDash.length; i++) {
			if (rand.nextFloat() <= mutation) {
				//System.out.println(pDash[i].getGene());
				pDash[i] = pDash[i].mutate();
			//	System.out.println(pDash[i].getGene());
			}
		}
	//	System.exit(0);
		
		Arrays.sort(pDash); // sort based on fitness
		p = pDash; // P<-P'
	}

	/* Selection done here TOURNAMENT STYLE! FIGHTTT!! */
	private Chromosome[] selectParents() {
		// System.out.println("Made it to here");
		
		
		Chromosome[] parents = new Chromosome[2];
		// Randomly select two parents via tournament selection.
		for (int i = 0; i < 2; i++) {
			//System.out.println(p.length);
//			System.out.println(rand.nextInt(p.length));
			parents[i] = p[rand.nextInt(p.length)]; // get random parent
//			System.out.println("parent before  " + (parents[i].getGene() + " " + parents[i].getFitness()));
			for (int j = 0; j < tournamentSize; j++) { // compare with others
				int index = rand.nextInt(p.length);
//				System.out.println("Second index" + index);
//				System.out.println((p[index].getGene()));
				if ((p[index].compareTo(parents[i]) < 0)) {
					parents[i] = p[index];
				}
			}
//			System.out.println("parent after  " + (parents[i].getGene() + " " + parents[i].getFitness()));
		}
		//System.exit(0);
		return parents;
	}

	/* implements random algorithm */
	public void random() {
		Chromosome[] randomPopulation = new Chromosome[p.length];
		for (int i = 0; i < p.length; i++) {
			randomPopulation[i] = Chromosome.generateRandom(geneLength);
		}
		/* Sort in order of fitness */
		Arrays.sort(randomPopulation);
		p = randomPopulation;
	}

	public static int[] convertIntegers(List<Integer> integers) {
		int[] ret = new int[integers.size()];
		Iterator<Integer> iterator = integers.iterator();
		for (int i = 0; i < ret.length; i++) {
			ret[i] = iterator.next().intValue();
		}
		return ret;
	}

}
