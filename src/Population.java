import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Population {

	public ArrayList<Chromosome> pArr;
	public Chromosome[] p;
	public int tournamentSize = 3;
	public Random rand = new Random();
	float mutation;
	float crossover;

	public Population() {
	}

	public void createPopulation(int populationSize, float crossoverRatio, float mutationRatio, int faultNumber,
			int geneLength) {

		this.crossover = crossoverRatio;
		this.mutation = mutationRatio;
		this.pArr = new ArrayList<Chromosome>(); // [populationSize];

		String read = "";
		int fault = 0, testCase = 0;
		File file = new File("nanoxmltestfaultmatrix.txt");
		int[] tests = new int[faultNumber];
		ArrayList<int[]> pool = new ArrayList<int[]>();

		/*
		 * scan each line until integer is found, add this to array and create
		 * new Unit Tests from array of faults
		 */
		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNext()) {
				if (scan.hasNextInt()) {
					int value = scan.nextInt();
					// System.out.println(value + " " + fault);
					tests[fault] = value;
					// System.out.println(value);
					fault++;
					if (fault == faultNumber) {
						fault = 0;
						pool.add(tests);
						// System.out.println(tests);
					}
				} else {
					read = scan.next();
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Chromosome.setPool(pool);
		for (int i = 0; i < populationSize; i++) {
			pArr.add(Chromosome.generateRandom());
		}
		System.out.println(pArr);

		p = new Chromosome[pArr.size()];
		p = pArr.toArray(p);
		/* Sort in order of fitness */
		// Arrays.sort(p);
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

//
// /* Implements HillClimber */
// public UnitTest hillClimber() {
// UnitTest best = null;
// // start at random point
// int index = rand.nextInt(p.length);
// best = p[index];
// boolean peak = false;
//
// for (int j = 0; j < 10; j++) {
// index = rand.nextInt(p.length);
//
// UnitTest current = p[index];
// UnitTest upper = p[index + 1];
// UnitTest lower = p[index - 1];
//
// while (peak != true) {
//
// if (upper.getFitness() < current.getFitness()) {
// lower = current;
// current = upper;
// upper = p[index + 2];
//
// } else if (lower.getFitness() < current.getFitness()) {
// upper = current;
// current = lower;
// lower = p[index - 2];
// } else {
// peak = true;
// }
//
// }
// // System.out.println(best);
// }
//
// return best;
//
// }
//
	/* implements random algorithm */
	public void random() {
		Chromosome[] randomPopulation = new Chromosome[p.length];
		for (int i = 0; i < p.length; i++) {
			randomPopulation[i] = Chromosome.generateRandom();
		}
		/* Sort in order of fitness */
		Arrays.sort(randomPopulation);
		p = randomPopulation;
	}

}
