package com.github.phillipkruger.quoteservice;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Some information on (if any) the proxy
 * @author Phillip Kruger (phillip.kruger@phillip-kruger.com)
 */
@ApplicationScoped
public class ProxyInfo {
    
    @Getter
    @Inject
    @ConfigProperty(name = "proxy.host",defaultValue = "")
    private String proxyHost;
    
    @Getter
    @Inject
    @ConfigProperty(name = "proxy.port",defaultValue = "8080")
    private int proxyPort;
    
    @Getter
    @Inject
    @ConfigProperty(name = "proxy.type",defaultValue = "HTTP")
    private String proxyType;
    
    @Getter
    @Inject
    @ConfigProperty(name = "proxy.username",defaultValue = "")
    private String proxyUsername;
    
    @Getter
    @Inject
    @ConfigProperty(name = "proxy.password",defaultValue = "")
    private String proxyPassword;

    public boolean isConfigured(){
        return this.getProxyHost()!=null && !this.getProxyHost().isEmpty();
    }
    
}
