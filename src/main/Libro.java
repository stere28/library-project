package main;

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
    private String autore;
    private String isbn;
    private Genere genere;
    private int valutazione; // da 1 a 5
    private Stato stato;

    public Libro(String titolo, String autore, String isbn, Genere genere, int valutazione, Stato stato) {
        if (isbn == null || !(isValidIsbn10(isbn) || isValidIsbn13(isbn))) {
            throw new IllegalArgumentException("ISBN non valido: deve essere ISBN-10 o ISBN-13");
        }
        if (valutazione < 1 || valutazione > 5) {
            throw new IllegalArgumentException("Valutazione deve essere compresa tra 1 e 5");
        }
        this.titolo = titolo;
        this.autore = autore;
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
    public String getAutore() {
        return autore;
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
                ", autore='" + autore + '\'' +
                ", isbn='" + isbn + '\'' +
                ", genere=" + genere +
                ", valutazione=" + valutazione +
                ", stato=" + stato +
                '}';
    }
}


