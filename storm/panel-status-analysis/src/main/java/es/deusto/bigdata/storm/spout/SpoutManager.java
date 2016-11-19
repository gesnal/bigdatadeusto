package es.deusto.bigdata.storm.spout;

import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.ZkHosts;

public class SpoutManager {

	public static KafkaSpout getSpout(com.typesafe.config.Config commandLineConfig) {
		BrokerHosts brokerHosts = new ZkHosts(commandLineConfig.getString("kafka.brokers"));
		SpoutConfig kafkaSpoutConfig = new SpoutConfig(brokerHosts, commandLineConfig.getString("kafka.topic"),
				commandLineConfig.getString("zookeeper"), commandLineConfig.getString("kafka.groupId"));
		return new KafkaSpout(kafkaSpoutConfig);
	}
}
