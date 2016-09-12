package ru.r.billing.service;

import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityNotFoundException;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import ru.r.billing.ex.NotEnoughMoneyException;
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
		Account account = genericDao.load(Account.class, id);
		account.getHistory();
		return account;
	}

	public Operation withdraw(long id, Money money) throws NotEnoughMoneyException {
		Account account = genericDao.load(Account.class, id);
		return account.withdraw(money);
	}

	public Operation deposit(long id, Money money) {
		Account account = genericDao.load(Account.class, id);
		return account.deposit(money);
	}

	public Collection<Operation> transfer(long id, long id2, Money money)
			throws NotEnoughMoneyException {
		Account account1 = genericDao.load(Account.class, id);
		Account account2 = genericDao.load(Account.class, id2);

		return account1.transfer(account2, money);
	}
}
