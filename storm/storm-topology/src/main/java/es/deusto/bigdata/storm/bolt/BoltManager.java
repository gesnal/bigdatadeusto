package es.deusto.bigdata.storm.bolt;

import org.apache.storm.topology.IRichBolt;

public class BoltManager {

	public static CleanBolt getCleanBolt(com.typesafe.config.Config commandLineConfig) {
		CleanBolt cleanBolt = new CleanBolt();
		return cleanBolt;
	}

	public static AnalysisBolt getAnalysisBolt(com.typesafe.config.Config commandLineConfig) {
		AnalysisBolt analysisBolt = new AnalysisBolt();
		return analysisBolt;
	}

	public static IRichBolt getKafkaSaversBolt(com.typesafe.config.Config commandLineConfig) {
		KafkaSaverBolt kafkaSaverBolt = new KafkaSaverBolt();
		return kafkaSaverBolt;
	}

}
