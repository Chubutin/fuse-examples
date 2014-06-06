package com.smg.camel.processor;

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
public class ProcessorInsertSQLStatement implements Processor {

	@SuppressWarnings("unchecked")
	@Override
	public void process(Exchange exchange) throws Exception {

		Map<String, Object> jsonBody = exchange.getProperty("jsonBody", Map.class);

		Integer prepaga = (Integer) jsonBody.get("prepaga");
		String contra = (String) jsonBody.get("contra");
		Integer finalizado = (Integer) jsonBody.get("finalizado");

		String query = "INSERT INTO CRM_LLAMADOS (PREPAGA,CONTRA,FINALIZADO) VALUES (" + prepaga
				+ ", '" + contra + "', " + finalizado + ")" + " Select @@identity";

		System.out.println("SQL QUERY: " + query);

		exchange.getOut().setHeader(SqlConstants.SQL_QUERY, query);

	}
}
