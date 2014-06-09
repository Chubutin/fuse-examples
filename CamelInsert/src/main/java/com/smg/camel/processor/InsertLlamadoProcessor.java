package com.smg.camel.processor;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.sql.SqlConstants;
import org.apache.log4j.Logger;

/**
 * 
 * @author Ramiro Pugh
 * @company www.fluxit.com.ar
 * @email ramiro.pugh@fluxit.com.ar
 * 
 */
public class InsertLlamadoProcessor implements Processor {

	Logger logger = Logger.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public void process(Exchange exchange) throws Exception {

		logger.debug("Invocando al processor de insercion de llamado");

		Map<String, Object> mapBody = exchange.getProperty("mapBody", Map.class);

		// TODO ojo con los casts!

		Integer prepaga = (Integer) mapBody.get("prepaga");
		String contra = (String) mapBody.get("contra");
		Integer finalizado = (Integer) mapBody.get("finalizado");

		String query = "INSERT INTO CRM_LLAMADOS (PREPAGA,CONTRA,FINALIZADO) VALUES (" + prepaga
				+ ", '" + contra + "', " + finalizado + ")" + " Select @@identity";

		logger.debug("SQL QUERY: " + query);
		
		exchange.setOut(exchange.getIn());
		
		exchange.getOut().setHeader(SqlConstants.SQL_QUERY, query);

	}
}
