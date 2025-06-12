package io.stefano.filtri;

import io.stefano.Libro;

public class BuildFiltroPredicato implements BuildFiltro {

    //TODO gestire le eccezioni
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
        filtroGenere.setNext(filtro);
        filtro = filtroGenere ;
    }

    @Override
    public void addPerStato(Libro.Stato stato) {
        Filtro filtroStato = new FiltroStato(stato);
        filtroStato.setNext(filtro);
        filtro = filtroStato;
    }

    @Override
    public void addPerText(String text) {
        Filtro filtroText = new FiltroTitoloAutore(text);
        filtroText.setNext(filtro);
        filtro = filtroText;
    }

    @Override
    public void addPerValutazione(int min, int max) {
        Filtro filtroValutazione = new FiltroValutazione(min, max);
        filtroValutazione.setNext(filtro);
        filtro = filtroValutazione;
    }

    @Override
    public Filtro getFiltro() {
        return filtro;
    }
}
