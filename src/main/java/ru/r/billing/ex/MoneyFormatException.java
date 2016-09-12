package ru.r.billing.ex;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class MoneyFormatException extends WebApplicationException {
	public MoneyFormatException() {
		super(Response.Status.BAD_REQUEST);
	}

}
