package com.github.phillipkruger.quoteservice.provider;

import java.net.URI;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Some configuration on Forismatic
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@ApplicationScoped
public class ForismaticConfig {
    @Getter
    @Inject
    @ConfigProperty(name = "forismatic.endpoint", defaultValue = "http://api.forismatic.com/api/1.0/")
    private URI endpoint;
    
    @Getter
    @Inject
    @ConfigProperty(name = "forismatic.method", defaultValue = "getQuote")
    private String method;
    
    @Getter
    @Inject
    @ConfigProperty(name = "forismatic.lang", defaultValue = "en")
    private String lang;
}
