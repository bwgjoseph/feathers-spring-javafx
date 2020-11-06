package com.bwgjoseph.springjavafxclient.configuration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The reason why we couldn't access `SecurityContextHolder.getContext()` object previously was because
 * `SecurityContextHolder` is defined inside a `EventThread` and since `SecurityContextHolder` is bounded to `ThreadLocal`
 * when trying to access `SecurityContextHolder` ApplicationThread, it returns a `null` context
 * 
 * Hence, to resolve the issue, we have to set the strategy to `MODE_GLOBAL`
 * Setting to `MODE_INHERITABLETHREADLOCAL` will not work because when using the MODE_INHERITABLETHREADLOCAL,
 * the framework copies the security context details from the original thread of the request to the security context of the new thread
 * See {Link} https://www.baeldung.com/spring-security-async-principal-propagation
 * 
 * With MODE_GLOBAL used as the security context management strategy, all the threads access the same security context.
 * This implies that they all have access to the same data and they can change that information.
 * Because of this, race conditions can occur and you have to take care of the synchronization.
 * 
 * For @Async / Thread Pool - See {Link} https://github.com/spring-projects/spring-security/issues/6856#issuecomment-518787966
 * 
 * @author Joseph
 *
 */
@Configuration
public class SecurityConfig implements InitializingBean {
	
	@Override
	public void afterPropertiesSet() throws Exception {
		SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);
	}
}
