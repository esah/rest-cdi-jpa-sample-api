package ru.r.billing.model;

import java.math.BigDecimal;
import java.util.Currency;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ru.r.billing.ex.DifferentCurrencyException;
import ru.r.billing.jaxb.CurrencyAdapter;

/**
 * Immutable
 */
@XmlRootElement(name = "money")
@XmlAccessorType(XmlAccessType.FIELD)
public class Money {
	private /*final*/ BigDecimal amount;
	@XmlJavaTypeAdapter(CurrencyAdapter.class)
	private /*final*/ Currency currency;

	private Money() {
	}

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
