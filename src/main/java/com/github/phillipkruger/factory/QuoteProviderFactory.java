package com.github.phillipkruger.factory;

import com.github.phillipkruger.quoteservice.provider.QuoteProvider;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

/**
 * Basic Factory
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@ApplicationScoped
public class QuoteProviderFactory {
    @Inject
    @Any
    private Instance<QuoteProvider> quoteProviders;
 
    private final Map<String,QuoteProvider> cached = new HashMap<>();
    
    public QuoteProvider getQuoteProviderForName(String name) {
	if(cached.containsKey(name)){
            return cached.get(name);
        }else{
            QuoteProvider provider = quoteProviders.select(new QuoteProviderNameLiteral(name)).get();
            cached.put(name, provider);
            return provider;
        }
    }
}