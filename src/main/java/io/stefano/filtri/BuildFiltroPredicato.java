package io.stefano.filtri;

import io.stefano.Libro;

public class BuildFiltroPredicato implements BuildFiltro { //TODO gestire le eccezioni
    private Filtro filtro;

    public BuildFiltroPredicato() {
        filtro = new NessunFiltro();
    }
    public BuildFiltroPredicato(Filtro filtro){
        this.filtro = filtro;
    }

    @Override
    public void addPerGenere(Libro.Genere genere) {
        Filtro filtroGenere = new FiltroGenere(genere);
        filtro =  filtroGenere.and(filtro);
    }

    @Override
    public void addPerStato(Libro.Stato stato) {
        Filtro filtroStato = new FiltroStato(stato);
        filtro = filtroStato.and(filtro);
    }

    @Override
    public void addPerText(String text) {
        Filtro filtroText = new FiltroTitoloAutore(text);
        filtro = filtroText.and(filtro);
    }

    @Override
    public void addPerValutazione(int min, int max) {
        Filtro filtroValutazione = new FiltroValutazione(min, max);
        filtro = filtroValutazione.and(filtro);
    }

    @Override
    public Filtro getFiltro() {
        return filtro;
    }
}
