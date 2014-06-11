package com.smg.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path(value = "/tasks")
public interface CamelInsertService {

	@POST
	@Produces("application/json")
	@Consumes(value = { "application/json", "application/xml" })
	Object task(String body, @HeaderParam(value = "headerName") String headerName)
			throws RuntimeException;

	@GET
	@Path("/{name}")
	@Produces("application/json")
	Object task() throws RuntimeException;

	@GET
	@Produces("application/json")
	Object taskTest() throws RuntimeException;

}
