package ru.r.billing.model;

import java.math.BigDecimal;
import java.util.Currency;
import ru.r.billing.ex.DifferentCurrencyException;
/**
 * Immutable
 */
public class Money {
	private /*final*/ BigDecimal amount;
	private /*final*/ Currency currency;

	public Money(BigDecimal amount, Currency currency) {
		this.amount = amount;
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public Money add(Money money) {
		theSameCurrency(money);
		return of(amount.add(money.getAmount()));
	}

	public Money subtract(Money money) {
		theSameCurrency(money);
		return of(amount.subtract(money.getAmount()));
	}

	private Money of(BigDecimal newAmount) {
		return new Money(newAmount, currency);
	}

	private void theSameCurrency(Money money) {
		if (!currency.equals(money.getCurrency())) {
			throw new DifferentCurrencyException();
		}
	}

	public boolean isLess(Money money) {
		theSameCurrency(money);
		return amount.compareTo(money.getAmount()) < 0;
	}
}
