import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

public class GenAlgPanel extends JPanel {

	private Population pop;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		float scaleRatio = 0;
		if (getWidth() < getHeight())
			scaleRatio = (float) getWidth() / Algorithm.BOARD_WIDTH;
		else
			scaleRatio = (float) getHeight() / Algorithm.BOARD_HEIGHT;
		g.setColor(Color.gray);
		g.drawRect(0, 0, (int) (Algorithm.BOARD_WIDTH * scaleRatio), (int) (Algorithm.BOARD_HEIGHT * scaleRatio));
		if (pop != null) {
			Individual fittestIndiv = pop.getFittest();
			
			for (int i = 0; i < Population.MAX_CONNECTED_COMP_COUNT; i++) {
				Individual.ConnectingComps connection = fittestIndiv.getConnectedComponent(i);
				if (connection != null) {
					g.setColor(Color.getHSBColor((float) Math.random(), .8f, .8f));
					Point2D p1 = fittestIndiv.getGene(connection.getComp1());
					Point2D p2 = fittestIndiv.getGene(connection.getComp2());
					g.drawLine((int) (p1.getX() * scaleRatio), (int) (p1.getY() * scaleRatio),
							(int) (p2.getX() * scaleRatio), (int) (p2.getY() * scaleRatio));
				}
			}
			
			int radius = 20;
			for (int i = 0; i < fittestIndiv.size(); i++) {
				Point2D point = fittestIndiv.getGene(i);
				g.setColor(Color.black);
				g.fillOval((int) (point.getX() * scaleRatio - radius / 2),
						(int) (point.getY() * scaleRatio - radius / 2), radius, radius);
				g.setColor(Color.white);
				g.drawString(String.valueOf(i), (int) (point.getX() * scaleRatio - radius / 3),
						(int) (point.getY() * scaleRatio + radius / 3));

			}
		}
	}

	public void setPopulation(Population myPop) {
		this.pop = myPop;
		repaint();
	}

}
