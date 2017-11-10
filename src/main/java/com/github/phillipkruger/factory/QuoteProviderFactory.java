package com.github.phillipkruger.factory;

import com.github.phillipkruger.quoteservice.provider.QuoteProvider;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
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
 
    public QuoteProvider getQuoteProvider(String name) {
	QuoteProvider selectedQuoteProvider = null;
        
        Instance<QuoteProvider> selectedQuoteProviders = quoteProviders.select(new QuoteProviderNameLiteral(name));
        
        for(QuoteProvider quoteProvider : selectedQuoteProviders) {
            selectedQuoteProvider = quoteProvider;
        }
	return selectedQuoteProvider;
    }	
}