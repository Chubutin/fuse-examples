package com.fluxit.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

public class DefaultProcessor implements Processor {
	
	Logger logger = Logger.getLogger(getClass());

	@Override
	public void process(Exchange arg0) throws Exception {
		
		logger.debug("Entro en el processor");
		arg0.setOut(arg0.getIn());

	}

}
