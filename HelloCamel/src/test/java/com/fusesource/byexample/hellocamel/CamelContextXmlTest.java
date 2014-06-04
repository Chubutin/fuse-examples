package com.fusesource.byexample.hellocamel;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.camel.CamelContext;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration({ "classpath:META-INF/spring/camel-context.xml" })
@DisableJmx(true)
public class CamelContextXmlTest {

	@Resource(name = "helloWorldContext")
	CamelContext context;

	@Test
	public void testReturnHello() throws Exception {

		MockEndpoint mockEndpoint = (MockEndpoint) context.getEndpoint("mock:returnHello");
		mockEndpoint.setExpectedMessageCount(1);

		URL url = new URL("http://localhost:9090/rest/hello");
		HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
		httpConnection.setRequestMethod("GET");
		httpConnection.setRequestProperty("Accept", "application/json");

		if (httpConnection.getResponseCode() != 200) {
			throw new RuntimeException("HTTP GET Request Failed with Error code : "
					+ httpConnection.getResponseCode());
		}

		BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
				(httpConnection.getInputStream())));

		String output;
		System.out.println("Output from Server:  \n");

		while ((output = responseBuffer.readLine()) != null) {
			System.out.println(output);
		}

		httpConnection.disconnect();

		mockEndpoint.assertIsSatisfied(100);
	}

	@Test
	public void testReturnHelloName() throws Exception {

		MockEndpoint mockEndpoint = (MockEndpoint) context.getEndpoint("mock:returnHelloName");
		mockEndpoint.setExpectedMessageCount(1);

		URL url = new URL("http://localhost:9090/rest/hello/chubutin");
		HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
		httpConnection.setRequestMethod("GET");
		httpConnection.setRequestProperty("Accept", "application/json");

		if (httpConnection.getResponseCode() != 200) {
			throw new RuntimeException("HTTP GET Request Failed with Error code : "
					+ httpConnection.getResponseCode());
		}

		BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
				(httpConnection.getInputStream())));

		String output;
		System.out.println("Output from Server:  \n");

		while ((output = responseBuffer.readLine()) != null) {
			System.out.println(output);
		}

		httpConnection.disconnect();

		mockEndpoint.assertIsSatisfied(100);

	}

	@Test
	public void testReturnHelloPOST() throws Exception {

		MockEndpoint mockEndpoint = (MockEndpoint) context.getEndpoint("mock:returnHelloNamePOST");
		mockEndpoint.setExpectedMessageCount(1);

		List<Object> providers = new ArrayList<Object>();
		providers.add(new JacksonJaxbJsonProvider());

		WebClient client = WebClient.create("http://localhost:9090/rest/hello/", providers)
				.accept("application/json").type("application/json");

		client.header("name", "<persona><nombre>Ramiro</nombre></persona>");

		Response response = client.post(String.class);

		if (response.getStatusInfo().getStatusCode() != 200) {
			fail("El codigo de respuesta de la invocacion es "
					+ response.getStatusInfo().getStatusCode());
		}

		mockEndpoint.assertIsSatisfied(100);

	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

}
