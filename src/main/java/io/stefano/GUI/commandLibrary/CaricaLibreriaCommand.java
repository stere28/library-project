package io.stefano.GUI.commandLibrary;

import io.stefano.GUI.Applicazione;
import io.stefano.Libreria;

/**
 * Comando che aggiorna la vista dei libri (JList) nella GUI.
 * Non è annullabile.
 */
public class CaricaLibreriaCommand implements Command {

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
        // non si può annullare
        return false;
    }
}
