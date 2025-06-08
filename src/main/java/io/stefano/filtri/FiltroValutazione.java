package io.stefano.filtri;

import io.stefano.Libro;

public class FiltroValutazione implements Filtro {
    private final double sogliaMin;
    private final double sogliaMax;

    public FiltroValutazione(double sogliaMin, double sogliaMax) {
        this.sogliaMin = sogliaMin;
        this.sogliaMax = sogliaMax;
    }

    @Override
    public boolean test(Libro libro) {
        double val = libro.getValutazione();
        return val >= sogliaMin && val <= sogliaMax;
    }
}
