import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.List;

public class DataParser {

	static HashMap<Integer, Wine> wineIndexToWineObj;
	static HashMap<Wine, Integer> wineObjToWineIndex;
	static HashMap<Integer, Double> preferenceIndexToRange = new HashMap<>();
	static WineComparer wineComparator;

	public static void parseData(String file) {

		wineIndexToWineObj = new HashMap<Integer, Wine>();
		wineObjToWineIndex = new HashMap<Wine, Integer>();
		BufferedReader br = null;
		String line = "";
		String split = ",";

		try {

			br = new BufferedReader(new FileReader(file));
			int count = 1;
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] wine = line.split(split);
				Wine newWine = new Wine(count);
				newWine.setFixedAcidity(Double.valueOf(wine[0]));
				newWine.setVolatileAcidity(Double.parseDouble(wine[1]));
				newWine.setCitricAcid(Double.parseDouble(wine[2]));
				newWine.setResidualSugar(Double.parseDouble(wine[3]));
				newWine.setChlorides(Double.parseDouble(wine[4]));
				newWine.setFreeSulfurDioxide(Double.parseDouble(wine[5]));
				newWine.setTotalSulfurDioxide(Double.parseDouble(wine[6]));
				newWine.setDensity(Double.parseDouble(wine[7]));
				newWine.setpH(Double.parseDouble(wine[8]));
				newWine.setSulphates(Double.parseDouble(wine[9]));
				newWine.setAlcohol(Double.parseDouble(wine[10]));
				newWine.setQuality(Double.parseDouble(wine[11]));
				wineIndexToWineObj.put(count, newWine);
				wineObjToWineIndex.put(newWine, count);
				count++;
			}

