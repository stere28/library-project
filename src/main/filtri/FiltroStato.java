package main.filtri;

import main.Libro;

public class FiltroStato implements Filtro{
    private Libro.Stato stato;
    protected FiltroStato(Libro.Stato stato){
        this.stato = stato;
    }
    @Override
    public boolean test(Libro libro) {
        return stato == libro.getStato();
    }
}
