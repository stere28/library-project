package io.stefano.filtri;

import io.stefano.Libro;

public class FiltroGenere implements Filtro{
    private Libro.Genere genere;
    protected FiltroGenere(Libro.Genere genere){
        this.genere = genere;
    }
    @Override
    public boolean test(Libro libro) {
        return libro.getGenere() == genere;
    }
}
