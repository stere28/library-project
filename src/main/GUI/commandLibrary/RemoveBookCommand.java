package main.GUI.commandLibrary;

import main.Libreria;
import main.Libro;

public class RemoveBookCommand implements Command {
    private final Libreria libreria;
    private final Libro libro;

    public RemoveBookCommand(Libreria libreria, Libro libro) {
        this.libreria = libreria;
        this.libro = libro;
    }

    @Override
    public boolean doIt() {
        libreria.rimuoviLibro(libro);
        return true; // Operazione annullabile
    }

    @Override
    public boolean undoIt() {
        libreria.aggiungiLibro(libro);
        return true;
    }
}