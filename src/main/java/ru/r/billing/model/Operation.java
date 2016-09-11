package ru.r.billing.model;

import java.time.ZonedDateTime;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import ru.r.billing.jaxb.ZonedDateTimeXmlAdapter;

@Entity
@Table(name = "operation")
@XmlRootElement(name = "operation")
@XmlAccessorType(XmlAccessType.FIELD)
public class Operation {
	public static enum Type {
		DEBIT,
		CREDIT
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@XmlTransient
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	@XmlTransient
	private Account account;

	@Embedded
	private Money amount;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private Type type;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name = "amount", column = @Column(name = "balance_amount", nullable = false)),
			@AttributeOverride(name = "currency", column = @Column(name = "balance_currency", nullable = false))
	})
	private Money balance;

	@Column(name = "time", nullable = false)
	@XmlJavaTypeAdapter(ZonedDateTimeXmlAdapter.class)
	private ZonedDateTime time;

	@Column(name = "desc")
	private String desc;

	private Operation() {
	}

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
