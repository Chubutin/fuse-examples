package com.fluxit.camel.processor;

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
public class ProcessorSelectSQLStatement implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		String nombreProcessor = exchange.getProperty("nombreProceso", String.class);

		String query = "select max(p.ID) from GPS_PROCESS p where p.PROCESS_NAME = '" + nombreProcessor
				+ "'";

		System.out.println("Query: " + query);

		exchange.getOut().setHeader(SqlConstants.SQL_QUERY, query);

	}
}
