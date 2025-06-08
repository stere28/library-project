package io.stefano;

import io.stefano.comparatore.ComparatoreLibri;
import io.stefano.filtri.Filtro;

import java.util.List;
/**
 * Rappresenta una collezione modificabile, filtrabile e ordinabile di libri.
 * L'ordine e la selezione dei libri dipendono dall'ordinamento e filtro impostati.
 */
public interface Libreria extends Iterable<Libro>{
    /**
     * Restituisce una lista dei libri visibili, secondo l'ordinamento e il filtro corrente.
     * La lista restituita è una copia indipendente dalla libreria.
     * @return lista dei libri visibili, ordinati secondo il comparatore impostato
     */
    List<Libro> getLibri();
    /**
     * Aggiunge un libro alla libreria.
     * @param libro il libro da aggiungere (non nullo)
     * @throws IllegalArgumentException se il libro è null o già presente
     */
    void aggiungiLibro(Libro libro);
    /**
     * Rimuove un libro dalla libreria.
     * @param libro il libro da rimuovere (non nullo)
     * @throws IllegalArgumentException se il libro non è presente
     */
    void rimuoviLibro(Libro libro);
    /**
     * Imposta l'ordinamento dei libri.
     * @param comparatore il comparatore da usare (null per ordinamento default)
     */
    void setOrdine(ComparatoreLibri comparatore);
    /**
     * Imposta un filtro per selezionare i libri.
     * @param filtro il filtro da applicare (null per filtro default)
     */
    void setFiltro(Filtro filtro);
    /**
     * Verifica se la libreria contiene un libro specifico.
     * @param libro il libro da cercare
     * @return true se il libro è presente e visibile (supera i filtri), false altrimenti
     */
    default boolean contiene(Libro libro) {
        for (Libro l : this) {
            if (l.equals(libro)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se la libreria è vuota (nessun libro visibile).
     * @return true se nessun libro è visibile, false altrimenti
     */
    default boolean isEmpty() {
        return !iterator().hasNext();
    }

    /**
     * Restituisce il numero di libri nella libreria, considerando eventuali filtri applicati.
     * @return numero di libri visibili
     */
    default int size() {
        int count = 0;
        for (Libro libro : this) {
            count++;
        }
        return count;
    }

}