package com.fluxit.service;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path(value = "/hello")
public interface HelloCamelService {

	@GET
	@Produces("application/json")
	String returnHello();

	@GET
	@Produces("application/json")
	@Path("/{name}")
	String returnHelloName(@QueryParam(value = "name") String name);
	
	@POST
	@Produces("application/json")
	String returnHello(@HeaderParam(value = "name") String name);

}
