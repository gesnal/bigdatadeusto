package es.deusto.bigdata.storm.bolt;

public class BoltManager {

	public static CleanBolt getCleanBolt(com.typesafe.config.Config commandLineConfig) {
		CleanBolt cleanBolt = new CleanBolt();
		return cleanBolt;
	}

	public static AnalysisBolt getAnalysisBolt(com.typesafe.config.Config commandLineConfig) {
		AnalysisBolt analysisBolt = new AnalysisBolt();
		return analysisBolt;
	}

}
