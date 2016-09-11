package ru.r.billing.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Currency;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Where;
import ru.r.billing.ex.NotEnoughMoneyException;

@XmlRootElement(name = "account")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //for simplicity
	@Column(name = "number")
	private Long number;

	//private Customer customer;

	@Embedded
	private Money balance;

	@BatchSize(size = 10)
	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
	@Where(clause = "time >= CURRENT_DATE - '3' MONTH") //last 3 month history
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
		return Arrays.asList(op1, op2);
	}

	private Operation deposit(Money amount) {
		balance = balance.add(amount);
		return Operation.debit(this, amount);
	}

	private Operation withdraw(Money amount) {
		hasEnough(amount);
		balance = balance.subtract(amount);
		return Operation.credit(this, amount);
	}

	public Long getNumber() {
		return number;
	}

	public Money getBalance() {
		return balance;
	}

	public Collection<Operation> getHistory() {
		return history;
	}
}
