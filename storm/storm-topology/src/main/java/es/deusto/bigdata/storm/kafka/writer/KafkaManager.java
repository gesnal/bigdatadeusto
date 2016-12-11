package es.deusto.bigdata.storm.kafka.writer;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaManager {

	private final static Logger LOG = LoggerFactory.getLogger(KafkaManager.class);
	
	private static Producer<String, String> producer;
	
	public static void produce(String topic, String message) {
		LOG.debug("Producing in the topic'" + topic + "', the message '" + message + "';");
		Producer<String, String> producer = getProducer();
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, message);
		producer.send(record);
	}

	private static Producer<String, String> getProducer() {
		if (producer == null) {
			Properties props = new Properties();
			props.put("bootstrap.servers", "localhost:9092");
			props.put("acks", "all");
			props.put("retries", 0);
			props.put("batch.size", 16384);
			props.put("linger.ms", 1);
			props.put("buffer.memory", 33554432);
			props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			producer = new KafkaProducer<>(props);
		}
		return producer;
	}

	public void produce(String topic, String... messages) {
		Producer<String, String> producer = getProducer();
		for (String message : messages) {
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, message);
			producer.send(record);
		}
	}

	public void closeProducer() {
		producer.close();
	}

}
