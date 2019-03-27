
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class Individual implements Serializable{

	public static int DEFAULT_GENE_LENGTH = 32;

	private Population pop;
	
	public Point2D.Float[] genes = new Point2D.Float[DEFAULT_GENE_LENGTH];

	private int fitness = 0;

	
	public void setPopulation(Population myPop) {
		this.pop = myPop;

	}

	public static class ConnectingComps implements Serializable {
		
		private int comp1, comp2;

		public ConnectingComps(int comp1, int comp2) {
			this.comp1 = comp1;
			this.comp2 = comp2;
		}

		public int getComp1() {
			return comp1;
		}

		public int getComp2() {
			return comp2;
		}
		
		public String toString() {
			return comp1 + ", " + comp2;
		}
	}

	public static ConnectingComps[] connectedcomps;

	public static void generateConnectedGene() {
		connectedcomps = new ConnectingComps[Population.MAX_CONNECTED_COMP_COUNT];
		for (int i = 0; i < Population.MAX_CONNECTED_COMP_COUNT; i++) {
			int first = (int) (Math.random() * DEFAULT_GENE_LENGTH);
			int second = (int) (Math.random() * DEFAULT_GENE_LENGTH);
			if (first != second) {
				ConnectingComps connectedcomp = new ConnectingComps(first, second);
				connectedcomps[i] = connectedcomp;
				System.out.println("Connected components are: " + connectedcomp.comp1 + "-------" + connectedcomp.comp2);
			} else
				i--;
		}

	}
	
	public static ConnectingComps[] getConnectedComponents() {
		return connectedcomps;
	}


	public static ConnectingComps getConnectedComponent(int index) {
		return connectedcomps[index];
	}

	// Create a random individual
	public void generateIndividual() {

		for (int i = 0; i < genes.length; i++) {
			int x = (int) (Math.random() * Algorithm.BOARD_WIDTH);
			int y = (int) (Math.random() * Algorithm.BOARD_HEIGHT);
			if(!isOverLapping(x, y, i)) {
				Point2D.Float gene = new Point2D.Float((int) (Math.random() * Algorithm.BOARD_WIDTH),
						(int) (Math.random() * Algorithm.BOARD_HEIGHT));
				genes[i] = gene;
			} else
				i--;
		}

	}
	
	public boolean isOverLapping(int x, int y, int checkTill) {
		boolean overlapping = false;
		for (int j = 0; j < checkTill; j++) {
			if(x == getGene(j).getX() && y == getGene(j).getY()) {
				overlapping = true;
				break;
			}
		}
		return overlapping;
	}

	public Point2D.Float getGene(int index) {
		return genes[index];
	}
	
	
	public void setGene(int index, Point2D.Float value) {
		genes[index] = value;
		fitness = 0;
	}

	public int size() {
		int counter = 0;
		for (int i = 0; i < genes.length; i++) {
			if (genes[i] != null) {
				counter++;
			}
		}
		return counter;
	}

	public int getFitness() {
		if (fitness == 0) {
			fitness = FitnessCalc.getFitness(this);
		}
		return fitness;
	}

	public int getArea() {
		return Algorithm.BOARD_HEIGHT * Algorithm.BOARD_WIDTH;
	}
	
	public int getIntersectionCount() {
		int counter=0;
		for (int i = 0; i < Population.MAX_CONNECTED_COMP_COUNT; i++) {
			if (getConnectedComponent(i) != null) {
				for (int j = i + 1; j < Population.MAX_CONNECTED_COMP_COUNT; j++) {
					if (getConnectedComponent(j) != null) {
						Point2D line1P1 = getGene(getConnectedComponent(i).getComp1());
						Point2D line1P2 = getGene(getConnectedComponent(i).getComp2());
						Point2D line2P1 = getGene(getConnectedComponent(j).getComp1());
						Point2D line2P2 = getGene(getConnectedComponent(j).getComp2());
						if(!line1P1.equals(line2P1) && !line1P1.equals(line2P2) &&
								!line1P2.equals(line2P1) && !line1P2.equals(line2P2)) {
							Line2D line1 = new Line2D.Float(line1P1, line1P2);
							Line2D line2 = new Line2D.Float(line2P1, line2P2);
							boolean result = line2.intersectsLine(line1);
							if (result){
								counter++;
							}
						}
					}
				}
			}
		}
		return counter;
	}
	
	public Population getPop() {
		return pop;
	}


}
