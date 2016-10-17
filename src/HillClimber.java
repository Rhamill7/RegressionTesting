import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HillClimber {
	
	private Chromosome startingPoint, globalfit;
	private Population p;
	private Random r;

	public HillClimber(Population pop) {
		p = pop;
		r = new Random();
		startingPoint = p.getPopulation()[r.nextInt(p.getPopulation().length - 1)];
		globalfit = startingPoint;
	}
	
	
	public Chromosome getStartingPoint() {
		return startingPoint;
	}
	
	private List<Chromosome> getNeighbours(Chromosome gene, int totTests) {
		int numTests = gene.getGene().size();
		List<Chromosome> nHood = new ArrayList<Chromosome>();
		
		for(int i = 0; i < numTests; i++) {
			List<Integer> original = gene.getGene();
			//int uniTest = original.get(i);
			int randomReplacement = gene.getGene().get(r.nextInt(gene.getGene().size()));
			boolean randomExist = false;
			for(int j = 0; j < numTests; j++) {
				if(original.get(j) == randomReplacement) {
					randomExist = true;
				}
			}
			if(!randomExist) {
				original.set(i, randomReplacement);
				nHood.add(new Chromosome(original));
			}
		}
		
		
		return nHood;
	}


	
//	private List<Chromosome> getNeighbours(Chromosome gene, int testNum) {
//		int numTests = gene.getGene().size();
//		List<Chromosome> nHood = new ArrayList<Chromosome>();
//		
//		
//		for(int i = 0; i < numTests; i++) {
//			List<Integer> original = gene.getGene();
//			int uniTest = original.get(i);
//			System.out.println(uniTest);
//			if(uniTest > 0) {
//				original.set(i, (uniTest-1));				
//				nHood.add(new Chromosome(original));
//			}
//			if(uniTest < (testNum-1)) {
//				original.set(i, (uniTest + 1));
//				nHood.add(new Chromosome(original));
//			}
//		}
//		return nHood;
//	}
	
	//compare the fitness of the starting point to the fitness of its neighbours
	public void compareFitness(Chromosome gene, int totalTests) {
		Chromosome fittest = gene;
		List<Chromosome> n = getNeighbours(gene, 0);
		for(int i = 0; i < n.size(); i++) {
			if(fittest.getFitness() < n.get(i).getFitness()) {
				fittest = n.get(i);
			}
		}
		
		//if we have a local optimum 
		if(fittest.equals(gene)) {
			//pick new random starting point but keep a hold of the best solution in local optimum
			startingPoint = p.getPopulation()[r.nextInt(p.getPopulation().length - 1)];
			globalfit = compareLocalOptimums(globalfit, fittest);
		}
		else {
			if(globalfit.getFitness() < fittest.getFitness()) {
				globalfit = fittest;
			}
			startingPoint = fittest;
		}
	}
	
	private Chromosome compareLocalOptimums(Chromosome a, Chromosome b) {
		if(a.getFitness() > b.getFitness()) {
			return a;
		}
		return b;
	}
	
	//return the fittest solution
	public Chromosome bestSolution() {
		return globalfit;
	}

}
