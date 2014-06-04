package com.fluxit.camel.processor;

import java.util.ArrayList;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.sql.SqlConstants;

import com.sun.xml.bind.v2.schemagen.xmlschema.List;

/**
 * 
 * @author Ramiro Pugh
 * @company www.fluxit.com.ar
 * @email ramiro.pugh@fluxit.com.ar
 * 
 */
public class ProcessorInsertSecondSQLStatement implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		ArrayList<Map<String, Object>> body = (ArrayList<Map<String, Object>>) exchange.getIn().getBody();

		String nombreTarea = exchange.getProperty("nombreTarea", String.class);

		String query = "INSERT INTO GPS_TASKS (PROCESS_ID_FK, TASK_NAME, APP, FORM, FORM_VISUALIZATION) values ("
				+ body.get(0).values().toArray()[0] + ", '" + nombreTarea + "', 'App', 'Form', 'Form visua')";

		System.out.println("Query: " + query);

		exchange.getOut().setHeader(SqlConstants.SQL_QUERY, query);

	}
}
