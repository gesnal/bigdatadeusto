package es.deusto.bigdata.flume.interceptor;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeustoTwitterInterceptor implements Interceptor {

	private final static Logger LOG = LoggerFactory.getLogger(DeustoTwitterInterceptor.class);

	public Event intercept(Event event) {
		Event interceptedEvent = process(event);
		return interceptedEvent;
	}

	public List<Event> intercept(List<Event> events) {
		List<Event> interceptedEvents = new ArrayList<Event>();
		for (Event event : events) {
			Event interceptedEvent = process(event);
			interceptedEvents.add(interceptedEvent);
		}
		return interceptedEvents;
	}

	private Event process(Event event) {
		byte[] bytes = (byte[]) event.getBody();
		DatumReader<AvroSchemaTwitter> readerAvro = new SpecificDatumReader<AvroSchemaTwitter>(
				AvroSchemaTwitter.getClassSchema());
		BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
		String tweetText = "";
		AvroSchemaTwitter tweet = null;
		try {
			tweet = readerAvro.read(null, decoder);
			tweetText = tweet.getText().toString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return EventBuilder.withBody(tweetText, Charset.defaultCharset());
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
			DeustoTwitterInterceptor interceptor = new DeustoTwitterInterceptor();
			return interceptor;
		}

	}
}
