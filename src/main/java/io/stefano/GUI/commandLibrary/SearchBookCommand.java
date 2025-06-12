package io.stefano.GUI.commandLibrary;

import io.stefano.GUI.Applicazione;
import io.stefano.Libreria;
import io.stefano.comparatore.ComparatoreLibri;
import io.stefano.filtri.Filtro;

public class SearchBookCommand implements Command, Cloneable{
    private Filtro filtro;
    private ComparatoreLibri comparatore;
    private boolean reverse;
    private final Libreria libreria;
    private final Applicazione applicazione;
    public SearchBookCommand(Libreria libreria, Applicazione app, Filtro filtro,ComparatoreLibri comparatore,boolean reverse ){
        this.comparatore = comparatore;
        this.reverse = reverse;
        this.libreria = libreria;
        this.filtro = filtro;
        this.applicazione = app;
    }

    public SearchBookCommand(Applicazione applicazione, Libreria libreria) {
        this.libreria = libreria;
        this.applicazione = applicazione;
    }

    public void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }

    public void setComparatore(ComparatoreLibri comparatore) {
        this.comparatore = comparatore;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }

    @Override
    public boolean doIt() {
        libreria.setFiltro(filtro);
        if(reverse) {
            libreria.setOrdine((ComparatoreLibri) comparatore.reversed());
        }
        else {
            libreria.setOrdine(comparatore);
        }
        new CaricaLibreriaCommand(applicazione,libreria).doIt();
        return false;
    }

    @Override
    public boolean undoIt() {
        return false;
    }

    @Override
    public SearchBookCommand clone() {
        try {
            return (SearchBookCommand) super.clone();
            //TODO potrebbe essere necessaria una copia profonda per filtri e comparatori
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Clone not supported", e);
        }
    }
}
