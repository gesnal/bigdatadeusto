package es.deusto.bigdata.storm.flume.sink;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.flume.Channel;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.EventDeliveryException;
import org.apache.flume.Transaction;
import org.apache.flume.conf.Configurable;
import org.apache.flume.sink.AbstractSink;

public class DeustoSink extends AbstractSink implements Configurable {

	private String listenerHost;

	public Status process() throws EventDeliveryException {
		Status status = null;
		Channel ch = getChannel();
		Transaction txn = ch.getTransaction();
		txn.begin();
		try {
			Event event = ch.take();
			sendToListener(event);
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

	private void sendToListener(Event event) throws Exception {
		String msg = new String(event.getBody());
		URL url = new URL(listenerHost + "/");
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		OutputStreamWriter out = new OutputStreamWriter(
		    httpCon.getOutputStream());
		out.write(msg);
		out.close();
		httpCon.getInputStream();
	}

	public void configure(Context context) {
		listenerHost = context.getString("listenerHost", "");
	}

}
