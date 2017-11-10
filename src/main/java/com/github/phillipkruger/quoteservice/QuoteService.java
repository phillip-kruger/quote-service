package com.github.phillipkruger.quoteservice;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import lombok.Getter;

@Stateless
public class QuoteService {
    
    @Inject
    private QuoteCache quoteCache;
    
    @Getter
    private Quote quote;
    
    @PostConstruct
    public void init(){
        this.quote = quoteCache.getQuote();
    }
    
}