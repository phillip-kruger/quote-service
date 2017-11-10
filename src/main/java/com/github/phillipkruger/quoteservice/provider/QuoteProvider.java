package com.github.phillipkruger.quoteservice.provider;

import com.github.phillipkruger.quoteservice.Quote;

/**
 * Fetching a quote to show on some (configured) interval
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
public interface QuoteProvider {

    public Quote getQuote();
    
}
