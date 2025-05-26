package main.filtri;

import main.Libro;

public class FiltroTitoloAutore implements Filtro {
    @Override
    public boolean test(Libro libro) {
        return false;
    }
}
