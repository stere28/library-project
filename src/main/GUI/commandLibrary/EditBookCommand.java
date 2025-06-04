package main.GUI.commandLibrary;

import main.GUI.Applicazione;
import main.Libreria;
import main.Libro;

public class EditBookCommand implements Command{
    private final Applicazione applicazione;
    private final Libreria libreria;
    private Libro libroOriginale;
    private Libro libroNuovo;

    public EditBookCommand(Applicazione applicazione, Libreria libreria, Libro libroOriginale, Libro libroNuovo) {
        this.applicazione = applicazione;
        this.libreria = libreria;
        this.libroOriginale = libroOriginale;
        this.libroNuovo = libroNuovo;
    }

    @Override
    public boolean doIt() {
        libreria.rimuoviLibro(libroOriginale);
        libreria.aggiungiLibro(libroNuovo);
        new ReloadCommand(applicazione).doIt();
        return true;
    }

    @Override
    public boolean undoIt() {
        libreria.aggiungiLibro(libroOriginale);
        libreria.rimuoviLibro(libroNuovo);
        new ReloadCommand(applicazione).doIt();
        return true;
    }
}
