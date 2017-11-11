package com.github.phillipkruger.quoteservice;

import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import lombok.extern.java.Log;
import org.junit.Assert;
import org.junit.Test;

/**
 * Testing endpoints
 * @author phillip.kruger@gmail.com
 */
@Log
public class QuoteApiIT {
    private final RestCaller restCaller = new RestCaller();
    
    @Test
    public void getMessageProvider(){
        URI uri = UriBuilder.fromUri("http://localhost:9000/quote-service/api/").build();
        String quote = restCaller.sendRequest(uri, "GET").readEntity(String.class);
        Assert.assertNotNull(quote);
        log.severe(quote);
    }
}
