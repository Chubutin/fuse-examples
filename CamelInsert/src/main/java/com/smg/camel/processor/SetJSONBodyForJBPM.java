package com.smg.camel.processor;

import java.util.Date;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.json.simple.JSONObject;

/**
 * 
 * @author Ramiro Pugh
 * 
 */
public class SetJSONBodyForJBPM implements Processor {

	@SuppressWarnings("unchecked")
	@Override
	public void process(Exchange exchange) throws Exception {

		Map<String, Object> mapBody = exchange.getProperty("mapBody",Map.class );
		
		JSONObject process = new JSONObject();
		
		 process.put("start", new Date().getTime());
         process.put("processName","Desregulación");
         process.put("status", "Running");
         process.put("key",new Date().getTime());
         process.put("credencial",mapBody.get("contra"));
         process.put("observaciones",mapBody.get("observacion"));
         process.put("detalle",mapBody.get("observacion"));
         process.put("processInfo",null);
         process.put("version", 1);
         
         
         exchange.getOut().setBody(process);
		
		

	}
}
