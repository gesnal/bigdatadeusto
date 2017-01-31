package es.deusto.bigdata.listener.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaManager {

	private Producer<String, String> producer;
	private KafkaConsumer<String, String> consumer;

	private static KafkaManager instance;
	private final static Logger LOG = LoggerFactory.getLogger(KafkaManager.class);

	public static KafkaManager getInstance() {
		if (instance == null) {
			instance = new KafkaManager();
		}

		return instance;
	}

	private KafkaManager() {
	}


	public void produce(String topic, String message) {
		Producer<String, String> producer = getProducer();
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, message);
		producer.send(record);
	}

	private Producer<String, String> getProducer() {
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
	
	public void closeProducer(){
		producer.close();
	}

	public List<String> consumeList(String topic, String groupId) {
		KafkaConsumer<String, String> consumer = getConsumer(groupId);
		List<String> messages = new ArrayList<>();
		consumer.subscribe(Arrays.asList(topic));
		ConsumerRecords<String, String> records = consumer.poll(100);
		for (ConsumerRecord<String, String> record : records) {
			messages.add(record.value());
		}

		LOG.error(">----> " + messages.size());
		return messages;
	}

	private KafkaConsumer<String, String> getConsumer(String groupId) {
		if (consumer == null) {
			Properties props = new Properties();
//			props.put("bootstrap.servers", "localhost:9092");
			props.put("bootstrap.servers", "bigd-hadoop1.deusto.es:6667");
			props.put("group.id", groupId);
			props.put("enable.auto.commit", "true");
			props.put("auto.commit.interval.ms", "1000");
			props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			consumer = new KafkaConsumer<>(props);
		}

		return consumer;
	}
}
