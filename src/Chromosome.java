import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class Chromosome implements Comparable<Chromosome> {

	/* What we are aiming for */
	public static Random rand = new Random();
	private ArrayList<Integer> gene;
	private static ArrayList<Integer> pool;
	private double fitness;
	private static int totalTests;
	private static int faultNumber;
	static HashMap<Integer, int[]> key;

	public Chromosome(ArrayList<Integer> gene) {
		this.gene = gene;
		this.fitness = calculateFitness(gene);
	}

	public ArrayList<Integer> getGene() {
		return gene;
	}

	public double getFitness() {
		return fitness;
	}

	public static void setData(ArrayList<Integer> allTests, HashMap<Integer, int[]> keyin, int tTests, int fNumber) {
		pool = allTests;
		key = keyin;
		totalTests = tTests;
		faultNumber = fNumber;
	}

	static Chromosome generateRandom(int geneLength) {
		// System.out.println("new gene");
		ArrayList<Integer> gene = new ArrayList<Integer>();
		for (int j = 0; j < geneLength; j++) {
			int test = pool.get(rand.nextInt(pool.size() - 1));
			// System.out.println(test);
			gene.add(test);
		}

		return new Chromosome(gene);
	}

	/* calculate fitness using fitness function */
	public double calculateFitness(List<Integer> gene) {
		int suiteTotal = 0;
		boolean fails = false;
		for (int i = 0; i < faultNumber; i++) {
			for (int j = 0; j < gene.size(); j++) {
				if (key.get(gene.get(j))[i] == 1) {
					suiteTotal += (j + 1);
					fails = true;
					break;
				}
			}
			if (!fails) {
				suiteTotal += (gene.size() + 1);
			}
			fails = false;
		}
		// double test = (double)1/(double)(2*gene.size());
		// double test2 =(double) suiteTotal /((double) ( faultNumber *
		// gene.size()));
		// double test3 = 1- (test + test2);
		// System.out.println(test);
		// System.out.println(test2);
		// System.out.println(test3);
		fitness = (double) 1 - ((double) suiteTotal / ((double) (faultNumber * gene.size()))
				+ ((double) 1 / (double) (2 * gene.size())));
		// System.out.println(fitness);
		return fitness;
	}

	/* Crossover Performed here */
	public Chromosome[] crossover(Chromosome mate) {

		ArrayList<Integer> geneArray1 = gene;
		ArrayList<Integer> geneArray2 = mate.gene;

		/* Variable to modify split point */
		int splitPoint = rand.nextInt(geneArray1.size());
		// System.out.println(splitPoint);
		ArrayList<Integer> child1 = new ArrayList<Integer>(geneArray1.subList(0, splitPoint));
		// get other half of 2nd array but do not use duplicates

		for (int i = splitPoint; i < geneArray1.size(); i++) {
			// if (!(geneArray1.get(i).equals(geneArray2.get(i)))) { //if i1 is
			// not i2
			if (child1.contains(geneArray2.get(i))) {
				child1.add(geneArray1.get(i));
			} else {
				child1.add(geneArray2.get(i));
			}
		}

		ArrayList<Integer> child2 = new ArrayList<Integer>(geneArray2.subList(0, splitPoint));
		for (int i = splitPoint; i < geneArray1.size(); i++) {
			if (child2.contains(geneArray1.get(i))) {
				child2.add(geneArray2.get(i));
			} else {
				child2.add(geneArray1.get(i));
			}

		}

		return new Chromosome[] { new Chromosome(child1), new Chromosome(child2) };
	}

	/* mutation performed here */
	public Chromosome mutate() {
		// char[] geneChars = gene.toCharArray();
		int one = rand.nextInt(gene.size() - 1);
		// System.out.println(one);
		int two = 0;
		if (one == gene.size()) {
			two = one - 1;
		} else {
			two = one + 1;
		}
		int mu1 = gene.get(one);
		int mu2 = gene.get(two);
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
