package io.stefano.GUI.commandLibrary;

import io.stefano.GUI.Applicazione;
import io.stefano.Libreria;
import io.stefano.Libro;

public class EditBookCommand implements Command, Cloneable{
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

    public EditBookCommand(Applicazione applicazione, Libreria libreria) {
        this.applicazione = applicazione;
        this.libreria = libreria;
    }

    public void setLibroOriginale(Libro libroOriginale) {
        this.libroOriginale = libroOriginale;
    }

    public void setLibroNuovo(Libro libroNuovo) {
        this.libroNuovo = libroNuovo;
    }

    @Override
    public boolean doIt() {
        libreria.rimuoviLibro(libroOriginale);
        libreria.aggiungiLibro(libroNuovo);
        new CaricaLibreriaCommand(applicazione,libreria).doIt();
        return true;
    }

    @Override
    public boolean undoIt() {
        libreria.rimuoviLibro(libroNuovo);
        libreria.aggiungiLibro(libroOriginale);
        new CaricaLibreriaCommand(applicazione,libreria).doIt();
        return true;
    }

    @Override
    public EditBookCommand clone()  {
        try {
            return (EditBookCommand) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Clone not supported", e);        }
    }
}
