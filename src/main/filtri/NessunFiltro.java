package main.filtri;

import main.Libro;

public class NessunFiltro implements Filtro{
    @Override
    public boolean test(Libro libro) {
        return true;
    }
}