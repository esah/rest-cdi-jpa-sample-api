package ru.r.billing.ex;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class NotFoundException extends WebApplicationException {

	public NotFoundException(String message) {
		//todo use message
		super(Response.Status.NOT_FOUND);
	}

}
