package es.deusto.bigdata.listener.controller.rest;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.deusto.bigdata.flume.bean.Data;
import es.deusto.bigdata.listener.controller.web.ListenerWebController;
import es.deusto.bigdata.listener.kafka.KafkaManager;

@RestController
public class ListernerRestController {

	private final KafkaManager kafkaManager = KafkaManager.getInstance();
	private final static Logger LOGGER = LoggerFactory.getLogger(ListenerWebController.class);
	private final static String TOPIC = "prueba2";

	@RequestMapping(value = "/write-kafka", method = RequestMethod.PUT)
	public String listener(HttpServletRequest request) throws Exception {
		InputStream inputStream = request.getInputStream();
		String messageJson = IOUtils.toString(inputStream, "UTF-8");
		Data data = Data.getInstance(messageJson);
		kafkaManager.produce(TOPIC, data.toJson());
		LOGGER.info("..:::WROTE IN KAFKA:::.." + data + "\n");
		Thread.sleep(100);
		return "OK";
	}
}
