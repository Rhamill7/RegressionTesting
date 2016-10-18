
public class Main {

	public static void main(String[] args) {

		/* Modify Variables to adjust results */
		int populationSize = 1000; // 100
		int numberOfGenerations = 1;
		float crossoverRatio = 0.9f;// 0.2 // 0.1f = 10%
		float mutationRatio = 0.15f; // 0.9// probability of mutation 
		int faultNumber = 9; // 38; //9;
		int geneLength = 100;
		int totalTests = 216; // 1000;//216;
		int noChanges = 0;
		double previousFit = 0;
		

		for (int i = 0; i < 10; i++) {
			long start = System.currentTimeMillis();
			Population p = new Population();
			p.createPopulation(populationSize, crossoverRatio, mutationRatio, faultNumber, geneLength, totalTests);
			Chromosome bestGene = p.getPopulation()[0];

			/*
			///////////////////////////////////////////////////////////////////////////////////////////////////
			 HillClimber hc = new HillClimber(p);
			 for (int j = 0; j < 1000; j++) {
				 double hcFit = hc.bestSolution().getFitness();
			 	 hc.compareFitness(hc.getStartingPoint(), totalTests);
			 	 if(hcFit == hc.bestSolution().getFitness()) {
			 		 noChanges++;
			 		 if(noChanges == 100) {
			 			 System.out.println("Generation " + j);
			 			 break;
			 		 }
			 	 }
			 	 else {
			 		 noChanges = 0;
			 	 }
			 }
			 System.out.println("Best solution with Hill Climber: " + hc.bestSolution().getFitness());
			 ///////////////////////////////////////////////////////////////////////////////////////////
			  */

			while (bestGene.getFitness() != 1 && numberOfGenerations < 1000 && noChanges != 100) {
				//p.evolve();
				p.random();
				bestGene = p.getPopulation()[0];
				
				if(bestGene.getFitness() == previousFit) {
					noChanges++;
				}
				else {
					noChanges = 0;
				}
				
				//System.out.println("Generation " + numberOfGenerations + ", best fitness: " + bestGene.getFitness());
				numberOfGenerations++;
				previousFit = bestGene.getFitness();
			}
			
			long finish = System.currentTimeMillis();
			
			/*
			/////////////////////////////////////////////////////////////////////////////////////
			System.out.println("Time elapsed in ms: " + (finish - start));
			/////////////////////////////////////////////////////////////////////////////////////
			*/

			
			System.out.println("Gen Number: " + numberOfGenerations + " BestGene: " + bestGene.getGene()
				+ " Current Fitness " + bestGene.getFitness() + " Time elapsed in ms:" + (finish - start));
			//System.out.println("\n");

			numberOfGenerations = 1;
			noChanges = 0;
		}
	}

}
