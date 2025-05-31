package main.GUI.commandLibrary;

import main.GUI.Applicazione;
import main.Libreria;
import main.Libro;

public class AddBookCommand implements Command {
    private final Applicazione app;
    private final Libreria libreria;
    private final Libro libro;

    public AddBookCommand(Libreria libreria, Libro libro, Applicazione app) {
        this.libreria = libreria;
        this.libro = libro;
        this.app = app;
    }

    @Override
    public boolean doIt() {
        libreria.aggiungiLibro(libro);
        new ReloadCommand(app).doIt();
        return true; // Operazione annullabile
    }

    @Override
    public boolean undoIt() {
        libreria.rimuoviLibro(libro);
        new ReloadCommand(app).doIt();
        return true;
    }
}