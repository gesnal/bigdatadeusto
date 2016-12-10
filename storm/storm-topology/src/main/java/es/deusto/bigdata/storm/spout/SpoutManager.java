package es.deusto.bigdata.storm.spout;

import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.RawMultiScheme;

public class SpoutManager {

	public static KafkaSpout getSpout(com.typesafe.config.Config commandLineConfig) {
		BrokerHosts brokerHosts = new ZkHosts(commandLineConfig.getString("zookeeper"));
		SpoutConfig kafkaSpoutConfig = new SpoutConfig(
				brokerHosts, 
				commandLineConfig.getString("kafka.topic"),
				"", 
				commandLineConfig.getString("kafka.groupId"));
		kafkaSpoutConfig.scheme = new RawMultiScheme();
		return new KafkaSpout(kafkaSpoutConfig);
	}
}
