package es.deusto.bigdata.flume.sink;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;
import org.codehaus.jackson.map.ObjectMapper;

import es.deusto.bigdata.flume.twitter.Tweet;

public class CsvSink extends AbstractSink implements Configurable {

	private String csvFileName;
	private File csvFile;
	
	public Status process() throws EventDeliveryException {
		Status status = null;
		Channel ch = getChannel();
		Transaction txn = ch.getTransaction();
		txn.begin();
		try {
			Event event = ch.take();
			writeToCsv(event);
			txn.commit();
			status = Status.READY;
		} catch (Throwable t) {
			txn.rollback();
			status = Status.BACKOFF;
			if (t instanceof Error) {
				throw (Error) t;
			}
		} finally {
			txn.close();
		}
		return status;
	}

	private void writeToCsv(Event event) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Tweet tweet = mapper.readValue(event.getBody(), Tweet.class);
		FileUtils.writeLines(csvFile, Arrays.asList(tweet.toCsvLine()), true);
	}

	public void configure(Context context) {
		csvFileName = context.getString("csvFileName", "");
		csvFile = new File(csvFileName);
		try {
			FileUtils.writeLines(csvFile, Arrays.asList(Tweet.getCsvHeader()), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
