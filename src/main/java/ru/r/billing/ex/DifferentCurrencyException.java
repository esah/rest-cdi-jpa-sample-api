package ru.r.billing.ex;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class DifferentCurrencyException extends WebApplicationException {
	public DifferentCurrencyException() {
		super(Response.Status.PRECONDITION_FAILED);
	}
}
