
public class Main {

	public static void main(String[] args) {

		/* Modify Variables to adjust results */
		int populationSize = 5; // 100
		int numberOfGenerations = 1;
		float crossoverRatio = 0.89f;// 0.2 // 0.1f = 10%
		float mutationRatio = 0.15f; // 0.9// probability of mutation for any
		int faultNumber = 9;

	//	for (int i = 0; i < 10; i++) {

			long start = System.currentTimeMillis();
			Population p = new Population(); 
			//p.getUnitTests();
			p.createPopulation(populationSize, crossoverRatio, mutationRatio, faultNumber);
		//	UnitTest bestGene = p.getPopulation()[0];
		//	while (bestGene.getFitness() != 0 && numberOfGenerations < 20000) {
			//	p.evolve(); 
				//p.random();
				// bestGene = p.hillClimber();
			//	bestGene = p.getPopulation()[0];
			//	numberOfGenerations++;

	//		}
			long finish = System.currentTimeMillis();

			
		//	System.out.println("Gen Number: " + numberOfGenerations + " Best Gene: " + bestGene.getGene()
			//		+ " Current Fitness " + bestGene.getFitness() + " Time elapsed in ms: " + (finish - start));

			numberOfGenerations = 1;
		//}
	}

}
