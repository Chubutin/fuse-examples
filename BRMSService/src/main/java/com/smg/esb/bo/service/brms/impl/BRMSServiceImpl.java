package com.smg.esb.bo.service.brms.impl;

import org.apache.camel.Exchange;
import org.apache.log4j.Logger;

import com.smg.esb.bo.service.brms.BRMSService;


/**
 * 
 * @author Ramiro Pugh 
 * 
 */
public class BRMSServiceImpl implements BRMSService {
	
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
