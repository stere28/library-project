package io.stefano.filtri;

import io.stefano.Libro;

public class FiltroStato implements Filtro{
    private Libro.Stato stato;
    private Filtro next = new NessunFiltro();
    protected FiltroStato(Libro.Stato stato){
        this.stato = stato;
    }
    @Override
    public boolean test(Libro libro) {
        if(stato != libro.getStato()) return false;
        return next.test(libro);
    }

    @Override
    public void setNext(Filtro next) {
        this.next = next;
    }
}
