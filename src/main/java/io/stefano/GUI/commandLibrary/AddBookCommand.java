package io.stefano.GUI.commandLibrary;

import io.stefano.GUI.Applicazione;
import io.stefano.Libreria;
import io.stefano.Libro;

public class AddBookCommand implements Command, Cloneable{
    private final Applicazione applicazione;
    private final Libreria libreria;
    private  Libro libro;

    public AddBookCommand(Libreria libreria, Libro libro, Applicazione app) {
        this.libreria = libreria;
        this.libro = libro;
        this.applicazione = app;
    }

    public AddBookCommand(Applicazione applicazione, Libreria libreria) {
        this.applicazione = applicazione;
        this.libreria = libreria;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    @Override
    public boolean doIt() {
        libreria.aggiungiLibro(libro);
        new CaricaLibreriaCommand(applicazione,libreria).doIt();
        return true; // Operazione annullabile
    }

    @Override
    public boolean undoIt() {
        libreria.rimuoviLibro(libro);
        new CaricaLibreriaCommand(applicazione,libreria).doIt();
        return true;
    }
    @Override
    public AddBookCommand clone() {
        try {
            return (AddBookCommand) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Clone not supported", e);
        }
    }
}