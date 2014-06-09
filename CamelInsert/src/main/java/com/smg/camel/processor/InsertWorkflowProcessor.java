package com.smg.camel.processor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.sql.SqlConstants;
import org.apache.log4j.Logger;

/**
 * 
 * @author Ramiro Pugh
 * @email rampugh@gmail.com
 * 
 */
public class InsertWorkflowProcessor implements Processor {

	Logger logger = Logger.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public void process(Exchange exchange) throws Exception {

		logger.debug("Invocando processor de Insercion de Workflow");

		Map<String, Object> mapBody = exchange.getProperty("mapBody", Map.class);

		BigDecimal llamadoId;

		if (mapBody.get("llamadoId") != null) {
			llamadoId = (BigDecimal) mapBody.get("llamadoId");
		} else {
			ArrayList<Map<String, Object>> body = (ArrayList<Map<String, Object>>) exchange.getIn()
					.getBody();
			// TODO NPE CHECK
			llamadoId = (BigDecimal) body.get(0).values().toArray()[0];
		}

		Integer inte = (Integer) mapBody.get("inte");
		String registradoPor = (String) mapBody.get("registradoPor");
		Integer motivoId = (Integer) mapBody.get("motivoId");
		Integer finalizado = (Integer) mapBody.get("finalizado");

		String query = "INSERT INTO CRM_WORKFLOW (LLAMADO_ID, INTE, MOTIVO_ID, MEDIO_ID, MEDIO_NUMERO,TOMADO_POR,FINALIZADO) values ("
				+ llamadoId
				+ ", '"
				+ inte
				+ "', "
				+ motivoId
				+ ", 1, '221', '"
				+ registradoPor
				+ "', " + finalizado + ") select @@Identity";

		logger.debug("Query a invocar en el insert de workflow: " + query);

		exchange.getOut().setHeader(SqlConstants.SQL_QUERY, query);

	}
}
