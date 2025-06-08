package io.stefano.filtri;

import io.stefano.Libro;

public interface BuildFiltro {
    void addPerGenere(Libro.Genere genere);
    void addPerStato(Libro.Stato stato);
    public void addPerText(String text);
    void addPerValutazione(int min, int max);
    Filtro getFiltro();
}
