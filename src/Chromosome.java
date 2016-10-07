import java.util.Random;

public class Chromosome implements Comparable<Chromosome> {

	/* What we are aiming for */
	public static char[] target = "Hello World!".toCharArray();
	public static Random rand = new Random();
	private String gene;
	private int fitness;

	public Chromosome(String gene) {
		this.gene = gene;
		this.fitness = calculateFitness(gene);
	}

	public String getGene() {
		return gene;
	}

	public int getFitness() {
		return fitness;
	}

	/* Generate random chromosome */
	static Chromosome generateRandom() {
		char[] gene = new char[target.length];
		for (int i = 0; i < gene.length; i++) {
			gene[i] = (char) (rand.nextInt(90) + 32);
		}
		return new Chromosome(String.valueOf(gene));
	}

	/* calculate fitness using fitness function */
	private static int calculateFitness(String gene) {
		int fitness = 0;
		char[] geneChars = gene.toCharArray();
		for (int i = 0; i < geneChars.length; i++) {
			// work out how close each letter is by subtracting their ASCII
			// values
			// add the results together, if it is the same this will equal 0.
			fitness += Math.abs(((int) geneChars[i]) - ((int) target[i]));
		}
		return fitness;
	}

	/* Crossover Performed here */
	public Chromosome[] crossover(Chromosome mate) {

		char[] geneArray1 = gene.toCharArray();
		char[] geneArray2 = mate.gene.toCharArray();
		char[] child1 = new char[gene.length()];
		char[] child2 = new char[gene.length()];

		/* Variable to modify split point */
		// int splitPoint = geneArray1.length/2;
		int splitPoint = rand.nextInt(geneArray1.length);

		System.arraycopy(geneArray1, 0, child1, 0, splitPoint);
		System.arraycopy(geneArray2, splitPoint, child1, splitPoint, (child1.length - splitPoint));

		System.arraycopy(geneArray2, 0, child2, 0, splitPoint);
		System.arraycopy(geneArray1, splitPoint, child2, splitPoint, (child2.length - splitPoint));

		return new Chromosome[] { new Chromosome(String.valueOf(child1)), new Chromosome(String.valueOf(child2)) };
	}

	/* mutation performed here */
	public Chromosome mutate() {
		char[] geneChars = gene.toCharArray();
		int ranChars = rand.nextInt(geneChars.length);
		int mutation = (rand.nextInt(90) + 32);
		geneChars[ranChars] = (char) (mutation);

		return new Chromosome(String.valueOf(geneChars));
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
