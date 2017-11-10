package com.github.phillipkruger.quoteservice;

import com.github.phillipkruger.quoteservice.provider.QuoteProvider;
import com.github.phillipkruger.quoteservice.provider.factory.QuoteProviderFactory;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class QuoteCache {
    
    @Inject
    private QuoteProviderFactory factory;

    @Inject
    @ConfigProperty(name = "quote.provider",defaultValue = "forismatic")
    private String providerName;
    
    private QuoteProvider quoteProvider;
    
    @Getter
    private Quote quote;
    
    @PostConstruct
    public void init(){
        this.quoteProvider = factory.getQuoteProviderForName(providerName);
        this.quote = quoteProvider.getQuote();
    }
    
}