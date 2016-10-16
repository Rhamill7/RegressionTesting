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

	public ArrayList<Chromosome> pArr;
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

	public int[] getKey(Integer order) {
		return key.get(order);
	}

	public void createPopulation(int populationSize, float crossoverRatio, float mutationRatio, int faultNumber,
			int geneLength, int totalTests) {

		this.crossover = crossoverRatio;
		this.mutation = mutationRatio;
		this.pArr = new ArrayList<Chromosome>();
		this.faultNumber = faultNumber;
		this.geneLength = geneLength;
		this.totalTests = totalTests;

		ArrayList<int[]> pool = fileScanner();
		Chromosome.setData(pool, totalTests, faultNumber);

		for (int i = 0; i < populationSize; i++) {
			pArr.add(Chromosome.generateRandom(geneLength));
			// System.out.println(pArr);
		}
		// System.out.println(pArr);

		p = new Chromosome[pArr.size()];
		p = pArr.toArray(p);
		/* Sort in order of fitness */
		Arrays.sort(p);
	}

	private ArrayList<int[]> fileScanner() {
		ArrayList<int[]> pool = new ArrayList<int[]>();
		key = new HashMap<Integer, int[]>();

		// "fault-matrix-1000.dat"); //"nanoxmltestfaultmatrix.txt");
		String title = "";
		int val;
		int unitest = 0;
		int faultnumber = 0;
		try {
			BufferedReader in = new BufferedReader(new FileReader( "fault-matrix-1000.dat"));
			// System.out.println(totalTests);
			for (int j = 0; j < totalTests; j++) {
				ArrayList<Integer> values = new ArrayList<Integer>();
				while (!(title = in.readLine()).contains("unitest")) {
				}
		//		System.out.println(title);
				int testNo = Integer.parseInt(title.substring(title.indexOf("unitest") + 7, title.length() - 1));
			//	System.out.println(testNo);
				// System.out.println("Filescanner is problem"+j);
				for (int i = 0; i < faultNumber; i++) {
					// System.out.println("Hoooo " + i);
					String test = in.readLine();
					// System.out.println("Test" + test);
					String temp = in.readLine().trim();
					// temp = temp.replaceAll("\\s+", "");
					val = Integer.parseInt(temp);
					// val = in.read();
				//	System.out.print(val);
					values.add(val);
					// vals[i] = val;
					// in.readLine();
				}
			//	System.out.println("\n");
				// int[] val2s = values.
				int[] vals = convertIntegers(values);
				pool.add(vals);
				key.put(testNo, vals);

			}
			in.close();

		} catch (IOException e) {
			System.out.println("Error! NanoXML test faultmatrix file not found!");
		}
		//System.out.println(pool.size());
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
				//
				// for (int j=0; j<parents.length; j++){
				// Chromosome test = parents[j];
				// System.out.println("Parent "+ j);
				// for (int i = 0; i <
				// ; i++) {
				// int[] orders = test.get(i);
				//
				// System.out.print(getKey(orders) + " ");
				// }
				// System.out.println("\n");
				// }
				Chromosome[] children = parents[0].crossover(parents[1]);

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
				pDash[i] = pDash[i].mutate();
			}
		}
		Arrays.sort(pDash); // sort based on fitness
		p = pDash; // P<-P'
	}

	/* Selection done here TOURNAMENT STYLE! FIGHTTT!! */
	private Chromosome[] selectParents() {
		// System.out.println("Made it to here");
		Chromosome[] parents = new Chromosome[2];
		// Randomly select two parents via tournament selection.
		for (int i = 0; i < 2; i++) {
			
			parents[i] = p[rand.nextInt(p.length)]; // get random parent
		//	System.out.println( "Before " +parents[i].getFitness());
			for (int j = 0; j < tournamentSize; j++) { // compare with others
				int index = rand.nextInt(p.length);
			//	System.out.println(p[index].getFitness());
				if ((p[index].compareTo(parents[i]) < 0)) {
					parents[i] = p[index];
				}
			}
		//	System.out.println("After " + parents[i].getFitness() + " \n");
		}
	//	System.out.println(parents[0] + " " + parents[0].getFitness());
	//	System.out.println(parents[1] + " " + parents[1].getFitness());
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
