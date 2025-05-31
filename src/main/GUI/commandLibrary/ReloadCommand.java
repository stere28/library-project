package main.GUI.commandLibrary;

import main.GUI.Applicazione;

/**
 * Comando che aggiorna la vista dei libri (JList) nella GUI.
 * Non è annullabile.
 */
public class ReloadCommand implements Command {

    private final Applicazione applicazione;

    public ReloadCommand(Applicazione applicazione) {
        this.applicazione = applicazione;
    }

    @Override
    public boolean doIt() {
        applicazione.aggiornaListaLibri();
        return false;
    }

    @Override
    public boolean undoIt() {
        // non si può annullare
        return false;
    }
}
