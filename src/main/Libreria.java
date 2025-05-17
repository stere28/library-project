package main;

import java.util.List;

public interface Libreria<T extends Filtro> extends Iterable<Libro>{
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
    void setFiltro(T filtro);
}