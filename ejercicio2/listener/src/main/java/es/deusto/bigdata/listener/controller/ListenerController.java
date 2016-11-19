package es.deusto.bigdata.listener.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListenerController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ListenerController.class);
	
	@RequestMapping(value="/listener", method= RequestMethod.PUT)
    public String index(HttpServletRequest request) throws IOException {
		InputStream inputStream = request.getInputStream();
		String message = IOUtils.toString(inputStream, "UTF-8");
		LOGGER.info("..:::LISTENED:::.." + message);
        return "OK";
    }
}
