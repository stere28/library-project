package main;

import java.util.List;
import java.util.Set;

public class Libro {
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

    public Libro(String titolo, Set<String> autori, String isbn, Genere genere, int valutazione, Stato stato) {
        if (titolo.isEmpty() || autori.isEmpty()) {
            throw  new IllegalArgumentException("Compila tutti i campi obbligatori.");
        }
        if (isbn.isEmpty() || !(isValidIsbn10(isbn) || isValidIsbn13(isbn))) {
            throw new IllegalArgumentException("ISBN non valido: deve essere ISBN-10 o ISBN-13");
        }
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

    // Controllo semplificato ISBN-10 (10 caratteri, ultimi 9 cifre + cifra o 'X')
    private boolean isValidIsbn10(String isbn) {
        if (isbn.length() != 10) return false;
        for (int i = 0; i < 9; i++) {
            if (!Character.isDigit(isbn.charAt(i))) return false;
        }
        char last = isbn.charAt(9);
        return Character.isDigit(last) || last == 'X' || last == 'x';
    }

    // Controllo semplificato ISBN-13 (13 cifre)
    private boolean isValidIsbn13(String isbn) {
        if (isbn.length() != 13) return false;
        for (int i = 0; i < 13; i++) {
            if (!Character.isDigit(isbn.charAt(i))) return false;
        }
        return true;
    }
    public String getTitolo() {
        return titolo;
    }
    public Set<String> getAutori() {
        return Set.copyOf(autori);
    }
    public String getIsbn() {
        return isbn;
    }
    public Genere getGenere() {
        return genere;
    }
    public int getValutazione() {
        return valutazione;
    }
    public Stato getStato() {
        return stato;
    }
    public void setStato(Stato stato) {
        this.stato = stato;
    }

//    public static Libro fromCSV(String line) {
//        String[] parts = line.split(",");
//        if (parts.length != 6) { //NON PENSO SIA NECESSARIO IN QUANTO SIAMO NOI A SCRIVERE SUL FILE
//            throw new IllegalArgumentException("Formato CSV non valido: " + line);
//        }
//        return new Libro(parts[0], parts[1], Boolean.parseBoolean(parts[2]));
//    }
//
//    public static String toCSV(Libro libro) {
//
//        return String.join(",",
//                libro.getTitolo(),
//                libro.getAutore(),
//                libro.getIsbn(),
//                libro.getGenere().toString(),
//                String.valueOf(libro.getValutazione()),
//                libro.getStato().toString()
//        );
//    }

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
    @Override
    public String toString() {
        return "LibroImpl{" +
                "titolo='" + titolo + '\'' +
                ", autore='" + autori + '\'' +
                ", isbn='" + isbn + '\'' +
                ", genere=" + genere +
                ", valutazione=" + valutazione +
                ", stato=" + stato +
                '}';
    }
}