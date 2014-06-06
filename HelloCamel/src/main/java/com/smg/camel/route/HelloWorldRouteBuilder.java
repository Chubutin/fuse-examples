package com.smg.camel.route;

import java.util.Map;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class HelloWorldRouteBuilder extends RouteBuilder {
	
	@Override
	public void configure() throws Exception {

		from("cxfrs:bean?resourceClasses=com.fluxit.service.HelloCamelService&bindingStyle=SimpleConsumer&address=http://localhost:9090/rest")
				.setHeader("headerRouting",
						simple("direct:${header.operationName}"))
				.routingSlip(
						header("headerRouting"));

		from("direct:returnHello").log("Entro en la ruta hello world")
				.to("setJSONBodyProcessor")
				.to("myProcessor")
				.unmarshal()
					.json(JsonLibrary.Jackson, Map.class)
				.to("myProcessor")
				.to("mock:returnHello")
				.setBody(constant("Hello"));

		from("direct:returnHelloName")
			.log("Entro en la ruta hello world name")
			.to("mock:returnHelloName")
			.setBody(simple("Hello #name"));

	}

}
