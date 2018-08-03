import java.util.HashMap;

public class Wine {

	private double fixedAcidity;
	private double volatileAcidity;
	private double citricAcid;
	private double residualSugar;
	private double chlorides;
	private double freeSulfurDioxide;
	private double totalSulfurDioxide;
	private double density;
	private double pH;
	private double sulphates;
	private double alcohol;
	private double quality;
	private int wineID; 
	private HashMap<Integer, Double> preferenceIndexToPreference = null; 
	
	public double getFixedAcidity() {
		return fixedAcidity;
	}

	public void setFixedAcidity(double fixedAcidity) {
		this.fixedAcidity = fixedAcidity;
	}

	public double getVolatileAcidity() {
		return volatileAcidity;
	}

	public void setVolatileAcidity(double volatileAcidity) {
		this.volatileAcidity = volatileAcidity;
	}

	public double getCitricAcid() {
		return citricAcid;
	}

	public void setCitricAcid(double citricAcid) {
		preferenceIndexToPreference.put(0,citricAcid);
		this.citricAcid = citricAcid;
	}

	public double getResidualSugar() {
		return residualSugar;
	}

	public void setResidualSugar(double residualSugar) {
		preferenceIndexToPreference.put(1,residualSugar);
		this.residualSugar = residualSugar;
	}

	public double getChlorides() {
		return chlorides;
	}

	public void setChlorides(double chlorides) {
		preferenceIndexToPreference.put(2,chlorides);
		this.chlorides = chlorides;
	}

	public double getFreeSulfurDioxide() {
		return freeSulfurDioxide;
	}

	public void setFreeSulfurDioxide(double freeSulfurDioxide) {
		this.freeSulfurDioxide = freeSulfurDioxide;
	}

	public double getTotalSulfurDioxide() {
		return totalSulfurDioxide;
	}

	public void setTotalSulfurDioxide(double totalSulfurDioxide) {
		preferenceIndexToPreference.put(3,totalSulfurDioxide);
		this.totalSulfurDioxide = totalSulfurDioxide;
	}

	public double getDensity() {
		return density;
	}

	public void setDensity(double density) {
		preferenceIndexToPreference.put(4,density);
		this.density = density;
	}

	public double getpH() {
		return pH;
	}

	public void setpH(double pH) {
		preferenceIndexToPreference.put(5,pH);
		this.pH = pH;
	}

	public double getSulphates() {
		return sulphates;
	}

	public void setSulphates(double sulphates) {
		preferenceIndexToPreference.put(6,sulphates);
		this.sulphates = sulphates;
	}

	public double getAlcohol() {
		return alcohol;
	}

	public void setAlcohol(double alcohol) {
		preferenceIndexToPreference.put(7,alcohol);
		this.alcohol = alcohol;
	}

	public double getQuality() {
		return quality;
	}

	public void setQuality(double quality) {
		preferenceIndexToPreference.put(8,quality);	
		this.quality = quality;
	}

	public Wine(int ID) {
		preferenceIndexToPreference = new HashMap<>(); 
		this.wineID = ID; 
	}
	
	public int getWineID() {
		return wineID; 
	}
	
	@Override
	public String toString() {
		return String.valueOf(wineID);  
	}
	
	@Override 
	public boolean equals(Object otherWine) {
        if (otherWine == null ||!(otherWine instanceof Wine)){
            return false;
        }
        else {
            Wine wine2 = (Wine) otherWine; 
            return (wineID == wine2.getWineID());
        }
    }     
 	
	public HashMap<Integer, Double> getCharacteristicsMap(){
		return preferenceIndexToPreference; 
	}	
}


