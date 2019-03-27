import java.awt.geom.Line2D;
import java.awt.geom.Point2D;


public class FitnessCalc {

	static int getFitness(Individual individual) {

		int fitness = 2000;
		for(int i=0 ; i< individual.size();i++) {
			if(individual.getGene(i).getX()==0 || individual.getGene(i).getY()== 0) {
				fitness-=10;
			}
		}

		fitness -= 20 * individual.getIntersectionCount();
	
		double maxDistance=0.0;
		for(int i=0; i<Population.MAX_CONNECTED_COMP_COUNT;i++) {
			if(individual.getConnectedComponent(i)!=null) {
				Point2D p1 = individual.getGene(individual.getConnectedComponent(i).getComp1());
				Point2D p2 = individual.getGene(individual.getConnectedComponent(i).getComp2());
				double distance=Math.abs(Math.sqrt(Math.pow((p2.getX()-p1.getX()), 2)+Math.pow((p2.getY()-p1.getY()), 2)));
				fitness-=distance/Math.sqrt(Algorithm.BOARD_HEIGHT*Algorithm.BOARD_HEIGHT + Algorithm.BOARD_WIDTH*Algorithm.BOARD_WIDTH);
			}
		}
		
		
		

		for (int i = 0; i < Population.MAX_CONNECTED_COMP_COUNT; i++) {
			if (individual.getConnectedComponent(i) != null) {
				Point2D p1 = individual.getGene(individual.getConnectedComponent(i).getComp1());
				Point2D p2 = individual.getGene(individual.getConnectedComponent(i).getComp2());
				for (int j = 0; j < individual.size(); j++) {
					Point2D p3 = individual.getGene(j); 
					
					if (Math.abs((p2.getY() - p1.getY()) * p3.getX() + (p1.getX() - p2.getX()) * p3.getY()
							+ (p2.getX() * p1.getY() - p1.getX() * p2.getY())
									/ Math.sqrt(Math.pow((p2.getY() - p1.getY()), 2)
											+ Math.pow((p1.getX() - p2.getX()), 2))) == 0) {

						fitness -= 15;
					}	
					
					
				}

			}
		}

		return fitness;
	}
}
