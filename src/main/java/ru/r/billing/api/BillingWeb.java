package ru.r.billing.api;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Currency;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import ru.r.billing.ex.DifferentCurrencyException;
import ru.r.billing.ex.MoneyFormatException;
import ru.r.billing.ex.NotEnoughMoneyException;
import ru.r.billing.jpa.GenericDao;
import ru.r.billing.model.Account;
import ru.r.billing.model.Money;
import ru.r.billing.model.Operation;
import ru.r.billing.service.AccountService;

@Singleton
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class BillingWeb {
	@Inject
	private AccountService accountService;
	@Inject
	private GenericDao genericDao;

	@GET
	@Path("account/{id}")
	public Account getAccount(@PathParam("id") long id) {
		return accountService.getAccountWithHistory(id);
	}

	@PUT
	@Path("account/{id}")
	public Account createAccount(
			@PathParam("id") long id,
			@DefaultValue("RUB") @QueryParam("currency") String currencyCode) {
		if (genericDao.find(Account.class, id) != null) {
			throw new WebApplicationException(Response.Status.CONFLICT);
		}
		final Currency currency = Currency.getInstance(currencyCode);
		return genericDao.saveOrUpdate(new Account(id, currency));
	}

	@PUT
	@Path("account/{id}/{amount}/{currency}")
	public Operation deposit(
			@PathParam("id") long id,
			@PathParam("amount") BigDecimal amount,
			@PathParam("currency") String currencyCode) {
		return accountService.deposit(id, toMoney(amount, currencyCode));
	}

	@DELETE
	@Path("account/{id}/{amount}/{currency}")
	public Operation withdraw(
			@PathParam("id") long id,
			@PathParam("amount") BigDecimal amount,
			@PathParam("currency") String currencyCode) {
		return accountService.withdraw(id, toMoney(amount, currencyCode));
	}

	@POST
	@Path("account/{id1}/transfer/{id2}")
	public Collection<Operation> transfer(
			@PathParam("id1") long id1,
			@PathParam("id2") long id2,
			@QueryParam("amount") BigDecimal amount,
			@QueryParam("currency") String currencyCode) {
		return accountService.transfer(id1, id2, toMoney(amount, currencyCode));
	}

	private Money toMoney(BigDecimal amount, String currencyCode) {
		if (amount == null || amount.signum() <= 0 || currencyCode == null) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
		return new Money(amount, Currency.getInstance(currencyCode));
	}
}
