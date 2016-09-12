package ru.r.billing.ex;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class NotEnoughMoneyException extends WebApplicationException {

	public NotEnoughMoneyException() {
		super(Response.Status.PRECONDITION_FAILED);
	}
}
