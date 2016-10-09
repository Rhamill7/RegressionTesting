import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class UnitTest implements Comparable<UnitTest> {

	/* What we are aiming for */
	public static char[] target = "Hello World!".toCharArray();
	public static Random rand = new Random();
	private static int[] gene;
	private int fitness;

	public UnitTest(int[] gene) {
		this.gene = gene;
		// this.fitness = calculateFitness(gene);
	}

	public int[] getGene() {
		return gene;
	}

	public int getFitness() {
		return fitness;
	}

	public static UnitTest getUnitTests(int test, int faultNumber) {
		String delimiter = "unitest" + test + ":";
		String read = "";
		File file = new File("nanoxmltestfaultmatrix.txt");
		int[] tests = new int[faultNumber];
		try {
			Scanner scan = new Scanner(file);
			scan.useDelimiter(delimiter);
			while (scan.hasNext() && !read.contains("unitest" + test + 1 + ":")) {
				read = scan.next();
				System.out.println(read);
				// tests[i] = testResult;
			}

			scan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new UnitTest(tests);

	}

	// /* Generate random chromosome */
	// static UnitTest generateRandom() {
	// char[] gene = new char[target.length];
	// for (int i = 0; i < gene.length; i++) {
	// gene[i] = (char) (rand.nextInt(90) + 32);
	// }
	// return new UnitTest(String.valueOf(gene));
	// }

	/* calculate fitness using fitness function */
	private static int calculateFitness(int[] gene) {
		int fitness = 0;
		// char[] geneChars = gene.toCharArray();
		// for (int i = 0; i < geneChars.length; i++) {
		// work out how close each letter is by subtracting their ASCII
		// values
		// add the results together, if it is the same this will equal 0.
		// fitness += Math.abs(((int) geneChars[i]) - ((int) target[i]));
		// }
		return fitness;
	}

	// /* Crossover Performed here */
	// public UnitTest[] crossover(UnitTest mate) {
	//
	// char[] geneArray1 = gene.toCharArray();
	// char[] geneArray2 = mate.gene.toCharArray();
	// char[] child1 = new char[gene.length()];
	// char[] child2 = new char[gene.length()];
	//
	// /* Variable to modify split point */
	// // int splitPoint = geneArray1.length/2;
	// int splitPoint = rand.nextInt(geneArray1.length);
	//
	// System.arraycopy(geneArray1, 0, child1, 0, splitPoint);
	// System.arraycopy(geneArray2, splitPoint, child1, splitPoint,
	// (child1.length - splitPoint));
	//
	// System.arraycopy(geneArray2, 0, child2, 0, splitPoint);
	// System.arraycopy(geneArray1, splitPoint, child2, splitPoint,
	// (child2.length - splitPoint));
	//
	// return new UnitTest[] { new UnitTest(String.valueOf(child1)), new
	// UnitTest(String.valueOf(child2)) };
	// }

	/* mutation performed here */
	// public UnitTest mutate() {
	// char[] geneChars = gene.toCharArray();
	// int ranChars = rand.nextInt(geneChars.length);
	// int mutation = (rand.nextInt(90) + 32);
	// geneChars[ranChars] = (char) (mutation);
	//
	// return new UnitTest(String.valueOf(geneChars));
	// }

	// Compare Method for comparing fitness
	@Override
	public int compareTo(UnitTest gene) {
		if (fitness < gene.fitness) {
			return -1;
		} else if (fitness > gene.fitness) {
			return 1;
		}
		return 0;
	}

}
