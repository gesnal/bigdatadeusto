package es.deusto.bigdata.flume.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class DataPoint {

	protected Long timestamp;
	protected Double preasure;
	protected Double humidity;
	protected Integer windSpeed;
	protected Integer solarRadiation;
	protected Double temperature;

	protected static final String SEPARATOR = ",";
	private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(FORMAT);
	private static final Logger LOG = LoggerFactory.getLogger(DataPoint.class);

	public DataPoint(String csvLine) {
		String[] values = csvLine.split(SEPARATOR);
		timestamp = getTimestamp(values);
		preasure = Double.valueOf(values[2]);
		humidity = Double.valueOf(values[4]);
		windSpeed = Integer.valueOf(values[5]);
		solarRadiation = Integer.valueOf(values[6]);
		temperature = Double.valueOf(values[8]);
	}

	public DataPoint() {
	}

	private Long getTimestamp(String[] values) {
		try {
			return DATE_FORMAT.parse(values[0]).getTime();
		} catch (ParseException e) {
			LOG.error("Unparseable date for format '" + FORMAT + "' : " + values[0]);
		}
		return null;
	}
	
	private String getDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(this.timestamp);
		return DATE_FORMAT.format(calendar.getTime());
	}

	public static DataPoint getInstance(String json) {
		return new Gson().fromJson(json, DataPoint.class);
	}

	public static DataPoint empty() {
		DataPoint dataPoint = new DataPoint();
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

	public Double getPreasure() {
		return preasure;
	}

	public void setPreasure(Double preasure) {
		this.preasure = preasure;
	}

	public Double getHumidity() {
		return humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public Integer getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(Integer windSpeed) {
		this.windSpeed = windSpeed;
	}

	public Integer getSolarRadiation() {
		return solarRadiation;
	}

	public void setSolarRadiation(Integer solarRadiation) {
		this.solarRadiation = solarRadiation;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public String getCsvLine() {
		return getDate() + SEPARATOR + preasure + SEPARATOR + humidity + SEPARATOR + windSpeed
				+ SEPARATOR + solarRadiation + SEPARATOR + temperature;
	}

	@Override
	public String toString() {
		return "DataPoint [timestamp=" + timestamp + ", preasure=" + preasure + ", humidity=" + humidity
				+ ", windSpeed=" + windSpeed + ", solarRadiation=" + solarRadiation + ", temperature=" + temperature
				+ "]";
	}

}
