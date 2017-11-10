package com.github.phillipkruger.quoteservice;

import com.github.phillipkruger.quoteservice.provider.QuoteProvider;
import com.github.phillipkruger.quoteservice.provider.factory.QuoteProviderFactory;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Singleton
@Startup
public class QuoteUpdater {
    
    @Inject
    private QuoteProviderFactory factory;

    @Resource
    private TimerService timerService;
    
    @Inject
    @ConfigProperty(name = "quote.provider",defaultValue = "forismatic")
    private String providerName;
    
    @Inject
    @ConfigProperty(name = "quote.updateFrequency",defaultValue = "3600") // 1 hour
    private int updateFrequency;
    
    private QuoteProvider quoteProvider;
    
    @Inject
    private Event<Quote> broadcaster;
    
    @PostConstruct
    public void init(){
        this.quoteProvider = factory.getQuoteProviderForName(providerName);
        update();
        TimerConfig config = new TimerConfig("Quote Cache update", false);
        timerService.createIntervalTimer(0,updateFrequency * 1000, config);
    }
    
    @Timeout
    public void timeout(Timer timer) {
        update();
    }
    
    private void update(){
        Quote quote = quoteProvider.getQuote();
        broadcaster.fire(quote);
    }
}