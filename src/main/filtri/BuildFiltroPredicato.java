package main.filtri;

import main.Libro;

import java.util.Set;

public class BuildFiltroPredicato implements BuildFiltro{
    private Filtro filtro;
    public BuildFiltroPredicato(){
        filtro = new NessunFiltro();
    }

    @Override
    public void addPerGenere(Set<Libro.Genere> genere) {
        //TODO
    }

    @Override
    public void addPerStato(Set<Libro.Stato> stato) {
        //TODO
    }

    @Override
    public void addPerAutore(String autore) {
        //TODO
    }

    @Override
    public void addPerTitolo(String titolo) {
        //TODO
    }

    @Override
    public void addPerValutazione(int min, int max) {
        //TODO
    }

    @Override
    public Filtro getFiltro() {
        return null; //TODO
    }
}
