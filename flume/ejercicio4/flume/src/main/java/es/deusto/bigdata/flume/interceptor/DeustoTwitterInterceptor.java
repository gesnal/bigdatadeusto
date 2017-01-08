package es.deusto.bigdata.flume.interceptor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.commons.io.FileUtils;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.event.EventBuilder;
import org.apache.flume.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.deusto.bigdata.flume.twitter.AvroSchemaTwitter;

public class DeustoTwitterInterceptor implements Interceptor {

	private static final String TEMP_FILES_FOLDER = "C:\\tmp\\";
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
		File file = null;
		try {
			file = new File(TEMP_FILES_FOLDER + System.currentTimeMillis() + ".tmp");
			byte[] bytes = (byte[]) event.getBody();
			DatumReader<AvroSchemaTwitter> userDatumReader = new SpecificDatumReader<AvroSchemaTwitter>(
					AvroSchemaTwitter.getClassSchema());
			FileUtils.writeByteArrayToFile(file, bytes);

			try (DataFileReader<AvroSchemaTwitter> dataFileReader = new DataFileReader<AvroSchemaTwitter>(file,
					userDatumReader)) {
				while (dataFileReader.hasNext()) {
					Object o = dataFileReader.next();
					String json = o.toString();
					FileUtils.forceDelete(file);
					return EventBuilder.withBody(json, Charset.defaultCharset());
				}
			}
		} catch (Exception e) {
		} finally {
			if (file != null && file.exists()) {
				try {
					FileUtils.forceDelete(file);
				} catch (IOException e) {
				}
			}
		}

		return null;

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
