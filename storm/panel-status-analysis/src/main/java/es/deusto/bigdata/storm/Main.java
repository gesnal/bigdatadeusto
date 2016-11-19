package es.deusto.bigdata.storm;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import es.deusto.bigdata.storm.bolt.BoltManager;
import es.deusto.bigdata.storm.commandline.CommandLineManager;
import es.deusto.bigdata.storm.spout.SpoutManager;

public class Main {

	public static void main(String[] args) throws Exception {
		com.typesafe.config.Config commandLineConfig = CommandLineManager.getConfig(args);
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("SpoutEjercicio1", SpoutManager.getSpout(commandLineConfig));
		builder.setBolt("BoltLimpiezaEjercicio1", BoltManager.getCleanBolt(commandLineConfig), 3)
				.fieldsGrouping("1", new Fields("word")).fieldsGrouping("2", new Fields("word"));
		builder.setBolt("BoltAnalysisEjercicio1", BoltManager.getAnalysisBolt(commandLineConfig)).globalGrouping("1");

		Map<String, Object> conf = new HashMap<>();
		conf.put(Config.TOPOLOGY_WORKERS, 4);

		StormSubmitter.submitTopology("mytopology", conf, builder.createTopology());
	}

}
