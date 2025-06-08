package io.stefano.implementazioni;

import io.stefano.Libro;
import io.stefano.comparatore.ComparatoreLibri;
import io.stefano.filtri.Filtro;

import java.util.List;

/**
 * Interfaccia per la persistenza dei libri, con operazioni CRUD e metodi per il recupero
 * con filtraggio e ordinamento.
 */
public interface LibreriaPersistente {

    /**
     * Salva un libro nel sistema di persistenza.
     *
     * @param libro il libro da salvare (non nullo)
     * @throws IllegalArgumentException se il libro è null o già esistente
     */
    void salvaLibro(Libro libro);

    /**
     * Rimuove un libro dal sistema di persistenza.
     *
     * @param libro il libro da rimuovere (non nullo)
     * @return true se il libro è stato rimosso, false se non esisteva
     * @throws IllegalArgumentException se il libro è null
     */
    void rimuoviLibro(Libro libro);

    /**
     * Carica tutti i libri che soddisfano il filtro, nell'ordine specificato.
     *
     * @param filtro      il filtro da applicare (null per nessun filtro)
     * @param comparatore il comparatore per l'ordinamento (null per ordinamento naturale)
     * @return lista non modificabile dei libri trovati
     */
    List<Libro> caricaLibreria(Filtro filtro, ComparatoreLibri comparatore);
}