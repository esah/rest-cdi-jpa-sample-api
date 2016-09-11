package ru.r.billing.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;
import ru.r.billing.ex.NotEnoughMoneyException;

@XmlRootElement(name = "account")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "account")
public class Account {

	@Id
	@Column(name = "number", nullable = false)
	private Long number;

	//private Customer customer;

	@Embedded
	private Money balance;

	@BatchSize(size = 10)
	@OneToMany(mappedBy = "account", /*fetch = FetchType.EAGER, */cascade = CascadeType.ALL, orphanRemoval = true)
	//@Where(clause = "(CURRENT_DATE – time) MONTH <= 3")
	@OrderBy("time") //todo do in java
	private Collection<Operation> history;

	@Column(name = "version_", nullable = false)
	@Version
	private int version;

	private Account() {
	}

	public Account(long number, Currency c) {
		this.number = number;
		this.balance = new Money(BigDecimal.ZERO, c);
	}

	private void hasEnough(Money money) {
		if (balance.isLess(money)) {
			throw new NotEnoughMoneyException();
		}
	}

	public Collection<Operation> transfer(Account to, Money amount) {
		Operation op1 = withdraw(amount);
		Operation op2 = to.deposit(amount);
		history.add(op1);
		history.add(op2);
		return Arrays.asList(op1, op2);
	}

	public Operation deposit(Money amount) {
		balance = balance.add(amount);
		final Operation debit = Operation.debit(this, amount);
		history().add(debit);
		return debit;
	}

	public Operation withdraw(Money amount) {
		hasEnough(amount);
		balance = balance.subtract(amount);
		final Operation credit = Operation.credit(this, amount);
		history().add(credit);
		return credit;
	}

	private Collection<Operation> history() {
		if (history == null) {
			history = new ArrayList<>();
		}
		return history;
	}

	public Long getNumber() {
		return number;
	}

	public Money getBalance() {
		return balance;
	}

	public Collection<Operation> getHistory() {
		return Collections.unmodifiableCollection(history());
	}
}
