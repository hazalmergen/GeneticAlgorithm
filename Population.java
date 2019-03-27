import java.awt.geom.Line2D;
import java.io.Serializable;

public class Population implements Serializable {

	public static int MAX_CONNECTED_COMP_COUNT=10;
	
	public static int POPULATION_SIZE = 50;

	Individual[] individuals;

	// Create a population
	public Population(int populationSize) {
	
		individuals = new Individual[populationSize];

		// Loop and create individuals
		for (int i = 0; i < individuals.length; i++) {
			Individual newIndividual = new Individual();
			newIndividual.generateIndividual();
			saveIndividual(i, newIndividual);
		}
	}

	public Individual getFittest() {
		Individual fittest = individuals[0];

		// Loop through individuals to find fittest
		for (int i = 0; i < individuals.length; i++) {
			if (fittest.getFitness() <= individuals[i].getFitness()) {
				fittest = individuals[i];
			}
		}
		return fittest;
	}

	// Save individual
	public void saveIndividual(int index, Individual indiv) {
		individuals[index] = indiv;
	}
}
