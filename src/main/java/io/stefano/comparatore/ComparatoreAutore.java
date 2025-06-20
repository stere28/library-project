package io.stefano.comparatore;

import java.util.*;
import io.stefano.Libro;

public class ComparatoreAutore implements ComparatoreLibri {
    @Override
    public int compare(Libro o1, Libro o2) {
        String primoAutore1 = getPrimoAutoreOrDefault(o1.getAutori());
        String primoAutore2 = getPrimoAutoreOrDefault(o2.getAutori());
        return primoAutore1.compareToIgnoreCase(primoAutore2);
    }

    private String getPrimoAutoreOrDefault(Set<String> autori) {
        if (autori == null || autori.isEmpty()) return "";
        return autori.stream().sorted(String::compareToIgnoreCase).findFirst().orElse("");
    }
}
