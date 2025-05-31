package main.filtri;

import main.Libro;

import java.util.Set;

public interface BuildFiltro {
    void addPerGenere(Libro.Genere genere);
    void addPerStato(Libro.Stato stato);
    public void addPerText(String text);
    void addPerValutazione(int min, int max);
    Filtro getFiltro();
}
