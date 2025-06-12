package io.stefano.GUI.commandLibrary;

import io.stefano.GUI.Applicazione;
import io.stefano.Libreria;
import io.stefano.Libro;

public class RemoveBookCommand implements Command, Cloneable {
    private final Applicazione applicazione;
    private final Libreria libreria;
    private Libro libro;

    public RemoveBookCommand(Applicazione applicazione1, Libreria libreria, Libro libro) {
        this.applicazione = applicazione1;
        this.libreria = libreria;
        this.libro = libro;

    }

    public RemoveBookCommand(Applicazione applicazione, Libreria libreria) {
        this.applicazione = applicazione;
        this.libreria = libreria;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public boolean doIt() {
        libreria.rimuoviLibro(libro);
        new CaricaLibreriaCommand(applicazione,libreria).doIt();
        return true; // Operazione annullabile
    }

    @Override
    public boolean undoIt() {
        libreria.aggiungiLibro(libro);
        new CaricaLibreriaCommand(applicazione,libreria).doIt();
        return true;
    }

    @Override
    public RemoveBookCommand clone() {
        try {
            return (RemoveBookCommand) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Clone not supported", e);
        }
    }
}