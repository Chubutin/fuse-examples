package com.smg.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

public class ErrorProcessor implements Processor {
	
	Logger logger = Logger.getLogger(getClass());

	@Override
	public void process(Exchange arg0) throws Exception {
		
		logger.error(arg0.getProperty("CamelExceptionCaught", Exception.class));
		arg0.setOut(arg0.getIn());

	}

}
