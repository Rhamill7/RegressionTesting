import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {

		/* Modify Variables to adjust results */
		int populationSize = 1000; // 100
		int numberOfGenerations = 1;
		float crossoverRatio = 0.9f;// 0.2 // 0.1f = 10%
		float mutationRatio = 0.15f; // 0.9// probability of mutation for any
		int faultNumber = 38; // 38; //9;
		int geneLength = 100;
		int totalTests = 1000; // 1000;//216;

		for (int i = 0; i < 10; i++) {
			long start = System.currentTimeMillis();
			Population p = new Population();
			p.createPopulation(populationSize, crossoverRatio, mutationRatio, faultNumber, geneLength, totalTests);
			
			
			Chromosome bestGene = p.getPopulation()[0];
			// Chromosome worstGene = p.getPopulation()[215];
//			 HillClimber hc = new HillClimber(p);
//			 for (int i = 0; i < 1000; i++) {
//			 hc.compareFitness(hc.getStartingPoint(), totalTests);
//			 System.out.println("Best solution with Hill Climber: " +
//			 hc.bestSolution().getFitness());
//			 }

			while (bestGene.getFitness() != 1 && numberOfGenerations < 1000) {
			//	p.evolve();
				 p.random();


				bestGene = p.getPopulation()[0];
				// worstGene = p.getPopulation()[215];

				// System.out.println(bestGene.getGene().toString());
				// System.out.println("best " + bestGene.getFitness());
				// System.out.println("Worst " + worstGene.getFitness());
				numberOfGenerations++;
			}
			long finish = System.currentTimeMillis();

			System.out.println("Gen Number: " + numberOfGenerations + " BestGene: " + bestGene.getGene()
				+ " Current Fitness " + bestGene.getFitness() + " Time elapsed in ms:" + (finish - start));

			numberOfGenerations = 1;
		}
	}

}
