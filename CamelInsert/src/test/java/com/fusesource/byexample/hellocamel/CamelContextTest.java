package com.fusesource.byexample.hellocamel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.core.Response;

import org.apache.camel.CamelContext;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration({ "classpath:spring-context/test-context.xml",
		"classpath:spring-import/camel-context.xml", "classpath:spring-import/beans-context.xml" })
@DisableJmx(true)
public class CamelContextTest {

	private Logger logger = Logger.getLogger(getClass());

	@Value(value = "${http.address.path}")
	String serviceAddress;

	@Resource(name = "camelInsertContext")
	CamelContext context;

	// @Test
	public void testInsertWithXML() throws Exception {

		MockEndpoint mockEndpoint = (MockEndpoint) context.getEndpoint("mock:returnHello");
		mockEndpoint.setExpectedMessageCount(1);

		List<Object> providers = new ArrayList<Object>();
		providers.add(new JacksonJaxbJsonProvider());

		WebClient client = WebClient.create(serviceAddress + "/tasks", providers)
				.accept("application/json").type("application/xml");

		Response response = client
				.post("<llamado><prepaga>2</prepaga><contra>2444</contra><finalizado>0</finalizado><inte>1</inte><registradoPor>chubutin</registradoPor><motivoId>43</motivoId></llamado>");

		logger.debug("Estado de Respuesta: " + response.getStatus());

		mockEndpoint.assertIsSatisfied(100);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInsertWithJSON() throws InterruptedException {

		MockEndpoint mockEndpoint = (MockEndpoint) context.getEndpoint("mock:returnHello");
		mockEndpoint.setExpectedMessageCount(1);

		List<Object> providers = new ArrayList<Object>();
		providers.add(new JacksonJaxbJsonProvider());

		WebClient client = WebClient.create(serviceAddress + "/tasks", providers)
				.accept("application/json").type("application/json");

		client.header("Connection", "Keep-Alive");
		client.header("headerName", "header en JSON");

		JSONObject jObject = new JSONObject();

		jObject.put("prepaga", new Integer(2));
		jObject.put("contra", "2444");
		jObject.put("finalizado", new Integer(0));
		jObject.put("inte", new Integer(1));
		jObject.put("registradoPor", "Chubutin");
		jObject.put("motivoId", new Integer(30));
		// jObject.put("llamadoId", null);
		jObject.put("workflow", new Integer(1));
		jObject.put("observacion", "Desde Fuse con los chicos!");
		jObject.put("sucursalId", new Integer(10));
		
		Response response = client.post(jObject);

		logger.debug("Estado de Respuesta: " + response.getStatus());

		mockEndpoint.assertIsSatisfied(100);
	}
	
}
