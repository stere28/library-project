package io.stefano.filtri;

import io.stefano.Libro;

public class FiltroValutazione implements Filtro {
    private final double sogliaMin;
    private final double sogliaMax;
    private Filtro next = new NessunFiltro();

    public FiltroValutazione(double sogliaMin, double sogliaMax) {
        this.sogliaMin = sogliaMin;
        this.sogliaMax = sogliaMax;
    }

    @Override
    public boolean test(Libro libro) {
        double val = libro.getValutazione();
        if( val < sogliaMin || val > sogliaMax) return false;
        return next.test(libro);
    }
    @Override
    public void setNext(Filtro next) {
        this.next = next;
    }
}
