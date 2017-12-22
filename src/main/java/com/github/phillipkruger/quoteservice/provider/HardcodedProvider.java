package com.github.phillipkruger.quoteservice.provider;

import com.github.phillipkruger.quoteservice.provider.factory.QuoteProviderName;
import com.github.phillipkruger.quoteservice.Quote;
import javax.enterprise.context.RequestScoped;
import lombok.extern.java.Log;

/**
 * Provider that get quotes from hardcoded value
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@RequestScoped
@QuoteProviderName("hardcoded")
@Log
public class HardcodedProvider implements QuoteProvider {
    
    @Override
    public Quote getQuote() {
       return new Quote("Everything in it's right place", "Radiohead");
    }
    
}
