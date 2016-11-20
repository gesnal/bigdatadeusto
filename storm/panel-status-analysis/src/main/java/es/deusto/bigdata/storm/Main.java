package es.deusto.bigdata.storm;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.deusto.bigdata.storm.bolt.BoltManager;
import es.deusto.bigdata.storm.commandline.CommandLineManager;
import es.deusto.bigdata.storm.spout.SpoutManager;

public class Main {

	private final static Logger LOG = LoggerFactory.getLogger(Main.class);
		
	public static void main(String[] args) throws Exception {
		com.typesafe.config.Config commandLineConfig = CommandLineManager.getConfig(args);
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("SpoutEjercicio1", SpoutManager.getSpout(commandLineConfig));
		builder.setBolt("BoltLimpiezaEjercicio1", BoltManager.getCleanBolt(commandLineConfig), 3)
				.fieldsGrouping("1", new Fields("word")).fieldsGrouping("2", new Fields("word"));
		builder.setBolt("BoltAnalysisEjercicio1", BoltManager.getAnalysisBolt(commandLineConfig)).globalGrouping("1");

		Map<String, Object> stormConfig = new HashMap<>();
		stormConfig.put(Config.TOPOLOGY_WORKERS, 4);

		runTopology("ejercicio1", commandLineConfig.getBoolean("locally"), builder, stormConfig);
		StormSubmitter.submitTopology("mytopology", stormConfig, builder.createTopology());
	}

	private static void runTopology(String name, Boolean locally, TopologyBuilder builder, Map<String, Object> stormConfig) {
		LOG.info("Running topology '" + name + "' locally: " + locally);
		if (locally) {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology(name, stormConfig, builder.createTopology());
		} else {
			try {
				StormSubmitter.submitTopology(name, stormConfig, builder.createTopology());
			} catch (Exception e) {
				LOG.error("Exception submitting topology", e);
			}
		}
	}

}
