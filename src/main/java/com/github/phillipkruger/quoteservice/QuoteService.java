package com.github.phillipkruger.quoteservice;

import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import lombok.Getter;
import lombok.extern.java.Log;

@Stateless
@Log
public class QuoteService {
    
    @Getter
    private Quote quote;
    
    public void updateQuote(@Observes Quote quote){
        log.log(Level.FINEST, "Updating quote to [{0}]", quote.getText());
        this.quote = quote;
    }
    
}