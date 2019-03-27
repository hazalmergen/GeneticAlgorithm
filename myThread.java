import javax.swing.JTextArea;

public class myThread extends Thread {
	
	private enum State {
		INITIAL,
		PAUSED,
		STOPPED
	};

	private State state = State.INITIAL;
	
	Population myPop;
	int generationCount = 0;
	GenAlgPanel gaPanel;
	JTextArea textArea;
	
	public myThread(GenAlgPanel gaPanel, JTextArea textArea, Population Pop ) {
		this.gaPanel = gaPanel;
		this.textArea = textArea;
		this.myPop=Pop;
	}

	public void mystop() {
		state=State.STOPPED;
	}

	public void pause() {
		state = State.PAUSED;
	}

	public void myresume() {
		state = State.INITIAL;
	}

	public void run() {
		//Individual.generateConnectedGene();
		String connectedComponentsString = "";
		//myPop = new Population(50);
		while (true) {
			String s = connectedComponentsString;
			switch(state) {
				case INITIAL:
					Individual fittestIndividual = myPop.getFittest();
					generationCount++;
					s += "Generation: " + generationCount + "\n";
					s += "Fittest: " + fittestIndividual.getFitness() + "\n";
					s += "Intersection Count: " + fittestIndividual.getIntersectionCount() + "\n";
					myPop = Algorithm.evolvePopulation(myPop);
					gaPanel.setPopulation(myPop);
					textArea.setText(s);
					break;
				case PAUSED:
					break;
					
				case STOPPED:
					return;
			}
			try {
				Thread.sleep(100);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
