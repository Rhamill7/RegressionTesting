import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Chromosome implements Comparable<Chromosome> {

	/* What we are aiming for */
	public static int target = 5;
	public static Random rand = new Random();
	private static ArrayList<int[]> gene;
	private static ArrayList<int[]> pool;
	private float fitness;

	public Chromosome(ArrayList<int[]> gene) {
		this.gene = gene;
		// this.fitness = calculateFitness(gene);
	}

	public ArrayList<int[]> getGene() {
		return gene;
	}

	public float getFitness() {
		return fitness;
	}

	public static void setPool(ArrayList<int[]> sourcePool) {
		pool = sourcePool;
	}

	public static Chromosome generateRandom() {
		ArrayList<int[]> gene = new ArrayList<int[]>();
		Random random = new Random();
		for (int j = 0; j < target; j++) {
			gene.add(pool.get(random.nextInt(pool.size())));
		}
		// System.out.println(gene);

		return new Chromosome(gene);

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
			// System.out.println(gene[i]);
			if (gene[i] == 1) {
				faults++;
			}
		}
		// System.out.println(gene.length);
		float APFD = (((float) faults) / ((float) gene.length)); // average
																	// percentage
																	// of fauls
																	// detected
		// System.out.println(APFD);
		// fitness += Math.abs(((int) geneChars[i]) - ((int) target[i]));
		// }
		return APFD;
	}

	/* Crossover Performed here */
	public Chromosome[] crossover(Chromosome mate) {
	
	 ArrayList<int[]> geneArray1 =  gene;
	ArrayList<int[]> geneArray2 = mate.gene;
	//ArrayList<int[]> child1 = new ArrayList<int[]>(gene.size());
	//ArrayList<int[]> child2 = new ArrayList<int[]>(gene.size());
	
	 /* Variable to modify split point */
	 // int splitPoint = geneArray1.length/2;
	 int splitPoint = rand.nextInt(geneArray1.size());
	
	 ArrayList<int[]> child1 = new ArrayList<int[]>(geneArray1.subList(0, splitPoint));
	// System.arraycopy(geneArray1, 0, child1, 0, splitPoint);
	 //get other half of 2nd array but do not use duplicates
	 
	 for (int i = splitPoint; i < geneArray1.size(); i++) {
			 if (!(geneArray1.get(i).equals(geneArray2.get(i)))) {
				 child1.add(geneArray2.get(i));
			 }
			 else {
				 child1.add(geneArray1.get(i));
			 }
	 }
	// System.arraycopy(geneArray2, splitPoint, child1, splitPoint,
	 //(child1.size() - splitPoint));
	
	// System.arraycopy(geneArray2, 0, child2, 0, splitPoint);
	 
	 ArrayList<int[]> child2 = new ArrayList<int[]>(geneArray2.subList(0, splitPoint));
	 for (int i = splitPoint; i < geneArray1.size(); i++) {
		 if (!(geneArray1.get(i).equals(geneArray2.get(i)))) {
			 child2.add(geneArray1.get(i));
		 }
		 else {
			 child2.add(geneArray2.get(i));
		 }
 }
	 
	// System.arraycopy(geneArray1, splitPoint, child2, splitPoint,
	 //(child2.size() - splitPoint));
	
	 return new Chromosome[] { new Chromosome(child1), new
	 Chromosome(child2) };
	 }

	/* mutation performed here */
	 public Chromosome mutate() {
	// char[] geneChars = gene.toCharArray();
		 int one = rand.nextInt(gene.size());
		 int two = 0;
		 if ( one == gene.size() ){
			  two = one - 1;
		 }
		 else if (one == 0){
			 two = one + 1;
		 }
		 else {
			 two = one-1;
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
		if (fitness < gene.fitness) {
			return -1;
		} else if (fitness > gene.fitness) {
			return 1;
		}
		return 0;
	}

}
