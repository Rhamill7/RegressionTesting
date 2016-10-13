import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Chromosome implements Comparable<Chromosome> {

	/* What we are aiming for */
	public static Random rand = new Random();
	private static ArrayList<int[]> gene;
	private static ArrayList<int[]> pool;
	private float fitness;
	private static int totalTests;
	private static int faultNumber;

	public Chromosome(ArrayList<int[]> gene) {
		this.gene = gene;
		this.fitness = calculateFitness(gene);
	}

	public ArrayList<int[]> getGene() {
		return gene;
	}

	public float getFitness() {
		return fitness;
	}
	
	public static void setData(ArrayList<int[]> allTests, int tTests, int fNumber) {
		pool=allTests;
		totalTests = tTests;
		faultNumber = fNumber;
	}

	public static Chromosome generateRandom(int geneLength ) {
		ArrayList<int[]> gene = new ArrayList<int[]>();
		for (int j = 0; j < geneLength; j++) {
			gene.add(pool.get(rand.nextInt(pool.size()-1)));
		}
		return new Chromosome(gene);
	}

	

	/* calculate fitness using fitness function */
	public float calculateFitness(List<int[]> gene) {
		int suiteTotal = 0;
		boolean fails = false;
		for (int i = 0; i < faultNumber; i++) {
			for (int j = 0; j < gene.size(); j++) {
				if (gene.get(j)[i] == 1) {
					suiteTotal += (j + 1);
					fails = true;
					break;
				}

			}
			if (!fails) {
				suiteTotal += (totalTests + 1);
			}
			fails = false;
		}

		fitness = 1
				- (((float) suiteTotal / ((float) faultNumber * (float) totalTests)) + (float) (1 / (2 * totalTests)));
		// System.out.println(fitness);
		return fitness;
	}

	/* Crossover Performed here */
	public Chromosome[] crossover(Chromosome mate) {

		ArrayList<int[]> geneArray1 = gene;
		ArrayList<int[]> geneArray2 = mate.gene;

		/* Variable to modify split point */
		int splitPoint = rand.nextInt(geneArray1.size());

		ArrayList<int[]> child1 = new ArrayList<int[]>(geneArray1.subList(0, splitPoint));
		// System.arraycopy(geneArray1, 0, child1, 0, splitPoint);
		// get other half of 2nd array but do not use duplicates

		for (int i = splitPoint; i < geneArray1.size(); i++) {
			if (!(geneArray1.get(i).equals(geneArray2.get(i)))) {
				child1.add(geneArray2.get(i));
			} else {
				child1.add(geneArray1.get(i));
			}
		}

		ArrayList<int[]> child2 = new ArrayList<int[]>(geneArray2.subList(0, splitPoint));
		for (int i = splitPoint; i < geneArray1.size(); i++) {
			if (!(geneArray1.get(i).equals(geneArray2.get(i)))) {
				child2.add(geneArray1.get(i));
			} else {
				child2.add(geneArray2.get(i));
			}
		}
		// System.out.println(child1);

		return new Chromosome[] { new Chromosome(child1), new Chromosome(child2) };
	}

	/* mutation performed here */
	public Chromosome mutate() {
		// char[] geneChars = gene.toCharArray();
		int one = rand.nextInt(gene.size() - 1);
		int two = 0;
		if (one == gene.size()) {
			two = one - 1;
		} else {
			two = one + 1;
		}
		int[] mu1 = gene.get(one);
		int[] mu2 = gene.get(two);
		gene.set(one, mu2);
		gene.set(two, mu1);

		return new Chromosome(gene);
	}

	// Compare Method for comparing fitness
	@Override
	public int compareTo(Chromosome gene) {
		if (fitness > gene.fitness) {
			return -1;
		} else if (fitness < gene.fitness) {
			return 1;
		}
		return 0;
	}

}
