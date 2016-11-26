package es.deusto.bigdata.storm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.deusto.bigdata.storm.bolt.BoltManager;
import es.deusto.bigdata.storm.commandline.CommandLineManager;
import es.deusto.bigdata.storm.spout.SpoutManager;

public class Main {

	private final static Logger LOG = LoggerFactory.getLogger(Main.class);
		
	public static void main(String[] args) throws Exception {
		com.typesafe.config.Config commandLineConfig = CommandLineManager.getConfig(args);
		TopologyBuilder builder = getTopology(commandLineConfig);
		runTopology("ejercicio1", commandLineConfig.getBoolean("locally"), builder);
	}

	private static TopologyBuilder getTopology(com.typesafe.config.Config commandLineConfig) {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("SpoutEjercicio1", SpoutManager.getSpout(commandLineConfig));
		builder.setBolt("BoltLimpiezaEjercicio1", BoltManager.getCleanBolt(commandLineConfig)).shuffleGrouping("SpoutEjercicio1");
		builder.setBolt("BoltAnalysisEjercicio1", BoltManager.getAnalysisBolt(commandLineConfig)).shuffleGrouping("BoltLimpiezaEjercicio1");
		
		return builder;
	}

	private static void runTopology(String name, Boolean locally, TopologyBuilder builder) {
		Config stormConfig = new Config();
		stormConfig.setDebug(false);
		stormConfig.setNumWorkers(2);
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
