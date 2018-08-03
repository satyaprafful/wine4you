import java.util.HashMap;
import java.util.List;

public class WineComparer {
	private HashMap<Integer, Wine> indexToWine; 
	private HashMap<Wine, Integer> wineToIndex;
	int Wine1, Wine2, Wine3 = -1;
	int pref1, pref2, pref3 = -1; 
	Wine userWine = null;
	private HashMap<Wine, Double> userWineToOtherWineVarianceMap; 
	DoubleWeightedDirectedGraphImpl<Wine> dGraph;
	
	public WineComparer (HashMap<Integer, Wine> indexToWine, HashMap<Wine, Integer> wineToIndex) {
		this.indexToWine = indexToWine;
		this.wineToIndex = wineToIndex; 
		userWineToOtherWineVarianceMap = new HashMap<>(); 
	}
	
	public Wine createUserWineObject() {
		Wine a = indexToWine.get(Wine1); 
		Wine b = indexToWine.get(Wine2); 
		Wine c = indexToWine.get(Wine3); 
		userWine = new Wine(-1); 
		userWine.setCitricAcid((a.getCitricAcid() + b.getCitricAcid() + c.getCitricAcid())/3.0);
		userWine.setResidualSugar((a.getResidualSugar() + b.getResidualSugar() + c.getResidualSugar())/3.0);
		userWine.setChlorides((a.getChlorides() + b.getChlorides() + c.getChlorides())/3.0);
		userWine.setTotalSulfurDioxide((a.getTotalSulfurDioxide() + b.getTotalSulfurDioxide() + c.getTotalSulfurDioxide())/3.0);
		userWine.setDensity((a.getDensity() + b.getDensity() + c.getDensity())/3.0);
		userWine.setpH((a.getpH() + b.getpH() + c.getpH())/3.0);
		userWine.setSulphates((a.getSulphates() + b.getSulphates() + c.getSulphates())/3.0);
		userWine.setAlcohol((a.getAlcohol() + b.getAlcohol() + c.getAlcohol())/3.0);
		userWine.setQuality((a.getQuality() + b.getQuality() + c.getQuality())/3.0);
		return userWine;
	}
	
	
	public double calculateVariance(Wine wine1, Wine wine2, int pref1, int pref2, int pref3) {
		Double sum = 0.0;
		for (int i = 0; i < wine1.getCharacteristicsMap().size(); i++) {
			double value = 0.0;
			value += (Math.abs(wine1.getCharacteristicsMap().get(i) - 
					wine2.getCharacteristicsMap().get(i))) 
					/ (DataParser.preferenceIndexToRange.get(i));
			if (i == pref1 || i == pref2 || i == pref3) {
				value = value * 2;
			}
			sum += value;
		}
		return sum;
	}
	
	
	public BinaryMinHeapImpl<Wine, Double> calculateProgram1
					(int Wine1, int Wine2, int Wine3, int pref1, int pref2, int pref3){
		this.Wine1 = Wine1;
		this.Wine2 = Wine2;
		this.Wine3 = Wine3;
		this.pref1 = pref1;
		this.pref2 = pref2;
		this.pref3 = pref3;
		BinaryMinHeapImpl<Wine, Double> recommendationsHeap = new BinaryMinHeapImpl<>(); 
		createUserWineObject();
		for (Wine w: wineToIndex.keySet()) {
			double variance = calculateVariance(userWine, w, pref1, pref2, pref3);
			userWineToOtherWineVarianceMap.put(w, variance);
			recommendationsHeap.add(w, variance);
		}		
		return recommendationsHeap;
	}
	
	public void createGraphForProgram2() {
		DoubleWeightedDirectedGraphImpl<Wine> dGraph = new DoubleWeightedDirectedGraphImpl
				<Wine> (wineToIndex.size(), wineToIndex);
		double tresholdValue = 0.7; 
		for (Wine w1: wineToIndex.keySet()) {
			for (Wine w2: wineToIndex.keySet()) {
				if (!wineToIndex.get(w1).equals(wineToIndex.get(w2))) {
					double variance = calculateVariance(w1, w2, pref1, pref2, pref3);
					if (variance < tresholdValue) {
						dGraph.addEdge(w1, w2, variance);
					}
				}
			}
		}	
		this.dGraph = dGraph;
	}
	
	public List<Wine> calculateProgram2(DoubleWeightedDirectedGraphImpl<Wine> dGraph, Wine src, Wine tgt){	
		Dijkstras shortestPathCalc = new Dijkstras();
		List<Wine> outputList = shortestPathCalc.getShortestPath(dGraph, src, tgt);
		return outputList;
	}
}
