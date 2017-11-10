package com.github.phillipkruger.quoteservice;

import com.github.phillipkruger.quoteservice.provider.QuoteProvider;
import com.github.phillipkruger.quoteservice.provider.factory.QuoteProviderFactory;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Stateless
public class QuoteUpdater {
    
    @Inject
    private QuoteProviderFactory factory;

    @Inject
    @ConfigProperty(name = "quote.provider",defaultValue = "forismatic")
    private String providerName;
    
    private QuoteProvider quoteProvider;
    
    @Inject
    private Event<Quote> broadcaster;
    
    @PostConstruct
    public void init(){
        this.quoteProvider = factory.getQuoteProviderForName(providerName);
    }
    
    @Asynchronous
    public void update(){
        Quote quote = quoteProvider.getQuote();
        broadcaster.fire(quote);
    }
}