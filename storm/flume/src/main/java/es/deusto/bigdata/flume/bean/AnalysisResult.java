package es.deusto.bigdata.flume.bean;

import java.util.Calendar;

import com.google.gson.Gson;

public class AnalysisResult {

	private Long timestamp;
	private Double energy;
	private Double modelledEnergy;
	private Boolean valid;

	public AnalysisResult() {
	}

	public AnalysisResult(Long timestamp, Double energy, Double modelledEnergy, Boolean valid) {
		this.timestamp = timestamp;
		this.energy = energy;
		this.modelledEnergy = modelledEnergy;
		this.valid = valid;
	}

	public static AnalysisResult getInstance(String json) {
		return new Gson().fromJson(json, AnalysisResult.class);
	}

	public static AnalysisResult empty() {
		AnalysisResult dataPoint = new AnalysisResult();
		Calendar now = Calendar.getInstance();
		dataPoint.setTimestamp(now.getTime().getTime());
		return dataPoint;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Double getEnergy() {
		return energy;
	}

	public void setEnergy(Double energy) {
		this.energy = energy;
	}

	public Double getModelledEnergy() {
		return modelledEnergy;
	}

	public void setModelledEnergy(Double modelledEnergy) {
		this.modelledEnergy = modelledEnergy;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}
}
