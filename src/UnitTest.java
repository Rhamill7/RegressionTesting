import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class UnitTest implements Comparable<UnitTest> {

	/* What we are aiming for */
	public static char[] target = "Hello World!".toCharArray();
	public static Random rand = new Random();
	private static int[] gene;
	private float fitness;

	public UnitTest(int[] gene) {
		this.gene = gene;
		this.fitness = calculateFitness(gene);
	}

	public int[] getGene() {
		return gene;
	}

	public float getFitness() {
		return fitness;
	}

	public static UnitTest getUnitTests(int[] test) {
		return new UnitTest(test);

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
	private static float calculateFitness(int[] gene) {
		int fitness = 0;
		int faults = 0;
		// char[] geneChars = gene.toCharArray();
		for (int i = 0; i < gene.length; i++) {
		//	System.out.println(gene[i]);
			if (gene[i] == 1) {
				faults++;
			}
		}
	//	System.out.println(gene.length);
		float APFD = (((float) faults)/((float)gene.length)); //average percentage of fauls detected
	//	System.out.println(APFD);
		// fitness += Math.abs(((int) geneChars[i]) - ((int) target[i]));
		// }
		return APFD;
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
