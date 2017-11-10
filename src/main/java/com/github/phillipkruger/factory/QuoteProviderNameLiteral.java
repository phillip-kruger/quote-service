package com.github.phillipkruger.factory;

import javax.enterprise.util.AnnotationLiteral;

public class QuoteProviderNameLiteral extends AnnotationLiteral<QuoteProviderName> implements QuoteProviderName {

    final String name;

    QuoteProviderNameLiteral(String name) {
        this.name = name;
    }

    @Override
    public String value() {
        return name;
    }
}