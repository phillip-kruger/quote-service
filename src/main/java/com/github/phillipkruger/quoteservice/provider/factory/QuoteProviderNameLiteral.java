package com.github.phillipkruger.quoteservice.provider.factory;

import javax.enterprise.util.AnnotationLiteral;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QuoteProviderNameLiteral extends AnnotationLiteral<QuoteProviderName> implements QuoteProviderName {

    private final String name;

    @Override
    public String value() {
        return this.name;
    }
}