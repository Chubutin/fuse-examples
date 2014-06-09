package com.smg.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

/**
 * 
 * @author Ramiro Pugh
 * @email rampugh@gmail.com
 * 
 */
public class BrmsDecisionProcessor implements Processor {

	Logger logger = Logger.getLogger(getClass());

	@Override
	public void process(Exchange exchange) throws Exception {

		logger.debug("Iniciando processor de decision");

		exchange.setOut(exchange.getIn());

		// TODO invocacion a BRMS?

		// FIXME HARDCODEADA LA DECISION DE INVOCACION GENERICA. Lo mejor sería
		// rutear dinámicamente por la respuesta del motor de reglas
		
		exchange.setOut(exchange.getIn());

		exchange.getOut().setHeader("decisionFlag", "direct:routeConsultaByBRMS");

		logger.debug("Finalizado processor de decision");

	}
}
