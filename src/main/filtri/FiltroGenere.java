package main.filtri;

import main.Libro;

public class FiltroGenere implements Filtro{
    @Override
    public boolean test(Libro libro) {
        return false;
    }
}
