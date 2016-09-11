package ru.r.billing;

import java.util.Collections;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import ru.r.billing.api.BillingWeb;

@ApplicationPath("/api")
public class RsApp extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		return Collections.singleton(BillingWeb.class);
	}
}
