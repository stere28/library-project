package io.stefano.GUI.commandLibrary;

import io.stefano.GUI.Applicazione;
import io.stefano.Libreria;
import io.stefano.comparatore.ComparatoreLibri;
import io.stefano.filtri.Filtro;

public class SearchBookCommand implements Command{
    private Filtro filtro;
    private ComparatoreLibri comparatore;
    private boolean reverse;
    private Libreria libreria;
    private Applicazione app;
    public SearchBookCommand(Libreria libreria, Applicazione app, Filtro filtro,ComparatoreLibri comparatore,boolean reverse ){
        this.comparatore = comparatore;
        this.reverse = reverse;
        this.libreria = libreria;
        this.filtro = filtro;
        this.app = app;
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
        new ReloadCommand(app).doIt();
        return false;
    }

    @Override
    public boolean undoIt() {
        return false;
    }
}
