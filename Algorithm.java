import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

public class Algorithm {
	/* GA parameters */
	public static double uniformRate = 0.5;
	public static double mutationRate = 0.010;
	public static int tournamentSize = 5;
	public static boolean elitism = true;

	public static int BOARD_WIDTH = 20;
	public static int BOARD_HEIGHT = 15;

	public static String selectedMutationOperator;

	/* Public methods */

	// Evolve a population
	public static Population evolvePopulation(Population pop) {
		Population newPopulation = new Population(pop.individuals.length);
		
		// Keep our best individual
		if (elitism) {
			newPopulation.saveIndividual(0, pop.getFittest());
			
		}

		// Crossover population
		int elitismOffset;
		if (elitism) {
			elitismOffset = 1;
		} else {
			elitismOffset = 0;
		}
		// Loop over the population size and create new individuals with
		// crossover
		for (int i = elitismOffset; i < pop.individuals.length; i++) {
			Individual indiv1 = tournamentSelection(pop);
			Individual indiv2 = tournamentSelection(pop);
			Individual newIndiv = crossover(indiv1, indiv2);
			if (newIndiv != null)
				newPopulation.saveIndividual(i, newIndiv);
			else
				i--;
		}

		// Mutate population
		for (int i = elitismOffset; i < newPopulation.individuals.length; i++) {
			if (selectedMutationOperator.equals("Random Mutation"))
				mutate(newPopulation.individuals[i]);
			else if (selectedMutationOperator.equals("Swap Mutation"))
				swapMutation(newPopulation.individuals[i]);
			else if (selectedMutationOperator.equals("Scramble Mutation"))
				scrambleMutation(newPopulation.individuals[i]);
			else if (selectedMutationOperator.equals("Inversion Mutation"))
				inversionMutation(newPopulation.individuals[i]);
		}

		return newPopulation;
	}

	// Crossover individuals
	private static Individual crossover(Individual indiv1, Individual indiv2) {
		Individual newSol = new Individual();
		// Loop through genes
		for (int i = 0; i < indiv1.size(); i++) {
			// Crossover
			Point2D.Float gene;
			if (Math.random() <= uniformRate) {
				gene = indiv1.getGene(i);
			} else {
				gene = indiv2.getGene(i);
			}
			if (!newSol.isOverLapping((int) gene.getX(), (int) gene.getY(), i))
				newSol.setGene(i, gene);
			else
				return null;
		}
		return newSol;
	}

	// Mutate an individual
	private static void mutate(Individual indiv) {
		// Loop through genes
		for (int i = 0; i < indiv.size(); i++) {
			if (Math.random() <= mutationRate) {
				int x = (int) (Math.random() * Algorithm.BOARD_WIDTH);
				int y = (int) (Math.random() * Algorithm.BOARD_HEIGHT);
				if (!indiv.isOverLapping(x, y, Individual.DEFAULT_GENE_LENGTH)) {
					Point2D.Float gene = new Point2D.Float((int) (Math.random() * BOARD_WIDTH),
							(int) (Math.random() * BOARD_HEIGHT));
					indiv.setGene(i, gene);
				} else
					i--;
			}
		}
	}

	private static void swapMutation(Individual indiv) {
		for (int i = 0; i < indiv.size(); i++) {
			if (Math.random() <= mutationRate) {
				int number = indiv.DEFAULT_GENE_LENGTH;
				int random1 = (int) (Math.random() * number);
				int random2 = (int) (Math.random() * number);
				if (random1 != random2) {
					Float a = indiv.getGene(random1);
					Float b = indiv.getGene(random2);
					indiv.setGene(random1, b);
					indiv.setGene(random2, a);
				} else
					i--;
			}
		}
	}

	private static void scrambleMutation(Individual indiv) {
		for (int i = 0; i < indiv.size(); i++) {
			if (Math.random() <= mutationRate) {
				int number = indiv.DEFAULT_GENE_LENGTH;
				int random1 = (int) (Math.random() * number);
				int random2 = (int) (Math.random() * number);
				if (random1 < random2) {
					int range = (random2 - random1) + 1;
					for (int k = 0; k < range; k++) {
						int i1 = (int) (Math.random() *range)+random1;
						int i2 = (int) (Math.random() *range)+random1;
						Float a = indiv.getGene(i1);
						Float b = indiv.getGene(i2);
						indiv.setGene(i1, b);
						indiv.setGene(i2, a);
					}
				}
			}
		}
	}

	private static void inversionMutation(Individual indiv) {
		for (int i = 0; i < indiv.size(); i++) {
			if (Math.random() <= mutationRate) {
				int number = indiv.DEFAULT_GENE_LENGTH;
				int random1 = (int) (Math.random() * number);
				int random2 = (int) (Math.random() * number);
				if (random1 < random2) {
					float midPoint = random1 + ((random2 + 1) - random1) / 2;
					int end = random2;
					int begin = random1;
					for (int k = 0; i < midPoint; i++) {
						Float a = indiv.getGene(end);
						Float b = indiv.getGene(begin);
						indiv.setGene(begin, a);
						indiv.setGene(end, b);
						end--;
					}
				}

			}
		}
	}

	// Select individuals for crossover
	private static Individual tournamentSelection(Population pop) {
		// Create a tournament population
		Population tournament = new Population(tournamentSize);
		// For each place in the tournament get a random individual
		for (int i = 0; i < tournamentSize; i++) {
			int randomId = (int) (Math.random() * pop.individuals.length);
			tournament.saveIndividual(i, pop.individuals[randomId]);
		}
		// Get the fittest
		Individual fittest = tournament.getFittest();
		return fittest;
	}
}
