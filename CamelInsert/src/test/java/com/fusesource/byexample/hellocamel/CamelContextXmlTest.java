package com.fusesource.byexample.hellocamel;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration({ "classpath:spring-context/test-context.xml",
		"classpath:spring-import/camel-context.xml" })
@DisableJmx(true)
public class CamelContextXmlTest {

	private Logger logger = Logger.getLogger(getClass());

	@Resource(name = "helloWorldContext")
	CamelContext context;

	@Test
	public void testInsert() throws Exception {

		MockEndpoint mockEndpoint = (MockEndpoint) context.getEndpoint("mock:returnHello");
		mockEndpoint.setExpectedMessageCount(1);

		List<Object> providers = new ArrayList<Object>();
		providers.add(new JacksonJaxbJsonProvider());

		WebClient client = WebClient.create("http://localhost:9090/rest/prepagas", providers)
				.accept("application/json").type("application/xml");

		client.header("headerName", "header en JSON");

		Response response = client
				.post("<tarea><nombreTarea>Tarea de prueba 10</nombreTarea><nombreProceso>Proceso de Prueba 10</nombreProceso></tarea>");

		logger.debug("Estado de Respuesta: " + response.getStatus());

		mockEndpoint.assertIsSatisfied(100);
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

}
