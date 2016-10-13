
public class Main {

	public static void main(String[] args) {

		/* Modify Variables to adjust results */
		int populationSize = 1000; // 100
		int numberOfGenerations = 1;
		float crossoverRatio = 0.5f;// 0.2 // 0.1f = 10%
		float mutationRatio = 0.05f; // 0.9// probability of mutation for any
		int faultNumber = 9;
		int geneLength = 5;
		int totalTests = 216;

		// for (int i = 0; i < 10; i++) {
		long start = System.currentTimeMillis();
		Population p = new Population();
		p.createPopulation(populationSize, crossoverRatio, mutationRatio, faultNumber, geneLength, totalTests);
		Chromosome bestGene = p.getPopulation()[0];
		Chromosome worstGene = p.getPopulation()[215];
	//	System.out.println(numberOfGenerations);
		while (bestGene.getFitness() != 0 && numberOfGenerations < 200000) {
			p.evolve();
		//	System.out.println(numberOfGenerations);
			
			// p.random();
			 bestGene = p.getPopulation()[0];
			 worstGene = p.getPopulation()[215];
			 System.out.println(bestGene.getFitness());
			 System.out.println(worstGene.getFitness());
			//System.out.println(p.getPopulation());
			 numberOfGenerations++;
			 

		}
		long finish = System.currentTimeMillis();

		// System.out.println("Gen Number: " + numberOfGenerations + " Best
		// Gene: " + bestGene.getGene()
		// + " Current Fitness " + bestGene.getFitness() + " Time elapsed in ms:
		// " + (finish - start));

		numberOfGenerations = 1;
		// }
	}

}
