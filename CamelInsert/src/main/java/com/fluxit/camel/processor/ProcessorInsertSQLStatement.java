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
public class ProcessorInsertSQLStatement implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		exchange.getOut().setHeader(SqlConstants.SQL_QUERY, "INSERT INTO CRM_observacion_generico (observacion, etapa_id) VALUES ('Algo',22)");
		
	}

}
