package main.comparatore;

import main.Libro;

public class ComparatoreValutazione implements ComparatoreLibri {
    @Override
    public int compare(Libro o1, Libro o2) {
        return Integer.compare(o2.getValutazione(), o1.getValutazione());
    }
}

