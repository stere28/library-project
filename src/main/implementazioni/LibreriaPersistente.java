package main.implementazioni;

import main.Libro;
import main.comparatore.ComparatoreLibri;
import main.filtri.Filtro;

import java.util.List;
import java.util.Set;

public interface LibreriaPersistente {
    //TODO da rendere singleton
    void salvaLibro(Libro libro);
    void rimuoviLibro(Libro libro);
    List<Libro> caricaLibreria(Filtro filtro, ComparatoreLibri comparatore);
}