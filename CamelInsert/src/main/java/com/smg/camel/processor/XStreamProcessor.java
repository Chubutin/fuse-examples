package com.smg.camel.processor;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XStreamProcessor implements Processor {

	static Logger logger = Logger.getLogger(XStreamProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		
		AbstractMap<String, Object> map = new HashMap<String, Object>();

		logger.debug("Entro en el processor de conversion del XML en Mapa");

		Document body = exchange.getIn().getBody(Document.class);

		recorrerRamaDOM(body, map);
		
		exchange.getOut().setBody(map);

	}

	public static void recorrerRamaDOM(Node nodo, Map<String,Object> map) {
		if (nodo != null) {
			System.out.println("Nombre del Nodo: " + nodo.getNodeName());
			System.out.println("Valor del Nodo: " + nodo.getTextContent());
			map.put(nodo.getNodeName(), nodo.getNodeValue());
			NodeList hijos = nodo.getChildNodes();
			for (int i = 0; i < hijos.getLength(); i++) {
				Node nodoNieto = hijos.item(i);
				recorrerRamaDOM(nodoNieto, map);
			}
		}
	}

}
