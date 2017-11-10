package com.github.phillipkruger.quoteservice;

import com.github.phillipkruger.factory.QuoteProviderFactory;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author phillip
 */
@Stateless
public class QuoteService {
    
    @Inject
    private QuoteProviderFactory factory;

    public Quote getQuote(){
        return factory.getQuoteProviderForName("forismatic").getQuote();
    }
}
