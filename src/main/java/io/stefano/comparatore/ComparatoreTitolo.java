package io.stefano.comparatore;

import io.stefano.Libro;

public class ComparatoreTitolo implements ComparatoreLibri {
    @Override
    public int compare(Libro o1, Libro o2) {
        return o1.getTitolo().compareToIgnoreCase(o2.getTitolo());
    }
}
