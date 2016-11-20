package es.deusto.bigdata.storm.bolt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CleanBolt implements IRichBolt {

	public OutputCollector collector;
	public TopologyContext context;
	public Map<String, Object> stormConf;
	
	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LoggerFactory.getLogger(CleanBolt.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		LOG.info(">----> Clean bolt prepared!");
		this.stormConf = stormConf;
		this.context = context;
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		LOG.info(">----> execute this input:" + input);
		List<Object> toEmit = new ArrayList<>();
		collector.emit(input, toEmit);
		collector.ack(input);
	}

	@Override
	public void cleanup() {
		LOG.info(">----> Cleanup");
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		Fields fields = new Fields("timestamp", "solarRadiation", "energy");
		declarer.declare(fields);
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return this.stormConf;
	}

}
