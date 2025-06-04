package main.GUI.commandLibrary;

import main.GUI.Applicazione;
import main.Libreria;
import main.Libro;

public class RemoveBookCommand implements Command {
    private final Applicazione applicazione;
    private final Libreria libreria;
    private final Libro libro;

    public RemoveBookCommand(Applicazione applicazione1, Libreria libreria, Libro libro) {
        this.applicazione = applicazione1;
        this.libreria = libreria;
        this.libro = libro;

    }

    @Override
    public boolean doIt() {
        libreria.rimuoviLibro(libro);
        new ReloadCommand(applicazione).doIt();
        return true; // Operazione annullabile
    }

    @Override
    public boolean undoIt() {
        libreria.aggiungiLibro(libro);
        new ReloadCommand(applicazione).doIt();
        return true;
    }
}