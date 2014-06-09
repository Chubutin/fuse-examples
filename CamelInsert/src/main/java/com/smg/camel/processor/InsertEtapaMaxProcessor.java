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
public class InsertEtapaMaxProcessor implements Processor {

	Logger logger = Logger.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public void process(Exchange exchange) throws Exception {

		logger.debug("Invocando processor de Insercion de Etapa");

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");

		ArrayList<Map<String, Object>> body = (ArrayList<Map<String, Object>>) exchange.getIn()
				.getBody();

		Map<String, Object> propertyMap = exchange.getProperty("mapBody", Map.class);
		BigDecimal etapaId;

		if (body.get(0) != null && body.get(0).values().toArray().length > 0) {
			etapaId = (BigDecimal) body.get(0).values().toArray()[0];
		} else {
			throw new CamelException(
					"No existe WORKFLOW_ID en el body, el identity no inserto datos en el body");
		}

		Integer workflow = (Integer) propertyMap.get("workflow");
		Integer motivoId = (Integer) propertyMap.get("motivoId");
		BigDecimal workflowId = (BigDecimal) propertyMap.get("workflowId");

		Calendar instance = Calendar.getInstance();
		Date registroFecha = instance.getTime();
		//TODO CAMBIAR
		String query = "INSERT INTO CRM_ETAPAS_MAX (registro_fecha,motivo_id,workflow_ultimo,workflow_id,etapa_id_max) values ('"
				+ formatter.format(registroFecha)
				+ "', "
				+ motivoId
				+ ", "
				+ workflow
				+ ", "
				+ workflowId + "," + etapaId + ") select @@Identity";

		logger.debug("Query a invocar en el insert de workflow: " + query);

		exchange.getOut().setHeader(SqlConstants.SQL_QUERY, query);

	}
}
