package ru.r.billing.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ru.r.billing.ex.DifferentCurrencyException;
import ru.r.billing.ex.MoneyFormatException;
import ru.r.billing.jaxb.CurrencyXmlAdapter;

/**
 * Immutable
 */
@XmlRootElement(name = "money")
@XmlAccessorType(XmlAccessType.FIELD)
@Embeddable
public class Money implements Comparable<Money>, Serializable {
	@Column(name = "amount", precision = 15, scale = 2, nullable = false)
	private /*final*/ BigDecimal amount;

	@XmlJavaTypeAdapter(CurrencyXmlAdapter.class)
	@Column(name = "currency", nullable = false)
/*	@Type(type = "org.hibernate.type.CurrencyType")*/
	private /*final*/ Currency currency;

	private Money() {
	}

	public Money(BigDecimal amount, Currency currency) throws MoneyFormatException {
		if (amount == null || amount.signum() < 0 || amount.scale() > 2 || currency == null) {
			throw new MoneyFormatException();
		}
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

	private Money of(BigDecimal newAmount) throws MoneyFormatException {
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

	@Override
	public int compareTo(Money o) {
		final int i = currency.getCurrencyCode().compareTo(o.getCurrency().getCurrencyCode());
		return i != 0 ? 0 : amount.compareTo(o.getAmount());
	}
}