			wineComparator = new WineComparer(wineIndexToWineObj, wineObjToWineIndex);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static class UserInterface implements Runnable {
		public void run() {

			final JFrame frame = new JFrame("Wine 4 You");
			frame.setLocation(500, 200);
			frame.setLayout(new BorderLayout());

			final JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			frame.add(panel, BorderLayout.CENTER);
			frame.add(new JLabel(new ImageIcon("wineGlasses.jpg")), BorderLayout.NORTH);
			frame.add(new JLabel(" "), BorderLayout.SOUTH);
			frame.add(new JLabel("  "), BorderLayout.WEST);
			frame.add(new JLabel("  "), BorderLayout.EAST);

			final JButton instructions = new JButton("Instructions..");
			instructions.setFont(new Font("Times", Font.BOLD, 15));

			instructions.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(frame,
							" Welcome to WINE 4 YOU! Below we have provided two"
									+ " \n recommendation methods for you to able to choose the bottle of choice for your next event!"
									+ "\n \n Characteristic Based Recommendations are recommendations that depend on your"
									+ "\n favorite three wines and three characteristics you value most in a bottle. "
									+ "\n \n Shortest Path Recommendations are recommendations that provide the shortest"
									+ "\n path of pairwise similar bottles, for you to be able to determine a whole variety of "
									+ "\n different drinks you may enjoy for your next event!");
				}
			});

			panel.add(instructions, BorderLayout.NORTH);

			final JButton characteristicRecommendation = new JButton("Charecteristic Based Recommendations");
			characteristicRecommendation.setFont(new Font("Times", Font.BOLD, 15));

			characteristicRecommendation.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					String wine1Input = "";

					do {
						wine1Input = JOptionPane
								.showInputDialog("Enter the integer number from 1-1999 " + "of your favorite wine.");

					} while (wine1Input.equals("") || Integer.parseInt(wine1Input) < 1
							|| Integer.parseInt(wine1Input) > 1999);

					int wine1 = Integer.parseInt(wine1Input);
					

					String wine2Input = "";

					do {
						wine2Input = JOptionPane
								.showInputDialog("Enter the integer number from 1-1999 " + "of your second favorite wine.");

					} while (wine2Input.equals("") || Integer.parseInt(wine2Input) < 1
							|| Integer.parseInt(wine2Input) > 1999);

					int wine2 = Integer.parseInt(wine2Input);
					

					String wine3Input = "";

					do {
						wine3Input = JOptionPane
								.showInputDialog("Enter the integer number from 1-1999 " + "of your third favorite wine.");

					} while (wine3Input.equals("") || Integer.parseInt(wine3Input) < 1
							|| Integer.parseInt(wine3Input) > 1999);

					int wine3 = Integer.parseInt(wine3Input);
					
					
					String pref1Input = "";
					
					do {
						pref1Input = JOptionPane
								.showInputDialog("Now choose the three characteristics "
										+ " you find most important in a wine. \n We will use them to weight the wine results"
										+ "so that the wines suggested will have results similar to the"
										+ " characteristics you pick."
										+ "\n \n Enter the integer number of the characteristic you find "
										+ "most important." + "\n 1. Citric Acid" + "\n 2. Residual Sugar"
										+ "\n 3. Chlorides" + "\n 4. Total Sulfur Dioxde" + "\n 5. Density" + "\n 6. pH"
										+ "\n 7. Sulphates" + "\n 8. Alcohol" + "\n 9. Quality");

					} while (pref1Input.equals("") || Integer.parseInt(pref1Input) < 1
							|| Integer.parseInt(pref1Input) > 9);

					int characteristic1 = Integer.parseInt(pref1Input);
					
					
					String pref2Input = "";
					
					do {
						pref2Input = JOptionPane
								.showInputDialog("Enter the integer number of the " + "characteristic you find"
										+ " second most important." + "\n \n 1. Citric Acid" + "\n 2. Residual Sugar"
										+ "\n 3. Chlorides" + "\n 4. Total Sulfur Dioxde" + "\n 5. Density" + "\n 6. pH"
										+ "\n 7. Sulphates" + "\n 8. Alcohol" + "\n 9. Quality");

					} while (pref2Input.equals("") || Integer.parseInt(pref2Input) < 1
							|| Integer.parseInt(pref2Input) > 9);

					int characteristic2 = Integer.parseInt(pref2Input);

					
					String pref3Input = "";
					
					do {
						pref3Input = JOptionPane
								.showInputDialog("Enter the integer number of the " + "characteristic you find"
										+ " third most important." + "\n \n 1. Citric Acid" + "\n 2. Residual Sugar"
										+ "\n 3. Chlorides" + "\n 4. Total Sulfur Dioxde" + "\n 5. Density" + "\n 6. pH"
										+ "\n 7. Sulphates" + "\n 8. Alcohol" + "\n 9. Quality");

					} while (pref3Input.equals("") || Integer.parseInt(pref3Input) < 1
							|| Integer.parseInt(pref3Input) > 9);

					int characteristic3 = Integer.parseInt(pref3Input);


					BinaryMinHeapImpl<Wine, Double> resultingHeap = wineComparator.calculateProgram1(wine1, wine2,
							wine3, characteristic1, characteristic2, characteristic3);

					
					String numOfResultsInput = "";

					do {
						numOfResultsInput = JOptionPane
								.showInputDialog("State how many results you want");

					} while (numOfResultsInput.equals("") || Integer.parseInt(numOfResultsInput) < 1);

					int numOfResults = Integer.parseInt(numOfResultsInput);
					

					String answer = "";

					while (numOfResults > 0 && !resultingHeap.isEmpty()) {
						answer += wineObjToWineIndex.get(resultingHeap.extractMin());
						answer += "\n";
						numOfResults--;
					}

					JOptionPane.showMessageDialog(frame, "These are the recommendations we found based on"
							+ " your input characteristics: " + "\n \n" + answer);

					frame.pack();
				}
			});

			panel.add(characteristicRecommendation, BorderLayout.CENTER);

			final JButton shortestPathRecommendation = new JButton("Shortest Path Recommendations");
			shortestPathRecommendation.setFont(new Font("Times", Font.BOLD, 15));

			shortestPathRecommendation.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					String wine1Input = "";

					do {
						wine1Input = JOptionPane
								.showInputDialog("Enter the integer number from 1-1999 " + "of your source wine.");

					} while (wine1Input.equals("") || Integer.parseInt(wine1Input) < 1
							|| Integer.parseInt(wine1Input) > 1999);

					int srcWine = Integer.parseInt(wine1Input);
					

					String wine2Input = "";

					do {
						wine2Input = JOptionPane
								.showInputDialog("Enter the integer number from 1-1999 " + "of your target wine.");

					} while (wine2Input.equals("") || Integer.parseInt(wine2Input) < 1
							|| Integer.parseInt(wine2Input) > 1999);

					int tgtWine = Integer.parseInt(wine2Input);

					List<Wine> outputList = wineComparator.calculateProgram2(wineComparator.dGraph,
							wineIndexToWineObj.get(srcWine), wineIndexToWineObj.get(tgtWine));

					if (outputList.size() == 0) {
						JOptionPane.showMessageDialog(frame, "We could not find any similar wines between "
								+ "your input wines... please try again.");
					} else {

						JOptionPane.showMessageDialog(frame, "Here is the trail of similar wines that we found"
								+ " between your input wines: " + "\n \n" + outputList.toString());
					}

					frame.pack();
				}
			});

			panel.add(shortestPathRecommendation, BorderLayout.SOUTH);

			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);

		}

	}

	public static void calculateRanges() {
		for (int i = 0; i < wineIndexToWineObj.get(1).getCharacteristicsMap().size(); i++) {
			Double min = 1000.0;
			Double max = 0.0;
			for (Wine w : wineObjToWineIndex.keySet()) {
				min = Math.min(min, w.getCharacteristicsMap().get(i));
				max = Math.max(max, w.getCharacteristicsMap().get(i));
			}
			Double range = max - min;
			preferenceIndexToRange.put(i, range);
		}
	}

	public static void main(String[] args) {
		parseData("food-wine-quality/winequality.csv");
		calculateRanges();
		wineComparator.createGraphForProgram2();
		SwingUtilities.invokeLater(new UserInterface());
	}

}