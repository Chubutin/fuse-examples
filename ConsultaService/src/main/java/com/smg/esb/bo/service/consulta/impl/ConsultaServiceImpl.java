package com.smg.esb.bo.service.consulta.impl;

import org.apache.camel.Exchange;
import org.apache.log4j.Logger;

import com.smg.esb.bo.service.consulta.ConsultaService;


/**
 * 
 * @author Ramiro Pugh 
 * 
 */
public class ConsultaServiceImpl implements ConsultaService {
	
	Logger logger = Logger.getLogger(getClass());

	@Override
	public void process(Exchange exchange) throws Exception {
		
		logger.debug("Ejecutando el servicio de consulta");
		
		exchange.setOut(exchange.getIn());
		
		logger.debug("Finalización del servicio de consulta");
		
	}

}
