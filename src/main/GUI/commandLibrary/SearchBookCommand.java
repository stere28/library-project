package main.GUI.commandLibrary;

import main.GUI.Applicazione;
import main.Libreria;
import main.comparatore.ComparatoreLibri;
import main.filtri.Filtro;

import java.util.Comparator;

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
