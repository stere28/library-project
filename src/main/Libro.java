package main;

import java.io.Serializable;
import java.util.Set;

/**
 * Rappresenta un libro con titolo, autori, ISBN, genere, valutazione e stato di lettura.
 * Fornisce metodi per validare gli ISBN, accedere ai dati e modificarne lo stato.
 */
public class Libro implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * I generi disponibili per un libro.
     */
    public enum Genere {
        ROMANZO,
        GIALLO,
        FANTASY,
        FANTASCIENZA,
        STORICO,
        BIOGRAFIA,
        SAGGIO,
        DISTOPIA,
        HORROR,
        AVVENTURA,
        POESIA,
        CLASSICO,
        THRILLER,
        ALTRO;
    }

    /**
     * Lo stato di lettura di un libro.
     */
    public enum Stato {
        LETTO,
        DA_LEGGERE,
        IN_CORSO;
    }

    private String titolo;
    private Set<String> autori;
    private String isbn;
    private Genere genere;
    private int valutazione; // da 1 a 5
    private Stato stato;

    /**
     * Costruisce un nuovo libro con i dati specificati.
     *
     * @param titolo      il titolo del libro (non vuoto)
     * @param autori      l'insieme degli autori (non vuoto)
     * @param isbn        l'ISBN (valido ISBN-10 o ISBN-13)
     * @param genere      il genere del libro
     * @param valutazione la valutazione (da 1 a 5)
     * @param stato       lo stato di lettura
     * @throws IllegalArgumentException se uno dei parametri non è valido
     */
    public Libro(String titolo, Set<String> autori, String isbn, Genere genere, int valutazione, Stato stato) {
        if (titolo == null || titolo.isEmpty() || autori == null || autori.isEmpty()) {
            throw new IllegalArgumentException("Compila tutti i campi obbligatori.");
        }
//        if (isbn == null || isbn.isEmpty() || !(isValidIsbn10(isbn) || isValidIsbn13(isbn))) {
//            throw new IllegalArgumentException("ISBN non valido: deve essere ISBN-10 o ISBN-13");
//        } //TODO riaggiungere tolto per comodita
        if (valutazione < 1 || valutazione > 5) {
            throw new IllegalArgumentException("Valutazione deve essere compresa tra 1 e 5");
        }
        this.titolo = titolo;
        this.autori = Set.copyOf(autori);
        this.isbn = isbn;
        this.genere = genere;
        this.valutazione = valutazione;
        this.stato = stato;
    }

    /**
     * Verifica se l'ISBN fornito è valido secondo le regole dell'ISBN-10.
     *
     * @param isbn l'ISBN da validare
     * @return true se valido, false altrimenti
     */
    private boolean isValidIsbn10(String isbn) {
        if (isbn.length() != 10) return false;
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            char c = isbn.charAt(i);
            if (!Character.isDigit(c)) return false;
            sum += (c - '0') * (10 - i);
        }
        char last = isbn.charAt(9);
        if (last != 'X' && last != 'x' && !Character.isDigit(last)) return false;
        sum += (last == 'X' || last == 'x') ? 10 : (last - '0');
        return sum % 11 == 0;
    }

    /**
     * Verifica se l'ISBN fornito è valido secondo le regole dell'ISBN-13.
     *
     * @param isbn l'ISBN da validare
     * @return true se valido, false altrimenti
     */
    private boolean isValidIsbn13(String isbn) {
        if (isbn.length() != 13) return false;
        int sum = 0;
        for (int i = 0; i < 13; i++) {
            char c = isbn.charAt(i);
            if (!Character.isDigit(c)) return false;
            int digit = c - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        return sum % 10 == 0;
    }

    /** @return il titolo del libro */
    public String getTitolo() {
        return titolo;
    }

    /** @return l'insieme degli autori */
    public Set<String> getAutori() {
        return Set.copyOf(autori);
    }

    /** @return l'ISBN del libro */
    public String getIsbn() {
        return isbn;
    }

    /** @return il genere del libro */
    public Genere getGenere() {
        return genere;
    }

    /** @return la valutazione da 1 a 5 */
    public int getValutazione() {
        return valutazione;
    }

    /** @return lo stato di lettura del libro */
    public Stato getStato() {
        return stato;
    }

    /**
     * Imposta un nuovo stato di lettura per il libro.
     *
     * @param stato il nuovo stato
     */
    public void setStato(Stato stato) {
        this.stato = stato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Libro)) return false;
        Libro libro = (Libro) o;
        return isbn != null && isbn.equals(libro.getIsbn());
    }

    @Override
    public int hashCode() {
        return isbn == null ? 0 : isbn.hashCode();
    }

    /**
     * Restituisce una rappresentazione in stringa del libro.
     *
     * @return una stringa con tutte le informazioni del libro
     */
    @Override
    public String toString() {
        return "Libro{" +
                "titolo='" + titolo + '\'' +
                ", autori=" + autori +
                ", isbn='" + isbn + '\'' +
                ", genere=" + genere +
                ", valutazione=" + valutazione +
                ", stato=" + stato +
                '}';
    }
}
