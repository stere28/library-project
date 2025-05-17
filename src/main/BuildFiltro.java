package main;

public interface BuildFiltro {
    void perGenere(Libro.Genere genere);
    void perStato(Libro.Stato stato);
    void perAutore(String autore);
    void perTitolo(String titolo);
    void perValutazione(int min, int max);
}
