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
		"classpath:spring-import/camel-context.xml" })
@DisableJmx(true)
public class CamelContextTest {

	private Logger logger = Logger.getLogger(getClass());

	@Value(value = "${http.address.path}")
	String serviceAddress;

	@Resource(name = "helloWorldContext")
	CamelContext context;

	 @Test
	public void testInsertWithXML() throws Exception {

		MockEndpoint mockEndpoint = (MockEndpoint) context.getEndpoint("mock:returnHello");
		mockEndpoint.setExpectedMessageCount(1);

		List<Object> providers = new ArrayList<Object>();
		providers.add(new JacksonJaxbJsonProvider());

		WebClient client = WebClient.create(serviceAddress + "/tasks", providers)
				.accept("application/json").type("application/xml");

		client.header("headerName", "header en JSON");

		Response response = client
				.post("<llamado><prepaga>2</prepaga><contra>2444</contra></llamado>");

		logger.debug("Estado de Respuesta: " + response.getStatus());

		mockEndpoint.assertIsSatisfied(100);
	}

	@Test
	public void testInsertWithJSON() throws InterruptedException {

		MockEndpoint mockEndpoint = (MockEndpoint) context.getEndpoint("mock:returnHello");
		mockEndpoint.setExpectedMessageCount(1);

		List<Object> providers = new ArrayList<Object>();
		providers.add(new JacksonJaxbJsonProvider());

		WebClient client = WebClient.create(serviceAddress + "/tasks", providers)
				.accept("application/json").type("application/json");

		client.header("headerName", "header en JSON");

		// Response response = client
		// .post("<persona><nombre>Ramiro</nombre></persona>");

		JSONObject jObject = new JSONObject();

		jObject.put("prepaga", new Integer(2));
		jObject.put("contra", "2555");
		jObject.put("finalizado", new Integer(0));
		jObject.put("inte", new Integer(1));
		jObject.put("registradoPor", "chubutin");
		jObject.put("motivoId", new Integer(43));

		Response response = client.post(jObject);

		// Response response = client
		// .post("{\"prepaga\":2,\"contra\":\"2555\",\"finalizado\":0,\"inte\":1,\"registradoPor\":\"chubutin\",\"motivoId\":43}");

		logger.debug("Estado de Respuesta: " + response.getStatus());

		mockEndpoint.assertIsSatisfied(100);
	}

}
