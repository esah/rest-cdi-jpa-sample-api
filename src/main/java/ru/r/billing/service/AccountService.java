package ru.r.billing.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Currency;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityNotFoundException;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import ru.r.billing.api.BillingWeb;
import ru.r.billing.jpa.GenericDao;
import ru.r.billing.model.Account;
import ru.r.billing.model.Money;
import ru.r.billing.model.Operation;

@Singleton
@Transactional
public class AccountService {
	@Inject
	private GenericDao genericDao;

	public Account getAccountWithHistory(long id) {
		final Account account = genericDao.find(Account.class, id);
		if (account == null) {
			throw new EntityNotFoundException("Account " + id);
		}
		account.getHistory();
		return account;
	}

	public Operation withdraw(long id, Money money) {
		final Account account = genericDao.find(Account.class, id);
		if (account == null) {
			throw new EntityNotFoundException("Account " + id);
		}
		return account.withdraw(money);
	}

	public Operation deposit(long id, Money money) {
		final Account account = genericDao.find(Account.class, id);
		if (account == null) {
			throw new EntityNotFoundException("Account " + id);
		}
		return account.deposit(money);
	}

	public Collection<Operation> transfer(long id, long id2, Money money) {
		final Account account1 = genericDao.find(Account.class, id);
		if (account1 == null) {
			throw new EntityNotFoundException("Account " + id);
		}
		final Account account2 = genericDao.find(Account.class, id2);
		if (account2 == null) {
			throw new EntityNotFoundException("Account " + id2);
		}
		return account1.transfer(account2, money);
	}
}
