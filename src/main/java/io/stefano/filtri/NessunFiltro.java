package io.stefano.filtri;

import io.stefano.Libro;

public class NessunFiltro implements Filtro{
    @Override
    public boolean test(Libro libro) {
        return true;
    }

    @Override
    public void setNext(Filtro next) {
    }
}