package main.filtri;

import main.Libro;

public class BuildFiltroPredicato implements BuildFiltro { //TODO gestire le eccezioni
    private Filtro filtro;

    public BuildFiltroPredicato() {
        filtro = new NessunFiltro();
    }

    @Override
    public void addPerGenere(Libro.Genere genere) {
        Filtro filtroGenere = new FiltroGenere(genere);
        filtro = (Filtro) filtroGenere.and(filtro);
    }

    @Override
    public void addPerStato(Libro.Stato stato) {
        Filtro filtroStato = new FiltroStato(stato);
        filtro = (Filtro) filtroStato.and(filtro);
    }

    @Override
    public void addPerAutore(String autore) {
        Filtro filtroAutore = new FiltroTitoloAutore(autore);
        filtro = (Filtro) filtroAutore.and(filtro);
    }

    @Override
    public void addPerTitolo(String titolo) {
        Filtro filtroTitolo = new FiltroTitoloAutore(titolo);
        filtro = (Filtro) filtroTitolo.and(filtro);
    }

    @Override
    public void addPerValutazione(int min, int max) {
        Filtro filtroValutazione = new FiltroValutazione(min, max);
        filtro = (Filtro) filtroValutazione.and(filtro);
    }

    @Override
    public Filtro getFiltro() {
        return filtro;
    }
}
