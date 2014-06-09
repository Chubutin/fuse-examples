package com.smg.camel.processor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.camel.CamelException;
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
public class InsertEtapaProcessor implements Processor {

	Logger logger = Logger.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public void process(Exchange exchange) throws Exception {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");

		logger.debug("Invocando processor de Insercion de Etapa");

		ArrayList<Map<String, Object>> body = (ArrayList<Map<String, Object>>) exchange.getIn()
				.getBody();

		Map<String, Object> propertyMap = exchange.getProperty("mapBody", Map.class);
		BigDecimal workflowId;

		if (body.get(0) != null && body.get(0).values().toArray().length > 0) {
			//TODO REVISAR ESTE CAST
			workflowId = (BigDecimal)body.get(0).values().toArray()[0];
			propertyMap.put("workflowId", workflowId);
		} else {
			throw new CamelException(
					"No existe WORKFLOW_ID en el body, el identity no inserto datos en el body");
		}

		String registradoPor = (String) propertyMap.get("registradoPor");
		Integer workflow = (Integer) propertyMap.get("workflow");
		String observacion = (String) propertyMap.get("observacion");
		Integer sucursalId = (Integer) propertyMap.get("sucursalId");

		Calendar instance = Calendar.getInstance();
		Date registroFecha = instance.getTime();

		String query = "INSERT INTO CRM_ETAPAS (WORKFLOW_ID,WORKFLOW,REGISTRO_FECHA,REGISTRO_USUARIO,OBSERVACION,sucursal_id,reg_username ) values ("
				+ workflowId
				+ ", "
				+ workflow
				+ ", '"
				+ formatter.format(registroFecha)
				+ "', '"
				+ registradoPor
				+ "','"
				+ observacion
				+ "',"
				+ sucursalId
				+ ", '"
				+ registradoPor
				+ "') select @@Identity";

		logger.debug("Query a invocar en el insert de workflow: " + query);

		exchange.getOut().setHeader(SqlConstants.SQL_QUERY, query);

	}
}
