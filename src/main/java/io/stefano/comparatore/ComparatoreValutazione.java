package io.stefano.comparatore;

import io.stefano.Libro;

public class ComparatoreValutazione implements ComparatoreLibri {
    @Override
    public int compare(Libro o1, Libro o2) {
        return Integer.compare(o2.getValutazione(), o1.getValutazione());
    }
}

