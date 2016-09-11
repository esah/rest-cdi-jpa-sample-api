package ru.r.billing.model;

import java.time.ZonedDateTime;

public class Operation {
	public static enum Type {
		DEBIT,
		CREDIT
	}

	private Account account;
	private Money amount;
	private Type type;
	private Money balance;
	private ZonedDateTime time;
	private String desc;

	public Operation(Account account, Money amount, Type type, Money balance,
			ZonedDateTime time) {
		this.account = account;
		this.amount = amount;
		this.type = type;
		this.balance = balance;
		this.time = time;
	}

	public static Operation debit(Account account, Money money) {
		return new Operation(account, money, Type.DEBIT, account.getBalance(), ZonedDateTime.now());
	}

	public static Operation credit(Account account, Money money) {
		return new Operation(account, money, Type.CREDIT, account.getBalance(), ZonedDateTime.now());
	}
}
