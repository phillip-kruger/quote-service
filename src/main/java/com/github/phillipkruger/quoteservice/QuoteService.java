package com.github.phillipkruger.quoteservice;

import com.github.phillipkruger.quoteservice.provider.QuoteProvider;
import com.github.phillipkruger.quoteservice.provider.factory.QuoteProviderFactory;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import lombok.extern.java.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Stateless
@Log
public class QuoteService {
    
    @Inject
    private QuoteProviderFactory factory;

    @Inject
    @ConfigProperty(name = "quote.provider",defaultValue = "forismatic")
    private String providerName;
    
    private QuoteProvider quoteProvider;
    
    @PostConstruct
    public void init(){
        this.quoteProvider = factory.getQuoteProviderForName(providerName);
    }
    
    public Quote getQuote(){
        Quote quote = quoteProvider.getQuote();
        log.log(Level.FINEST, "Got {0} from {1}", new Object[]{quote, providerName});
        return quote;
    }
    
}