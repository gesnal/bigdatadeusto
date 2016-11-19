package es.deusto.bigdata.flume.bean;

public class DataPointEnergy extends DataPoint {

	private Double energy;
	
	public DataPointEnergy(String csvLine) {
		super(csvLine);
		estimateEnergy();
	}
	
	public void estimateEnergy(){
		this.energy = Math.log1p(solarRadiation + (Math.random()/10== 0 ? Math.random()*2000 : Math.random()*2));
	}
	
	@Override
	public String getCsvLine() {
		return super.getCsvLine() + SEPARATOR + energy;
	}
	
	
}
