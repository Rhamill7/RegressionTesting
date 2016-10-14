import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
	static HashMap<int[], Integer> key;

	public Population() {
	}
	
	public Integer getKey(int[] order){
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
		
		Chromosome.setData(fileScanner(),totalTests, faultNumber);

		
		for (int i = 0; i < populationSize; i++) {
			pArr.add(Chromosome.generateRandom(geneLength));
		}
	//	System.out.println(pArr);

		p = new Chromosome[pArr.size()];
		p = pArr.toArray(p);
		/* Sort in order of fitness */
		Arrays.sort(p);
	}
	
	private static ArrayList<int[]> fileScanner() {
		ArrayList<int[]> pool = new ArrayList<int[]>();
		
		key = new HashMap<int[], Integer>();
		File file = new File("nanoxmltestfaultmatrix.txt");
		String title = "";
		int val;
		try {
			Scanner scan = new Scanner(file);
			for (int j = 0; j < totalTests; j++) {
				int[] vals = new int[faultNumber];
				while (!title.equals("unitest" + j + ":") && scan.hasNext()) {
					title = scan.nextLine();
				}
				for (int i = 0; i < faultNumber - 1; i++) {
					scan.nextLine();
					val = scan.nextInt();
					vals[i] = val;
					scan.nextLine();
				}
			pool.add(vals);
			key.put(vals, j);
			
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error! NanoXML test faultmatrix file not found!");
		}
		for (int i = 0; i < faultNumber; i++) {
			// System.out.print(vals[i] + " ");
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
		Chromosome[] parents = new Chromosome[2];
		// Randomly select two parents via tournament selection.
		for (int i = 0; i < 2; i++) {
			parents[i] = p[rand.nextInt(p.length)]; // get random parent
			for (int j = 0; j < tournamentSize; j++) { // compare with others
				int index = rand.nextInt(p.length);
				if (p[index].compareTo(parents[i]) < 0) {
					parents[i] = p[index];
				}
			}
		}
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

}
