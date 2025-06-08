package io.stefano;

import io.stefano.comparatore.ComparatoreLibri;
import io.stefano.comparatore.ComparatoreTitolo;
import io.stefano.filtri.Filtro;
import io.stefano.filtri.NessunFiltro;
import io.stefano.implementazioni.LibreriaPersistente;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class LibreriaConcreta implements Libreria {
    private LibreriaPersistente implementazione;
    private ComparatoreLibri comparatore = new ComparatoreTitolo();
    private Filtro filtro = new NessunFiltro();

    public LibreriaConcreta(LibreriaPersistente implementazione) {
        this.implementazione = Objects.requireNonNull(implementazione, "L'implementazione non può essere null");
    }
    @Override
    public List<Libro> getLibri() {
        return implementazione.caricaLibreria(filtro,comparatore);
    }

    @Override
    public void aggiungiLibro(Libro libro) {
        Objects.requireNonNull(libro, "Il libro non può essere null");
        implementazione.salvaLibro(libro);

    }

    @Override
    public void rimuoviLibro(Libro libro) {
        Objects.requireNonNull(libro, "Il libro non può essere null");
        implementazione.rimuoviLibro(libro);

    }

    @Override
    public void setOrdine(ComparatoreLibri comparatore) {
        this.comparatore = comparatore;
    }

    @Override
    public void setFiltro(Filtro filtro) {
        this.filtro = filtro; //TODO se è mutabile fare la copia
    }

    @Override
    public Iterator<Libro> iterator() {
        return getLibri().iterator();
    }
}
