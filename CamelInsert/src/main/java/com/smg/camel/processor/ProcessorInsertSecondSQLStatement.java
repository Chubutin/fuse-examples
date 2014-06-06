package com.smg.camel.processor;

import java.util.ArrayList;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.sql.SqlConstants;

/**
 * 
 * @author Ramiro Pugh
 * @company www.fluxit.com.ar
 * @email ramiro.pugh@fluxit.com.ar
 * 
 */
public class ProcessorInsertSecondSQLStatement implements Processor {

	@SuppressWarnings("unchecked")
	@Override
	public void process(Exchange exchange) throws Exception {

		ArrayList<Map<String, Object>> body = (ArrayList<Map<String, Object>>) exchange.getIn()
				.getBody();

		Map<String, Object> jsonBody = exchange.getProperty("jsonBody", Map.class);

		Integer inte = (Integer) jsonBody.get("inte");
		String registradoPor = (String) jsonBody.get("registradoPor");
		Integer motivoId = (Integer) jsonBody.get("motivoId");
		Integer finalizado = (Integer) jsonBody.get("finalizado");

		String query = "INSERT INTO CRM_WORKFLOW (LLAMADO_ID, INTE, MOTIVO_ID, MEDIO_ID, MEDIO_NUMERO,TOMADO_POR,FINALIZADO) values ("
				+ body.get(0).values().toArray()[0]
				+ ", '"
				+ inte
				+ "', "
				+ motivoId
				+ ", 1, '221', '" + registradoPor + "', " + finalizado + ")";

		System.out.println("Query: " + query);

		exchange.getOut().setHeader(SqlConstants.SQL_QUERY, query);

	}
}
