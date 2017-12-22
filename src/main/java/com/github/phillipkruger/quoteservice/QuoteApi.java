package com.github.phillipkruger.quoteservice;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.extern.java.Log;

/**
 * The API to get the quote
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@Log
@Path("/")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML}) @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class QuoteApi {
    
    @Inject
    private QuoteService service;
    
    @GET
    public Quote getQuote(){
        return service.getQuote();
    }
    
}