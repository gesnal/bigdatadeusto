package es.deusto.bigdata.storm.bolt;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.deusto.bigdata.flume.bean.AnalysisResult;
import es.deusto.bigdata.storm.kafka.writer.KafkaManager;

public class KafkaSaverBolt implements IRichBolt {

	public OutputCollector collector;
	public TopologyContext context;
	public Map<String, Object> stormConf;
	
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LoggerFactory.getLogger(KafkaSaverBolt.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.stormConf = stormConf;
		this.context = context;
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		Long timestamp = input.getLong(0);
		Double energy = input.getDouble(1);
		Double modelledEnergy = input.getDouble(2);
		Boolean valid = input.getBoolean(3);
		AnalysisResult result = new AnalysisResult(timestamp, energy, modelledEnergy, valid);
		KafkaManager.produce("result", result.toJson());
		collector.ack(input);
	}

	@Override
	public void cleanup() {
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		Fields fields = new Fields();
		declarer.declare(fields);
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return this.stormConf;
	}

}
