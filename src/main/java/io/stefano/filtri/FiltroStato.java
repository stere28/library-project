package io.stefano.filtri;

import io.stefano.Libro;

public class FiltroStato implements Filtro{
    private Libro.Stato stato;
    public FiltroStato(Libro.Stato stato){
        this.stato = stato;
    }
    @Override
    public boolean test(Libro libro) {
        return stato == libro.getStato();
    }
}
