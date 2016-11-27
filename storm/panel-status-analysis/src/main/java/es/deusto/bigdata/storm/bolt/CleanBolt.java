package es.deusto.bigdata.storm.bolt;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
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

import es.deusto.bigdata.flume.bean.Data;

public class CleanBolt implements IRichBolt {

	public OutputCollector collector;
	public TopologyContext context;
	public Map<String, Object> stormConf;

	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LoggerFactory.getLogger(CleanBolt.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.stormConf = stormConf;
		this.context = context;
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		String json = new String(input.getBinary(0), StandardCharsets.UTF_8);
		Data data = Data.getInstance(json);
		List<Object> toEmit = new ArrayList<>();
		toEmit.add(data.getTimestamp());
		toEmit.add(data.getSolarRadiation());
		toEmit.add(data.getEnergy());
		if (isNotNight(data.getTimestamp())) {
			collector.emit(input, toEmit);
		}
		collector.ack(input);
	}

	private boolean isNotNight(Long timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp);
		int hour = calendar.get(Calendar.HOUR);
		return 6 <= hour && hour <= 19;
	}

	@Override
	public void cleanup() {
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
