package com.github.phillipkruger.quoteservice.provider;

import com.github.phillipkruger.quoteservice.provider.factory.QuoteProviderName;
import com.github.phillipkruger.quoteservice.ProxyInfo;
import com.github.phillipkruger.quoteservice.Quote;
import java.io.StringReader;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.extern.java.Log;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;

/**
 * Provider that get quotes from http://forismatic.com/en/api/
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@RequestScoped
@QuoteProviderName("forismatic")
@Log
public class ForismaticProvider implements QuoteProvider {
    
    @Inject
    private ProxyInfo proxyInfo;
    @Inject
    private ForismaticConfig forismaticConfig;
    
    @Override
    @Retry(delay = 5,delayUnit = ChronoUnit.SECONDS, maxRetries = 5)
    @Timeout(unit = ChronoUnit.SECONDS,value = 5)
    public Quote getQuote() {
        WebTarget target = client.target(forismaticConfig.getEndpoint());

        Response response = target
                .request(MediaType.APPLICATION_FORM_URLENCODED)
                .buildPost(Entity.form(form)).invoke();
        
        if(response.getStatus()==Response.Status.OK.getStatusCode()){
            String json = response.readEntity(String.class);
            if(json!=null && !json.isEmpty()){
                return toQuote(json);
            }else{
                throw new NullPointerException("Error while getting qoute from Forismatic [null message]");
            }
        }else { 
            throw new RuntimeException("Error while getting qoute from Forismatic [" + response.getStatusInfo().getReasonPhrase() + "]");
        }
        
    }
    
    private Quote toQuote(String json){
        
        Quote quote = new Quote();
        try (JsonReader reader = Json.createReader(new StringReader(json))) {
            JsonObject jsonObject = reader.readObject();
            
            String text = jsonObject.getString(KEY_TEXT);
            if(text!=null)text = text.trim();
            quote.setText(text);
            
            String author = jsonObject.getString(KEY_AUTHOR);
            if(author!=null)author = author.trim();
            quote.setAuthor(author);
        }
        
        return quote;
    }
    
    @PostConstruct
    public void init(){
        
        ClientBuilder cb = ClientBuilder.newBuilder();
        
        if(proxyInfo.isConfigured()){
            log.log(Level.INFO, "Using proxy server [{0}]", proxyInfo.getProxyHost());
            cb.property("com.ibm.ws.jaxrs.client.proxy.host", proxyInfo.getProxyHost());
            cb.property("com.ibm.ws.jaxrs.client.proxy.port", proxyInfo.getProxyPort());
            cb.property("com.ibm.ws.jaxrs.client.proxy.type", proxyInfo.getProxyType());
            cb.property("com.ibm.ws.jaxrs.client.proxy.username", proxyInfo.getProxyUsername());
            cb.property("com.ibm.ws.jaxrs.client.proxy.password", proxyInfo.getProxyPassword());
            this.client = cb.build();
        }else{
            this.client = ClientBuilder.newClient();
        }
        
        this.form = new Form()
            .param("method", forismaticConfig.getMethod())
            .param("lang", forismaticConfig.getLang())
            .param("format", "json");
    }
    
    @PreDestroy
    public void destroy(){
        this.client.close();
        this.client = null;
    }
    
    private Client client;
    private Form form;
    
    private static final String KEY_TEXT = "quoteText";
    private static final String KEY_AUTHOR = "quoteAuthor";
    
}
