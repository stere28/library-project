package io.stefano.filtri;

import io.stefano.Libro;

public class FiltroTitoloAutore implements Filtro {
    private final String testo;
    private Filtro next = new NessunFiltro();

    public FiltroTitoloAutore(String testo) {
        this.testo = testo.toLowerCase();
    }

    @Override
    public boolean test(Libro libro) {
        // Cerca nel titolo
        boolean matchTitolo = libro.getTitolo().toLowerCase().contains(testo);

        // Cerca in almeno un autore
        boolean matchAutore = libro.getAutori().stream()
                .anyMatch(autore -> autore.toLowerCase().contains(testo));

        if(! matchTitolo && ! matchAutore) return false;
        return next.test(libro);
    }
    @Override
    public void setNext(Filtro next) {
        this.next = next;
    }
}
