package ru.r.billing.api;

import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import ru.r.billing.jpa.GenericDao;
import ru.r.billing.model.Account;
import ru.r.billing.model.Money;
import ru.r.billing.model.Operation;
import ru.r.billing.service.AccountService;

@Singleton
@Path("billing")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BillingWeb {

	@Inject
	private AccountService accountService;

	@Inject
	private GenericDao genericDao;

	@GET
	public Object hello() {
		return genericDao.find(Account.class, 1L);
		//return new Account(1, Currency.getInstance("RUB"));
	}

	private Account account;

	//GET /account/1
	public Account getAccount() {
		//fetch getHistory()
		throw new UnsupportedOperationException();
	}

	//PUT /1/account?currency=rub
	public long createAccount() {

		throw new UnsupportedOperationException();
	}

	//PUT    /account/1/200/rub
	//DELETE /account/1/200/rub
	public Operation deposit(long accountNumber, Money money) {

		throw new UnsupportedOperationException();
	}

	//POST /account/1/transfer/2?amount=200&currency=rub
	public Collection<Operation> transfer(long fromAccountNumber, long toAccountNumber,
			Money amount) {

		throw new UnsupportedOperationException();

	}
}
