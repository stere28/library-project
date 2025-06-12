package io.stefano.GUI.commandLibrary;

import io.stefano.GUI.Applicazione;
import io.stefano.Libreria;

/**
 * Comando che aggiorna la vista dei libri (JList) nella GUI.
 * Non è annullabile.
 */
public class CaricaLibreriaCommand implements Command, Cloneable {

    private final Applicazione applicazione;
    private final Libreria libreria;

    public CaricaLibreriaCommand(Applicazione applicazione, Libreria libreria) {
        this.applicazione = applicazione;
        this.libreria = libreria;
    }


    @Override
    public boolean doIt() {
        applicazione.aggiornaListaLibri(libreria.getLibri());
        return false;
    }

    @Override
    public boolean undoIt() {
        return false;
    }

    @Override
    public CaricaLibreriaCommand clone() {
        try {
            return (CaricaLibreriaCommand) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Clone not supported", e);
        }
    }
}
