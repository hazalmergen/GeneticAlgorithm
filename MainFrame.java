import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame {
	static Population myPop;
	public static myThread t1;

	JSlider populationsizeSlider = new JSlider(0, 200, 50);
	JSlider boardHeightSlider = new JSlider(10, 100, 15);
	JSlider boardWidthSlider = new JSlider(10, 100, 20);
	JSlider componentNumSlider = new JSlider(0, 100, 32);
	JSlider conncomponentNumSlider = new JSlider(0, 100, 32);
	JSlider uniformRateSlider = new JSlider(0, 100, 50);
	JSlider mutationrateSlider = new JSlider(0, 100, 15);
	JSlider tournamentsizeSlider = new JSlider(1, 10, 5);
	JComboBox<String> comboMutation;
	JTextArea fileName;
	public void initialize() {
		try {
			Population.POPULATION_SIZE = populationsizeSlider.getValue();
		} catch (Exception ex) {
		}
		try {
			Algorithm.BOARD_HEIGHT = boardHeightSlider.getValue();
		} catch (Exception ex) {
		}
		try {
			Algorithm.BOARD_WIDTH = boardWidthSlider.getValue();
		} catch (Exception ex) {
		}
		try {
			Individual.DEFAULT_GENE_LENGTH = componentNumSlider.getValue();
		} catch (Exception ex) {
		}
		try {
			Population.MAX_CONNECTED_COMP_COUNT = conncomponentNumSlider.getValue();
		} catch (Exception ex) {
		}
		try {
			Algorithm.uniformRate = uniformRateSlider.getValue() / 100.0;
		} catch (Exception ex) {
		}
		try {
			Algorithm.mutationRate = mutationrateSlider.getValue() / 1000.0;
		} catch (Exception ex) {
		}
		try {
			Algorithm.tournamentSize = tournamentsizeSlider.getValue();
		} catch (Exception ex) {
		}
		Algorithm.selectedMutationOperator = (String) comboMutation.getSelectedItem();
	}
	
	public void load(){
		try{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName.getText()));
			Algorithm.BOARD_HEIGHT=(int) ois.readObject();
			Algorithm.BOARD_WIDTH=(int) ois.readObject();
			Individual.connectedcomps=(Individual.ConnectingComps[]) ois.readObject();
			myPop=(Population) ois.readObject();
			
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public MainFrame() {
		GenAlgPanel gaPanel = new GenAlgPanel();
		JPanel toolPanel = new JPanel();
		JPanel inputPanel = new JPanel();
		
		comboMutation = new JComboBox<String>();
		comboMutation.addItem("Random Mutation");
		comboMutation.addItem("Swap Mutation");
		comboMutation.addItem("Scramble Mutation");
		comboMutation.addItem("Inversion Mutation");
		
		comboMutation.setMaximumSize(comboMutation.getPreferredSize());
		JTextArea t = new JTextArea("", 10, 15);
		t.setEditable(false);
		
		
		fileName= new JTextArea("Enter file name:  ");
		fileName.setEditable(true);
		fileName.setBackground(Color.LIGHT_GRAY);
	
		JButton fileChooser= new JButton("Choose the file to generate");
		fileChooser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc= new JFileChooser();
				int returnValue= fc.showOpenDialog(null);
				if(returnValue==JFileChooser.APPROVE_OPTION){
					File selectedFile=fc.getSelectedFile();
					String name= selectedFile.getName();
					fileName.setText(name);
				}
			}
		});
		
		BoxLayout toolPanelLayout = new BoxLayout(toolPanel, BoxLayout.X_AXIS);
		BoxLayout inputPanelLayout = new BoxLayout(inputPanel, BoxLayout.Y_AXIS);

		toolPanel.setLayout(toolPanelLayout);
		inputPanel.setLayout(inputPanelLayout);

		JLabel text2 = new JLabel("----------------------------------------");
		JLabel text = new JLabel("   ");
		JLabel text1 = new JLabel("        Choose mutation type: ");

		JLabel populationsize = new JLabel("Arrange Population Size : ");
		populationsizeSlider.setMajorTickSpacing(50);
		populationsizeSlider.setMinorTickSpacing(10);
		populationsizeSlider.setPaintTicks(true);
		populationsizeSlider.setPaintLabels(true);
		populationsizeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					Population.POPULATION_SIZE = populationsizeSlider.getValue();
				} catch (Exception ex) {
				}
				populationsize.setText(populationsize.getText().substring(0, populationsize.getText().indexOf(':') + 1)
						+ " (" + Integer.toString(populationsizeSlider.getValue()) + ")");
			}
		});
		populationsize.setText(populationsize.getText().substring(0, populationsize.getText().indexOf(':') + 1) + " ("
				+ Integer.toString(populationsizeSlider.getValue()) + ")");

		JLabel boardHeight = new JLabel("Arrange Board Height : ");
		boardHeightSlider.setMajorTickSpacing(10);
		boardHeightSlider.setMinorTickSpacing(1);
		boardHeightSlider.setPaintTicks(true);
		boardHeightSlider.setPaintLabels(true);
		boardHeightSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					Algorithm.BOARD_HEIGHT = boardHeightSlider.getValue();
				} catch (Exception ex) {
				}
				boardHeight.setText(boardHeight.getText().substring(0, boardHeight.getText().indexOf(':') + 1) + " ("
						+ Integer.toString(boardHeightSlider.getValue()) + ")");
			}

		});
		boardHeight.setText(boardHeight.getText().substring(0, boardHeight.getText().indexOf(':') + 1) + " ("
				+ Integer.toString(boardHeightSlider.getValue()) + ")");

		JLabel boardWidth = new JLabel("Arrange Board Width : ");
		boardWidthSlider.setMajorTickSpacing(10);
		boardWidthSlider.setMinorTickSpacing(1);
		boardWidthSlider.setPaintTicks(true);
		boardWidthSlider.setPaintLabels(true);
		boardWidthSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					Algorithm.BOARD_WIDTH = boardWidthSlider.getValue();
				} catch (Exception ex) {
				}
				boardWidth.setText(boardWidth.getText().substring(0, boardWidth.getText().indexOf(':') + 1) + " ("
						+ Integer.toString(boardWidthSlider.getValue()) + ")");
			}
		});
		boardWidth.setText(boardWidth.getText().substring(0, boardWidth.getText().indexOf(':') + 1) + " ("
				+ Integer.toString(boardWidthSlider.getValue()) + ")");

		JLabel componentNum = new JLabel("Arrange Component Number : ");
		componentNumSlider.setMajorTickSpacing(10);
		componentNumSlider.setMinorTickSpacing(1);
		componentNumSlider.setPaintTicks(true);
		componentNumSlider.setPaintLabels(true);
		componentNumSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					Individual.DEFAULT_GENE_LENGTH = componentNumSlider.getValue();
				} catch (Exception ex) {
				}
				componentNum.setText(componentNum.getText().substring(0, componentNum.getText().indexOf(':') + 1) + " ("
						+ Integer.toString(componentNumSlider.getValue()) + ")");

			}
		});
		componentNum.setText(componentNum.getText().substring(0, componentNum.getText().indexOf(':') + 1) + " ("
				+ Integer.toString(componentNumSlider.getValue()) + ")");

		JLabel connCompNumber = new JLabel("Arrange Connected Component Number : ");
		conncomponentNumSlider.setMajorTickSpacing(10);
		conncomponentNumSlider.setMinorTickSpacing(1);
		conncomponentNumSlider.setPaintTicks(true);
		conncomponentNumSlider.setPaintLabels(true);
		conncomponentNumSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					Population.MAX_CONNECTED_COMP_COUNT = conncomponentNumSlider.getValue();
				} catch (Exception ex) {
				}
				connCompNumber.setText(connCompNumber.getText().substring(0, connCompNumber.getText().indexOf(':') + 1)
						+ " (" + Integer.toString(conncomponentNumSlider.getValue()) + ")");
			}
		});
		connCompNumber.setText(connCompNumber.getText().substring(0, connCompNumber.getText().indexOf(':') + 1) + " ("
				+ Integer.toString(conncomponentNumSlider.getValue()) + ")");

		JLabel uniformrate = new JLabel("Arrange Uniform Rate : ");
		uniformRateSlider.setMajorTickSpacing(10);
		uniformRateSlider.setPaintTicks(true);
		uniformRateSlider.setPaintLabels(false);
		uniformRateSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					Algorithm.uniformRate = uniformRateSlider.getValue() / 100.0;
				} catch (Exception ex) {
				}
				uniformrate.setText(uniformrate.getText().substring(0, uniformrate.getText().indexOf(':') + 1) + " ("
						+ Double.toString(uniformRateSlider.getValue() / 100.0) + ")");
			}
		});
		uniformrate.setText(uniformrate.getText().substring(0, uniformrate.getText().indexOf(':') + 1) + " ("
				+ Double.toString(uniformRateSlider.getValue() / 100.0) + ")");

		JLabel mutationrate = new JLabel("Arrange Mutation Rate : ");
		mutationrateSlider.setMinorTickSpacing(1);
		mutationrateSlider.setMajorTickSpacing(10);
		mutationrateSlider.setPaintTicks(true);
		mutationrateSlider.setPaintLabels(false);
		mutationrateSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					Algorithm.mutationRate = mutationrateSlider.getValue() / 1000.0;
				} catch (Exception ex) {
				}
				mutationrate.setText(mutationrate.getText().substring(0, mutationrate.getText().indexOf(':') + 1) + " ("
						+ Double.toString(mutationrateSlider.getValue() / 1000.0) + ")");

			}
		});
		mutationrate.setText(mutationrate.getText().substring(0, mutationrate.getText().indexOf(':') + 1) + " ("
				+ Double.toString(mutationrateSlider.getValue() / 1000.0) + ")");

		JLabel tournamentsize = new JLabel("Arrange Tournament Size : ");
		tournamentsizeSlider.setMajorTickSpacing(1);
		tournamentsizeSlider.setPaintTicks(true);
		tournamentsizeSlider.setPaintLabels(false);
		tournamentsizeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					Algorithm.tournamentSize = tournamentsizeSlider.getValue();
				} catch (Exception ex) {
				}
				tournamentsize.setText(tournamentsize.getText().substring(0, tournamentsize.getText().indexOf(':') + 1)
						+ " (" + Integer.toString(tournamentsizeSlider.getValue()) + ")");
			}
		});
		tournamentsize.setText(tournamentsize.getText().substring(0, tournamentsize.getText().indexOf(':') + 1) + " ("
				+ Integer.toString(tournamentsizeSlider.getValue()) + ")");

		inputPanel.add(populationsize);
		inputPanel.add(populationsizeSlider);
		inputPanel.add(boardHeight);
		inputPanel.add(boardHeightSlider);
		inputPanel.add(boardWidth);
		inputPanel.add(boardWidthSlider);
		inputPanel.add(componentNum);
		inputPanel.add(componentNumSlider);
		inputPanel.add(connCompNumber);
		inputPanel.add(conncomponentNumSlider);
		inputPanel.add(uniformrate);
		inputPanel.add(uniformRateSlider);
		inputPanel.add(mutationrate);
		inputPanel.add(mutationrateSlider);
		inputPanel.add(tournamentsize);
		inputPanel.add(tournamentsizeSlider);
		inputPanel.add(text);
		inputPanel.add(text);
		inputPanel.add(text2);
		inputPanel.add(text1);
		inputPanel.add(text);
		inputPanel.add(comboMutation);
		//toolPanel.add(fileChooser);
	

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					FileOutputStream out = new FileOutputStream(fileName.getText());
					ObjectOutputStream oout = new ObjectOutputStream(out);
					oout.writeObject(Algorithm.BOARD_HEIGHT);
					oout.writeObject(Algorithm.BOARD_WIDTH);
					oout.writeObject(Individual.getConnectedComponents());
					oout.writeObject(myPop);
					oout.flush();

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});
		
		
		
		JButton generateButton1 = new JButton("Load Generation Data from File");
		generateButton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				load();
				initialize();
				
			}
		});
		
		JButton generateButton = new JButton("Generate Population");
		generateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initialize();
				Individual.generateConnectedGene();
				myPop = new Population(Population.POPULATION_SIZE);
				
			}
		});

		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initialize();
				t1 = new myThread(gaPanel, t, myPop);
			
				populationsizeSlider.setEnabled(false);
				boardHeightSlider.setEnabled(false);
				boardWidthSlider.setEnabled(false);
				componentNumSlider.setEnabled(false);
				conncomponentNumSlider.setEnabled(false);
				t1.start();
			}
		});
		JButton stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				populationsizeSlider.setEnabled(true);
				boardHeightSlider.setEnabled(true);
				boardWidthSlider.setEnabled(true);
				componentNumSlider.setEnabled(true);
				conncomponentNumSlider.setEnabled(true);
				t1.mystop();

			}
		});
		JButton pauseButton = new JButton("Pause");
		pauseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				t1.pause();
			}

		});
		JButton resumeButton = new JButton("Resume");
		resumeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				t1.myresume();
			}
		});
		
		
		toolPanel.add(generateButton);
		toolPanel.add(generateButton1);
		toolPanel.add(startButton);
		toolPanel.add(stopButton);
		toolPanel.add(pauseButton);
		toolPanel.add(resumeButton);
		toolPanel.add(saveButton);
		toolPanel.add(fileName);
		//toolPanel.add(fileChooser);
		JFrame frame = new JFrame("GA");
		frame.setSize(1200, 700);
		frame.setLayout(new BorderLayout());
		frame.add(gaPanel, BorderLayout.CENTER);
		frame.add(toolPanel, BorderLayout.SOUTH);
		frame.add(inputPanel, BorderLayout.WEST);
		frame.add(t, BorderLayout.EAST);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {

		new MainFrame();

	}

}
