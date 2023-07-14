package de.manuelclever.satisfactorycalculator.languages;

import java.util.ArrayList;
import java.util.Collection;

public class myLanguageList<Locale> extends ArrayList<java.util.Locale> {

    public myLanguageList(Collection<? extends java.util.Locale> c) {
        super(c);
    }

    @Override
    public boolean add(java.util.Locale locale) {
        return super.add(locale);
    }

    public java.util.Locale get(String s) {
        for(java.util.Locale locale : this) {
            if(locale.getLanguage().equals(s)) {
                return locale;
            }
        }
        return null;
    }
}
