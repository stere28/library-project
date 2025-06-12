package io.stefano.filtri;

import io.stefano.Libro;

public class FiltroGenere implements Filtro{
    private Libro.Genere genere;
    private Filtro next = new NessunFiltro();
    protected FiltroGenere(Libro.Genere genere){
        this.genere = genere;
    }
    @Override
    public boolean test(Libro libro) {
        if(libro.getGenere() != genere) return false;
        return next.test(libro);
    }

    @Override
    public void setNext(Filtro filtro) {
        this.next = next;
    }
}
