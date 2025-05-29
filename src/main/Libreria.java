package main;

import main.comparatore.ComparatoreLibri;
import main.filtri.Filtro;

import java.util.List;

public interface Libreria extends Iterable<Libro>{
    default int size(){
        int ret = 0;
        for(@SuppressWarnings("unused") Libro l: this){
            ret ++;
        }
        return ret;
    }
    List<Libro> getLibri();
    void aggiungiLibro(Libro libro);
    void rimuoviLibro(Libro libro);
    void setOrdine(ComparatoreLibri comparatore);
    void setFiltro(Filtro filtro);

}