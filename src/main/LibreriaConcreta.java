package main;

import main.comparatore.ComparatoreLibri;
import main.comparatore.ComparatoreTitolo;
import main.filtri.Filtro;
import main.filtri.NessunFiltro;
import main.implementazioni.LibreriaJSON;
import main.implementazioni.LibreriaPersistente;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LibreriaConcreta implements Libreria{
    private LibreriaPersistente implementazione;
    private ComparatoreLibri comparatore = new ComparatoreTitolo();
    private Filtro filtro = new NessunFiltro();

    public LibreriaConcreta(){
        implementazione = LibreriaJSON.INSTANCE;
    }
    @Override
    public List<Libro> getLibri() {
        return implementazione.caricaLibreria(filtro,comparatore);
    }

    @Override
    public void aggiungiLibro(Libro libro) {
        implementazione.salvaLibro(libro);
    }

    @Override
    public void rimuoviLibro(Libro libro) {
        implementazione.rimuoviLibro(libro);
    }

    @Override
    public void setOrdine(ComparatoreLibri comparatore) {
        this.comparatore = comparatore; //TODO
    }

    @Override
    public void setFiltro(Filtro filtro) {
        this.filtro = filtro; //TODO
    }

    @Override
    public Iterator<Libro> iterator() {
        return getLibri().iterator();
    }
}
