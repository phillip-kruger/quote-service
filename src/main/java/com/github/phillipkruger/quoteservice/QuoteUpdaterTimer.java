package com.github.phillipkruger.quoteservice;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Singleton
@Startup
public class QuoteUpdaterTimer {
    
    @Inject
    private QuoteUpdater updater;

    @Resource
    private TimerService timerService;
    
    @Inject
    @ConfigProperty(name = "quote.updateFrequency",defaultValue = "3600") // 1 hour
    private int updateFrequency;
    
    @PostConstruct
    public void init(){
        int timeout = updateFrequency * 1000;
        TimerConfig config = new TimerConfig("Quote Cache update", false);
        updater.update();
        timerService.createIntervalTimer(timeout,timeout, config);
    }
    
    @Timeout
    public void timeout(Timer timer) {
        updater.update();
    }
    
}