package es.deusto.bigdata.flume.interceptor;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.deusto.bigdata.flume.bean.Data;

public class DeustoInterceptor implements Interceptor {

	private final static Logger LOG = LoggerFactory.getLogger(DeustoInterceptor.class);
	
	public Event intercept(Event event) {
		Event interceptedEvent = process(event);
		return interceptedEvent;
	}

	public List<Event> intercept(List<Event> events) {
		List<Event> interceptedEvents = new ArrayList<Event>();
		for(Event event : events){
			Event interceptedEvent = process(event);
			interceptedEvents.add(interceptedEvent);
		}
		return interceptedEvents;
	}

	private Event process(Event event) {
		String csvLine = new String(event.getBody());
		Data dataPoint = new Data(csvLine);
		return EventBuilder.withBody(dataPoint.toJson(), Charset.defaultCharset());
	}

	public void close() {
	}

	public void initialize() {
	}
	
	public static class Builder implements Interceptor.Builder {

		public void configure(Context context) {
		}

		public Interceptor build() {
			LOG.info(">------------------------> Building interceptor");
			DeustoInterceptor interceptor = new DeustoInterceptor();
			return interceptor;
		}

	}
}
