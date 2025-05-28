package main.filtri;

import main.Libro;

import java.util.Set;

public interface BuildFiltro {
    void addPerGenere(Libro.Genere genere);
    void addPerStato(Libro.Stato stato);
    void addPerAutore(String autore);
    void addPerTitolo(String titolo);
    void addPerValutazione(int min, int max);
    Filtro getFiltro();
}
