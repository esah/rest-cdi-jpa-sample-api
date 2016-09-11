package ru.r.billing.jaxb;

import java.util.Currency;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CurrencyAdapter extends XmlAdapter<String, Currency> {
	@Override
	public Currency unmarshal(String v) throws Exception {
		return Currency.getInstance(v);
	}

	@Override
	public String marshal(Currency v) throws Exception {
		return v.getCurrencyCode();
	}
}
