package com.smg.camel.processor;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

public class SetJSONBodyProcessor implements Processor {

	Logger logger = Logger.getLogger(getClass());
	
	private static String FILE_IN = "src/main/resources/persona.json";

	@Override
	public void process(Exchange exchange) throws Exception {

		logger.debug("Entro en el processor de set body");
		exchange.getOut().setHeaders(exchange.getIn().getHeaders());
		exchange.getOut().setBody(readFile(FILE_IN, Charset.defaultCharset()),
				String.class);

	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

}
