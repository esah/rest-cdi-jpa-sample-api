package ru.r.billing.model;

import java.util.Arrays;
import java.util.Collection;
import ru.r.billing.ex.NotEnoughMoneyException;

public class Account {

	private long number;

	//private Customer customer;

	private Money balance;

	//batch size=10, lazy=true //where
	private Collection<Operation> history; //last 3 month history


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

	public long getNumber() {
		return number;
	}

	public Money getBalance() {
		return balance;
	}

	public Collection<Operation> getHistory() {
		return history;
	}
}
