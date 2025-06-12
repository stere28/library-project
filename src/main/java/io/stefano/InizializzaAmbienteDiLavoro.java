package io.stefano;

import io.stefano.GUI.Applicazione;
import io.stefano.GUI.commandLibrary.*;
import io.stefano.implementazioni.LibreriaJSON;

public class InizializzaAmbienteDiLavoro {
    private final Applicazione applicazione;
    private final Libreria libreria;
    private final AddBookCommand addBookCommand;
    private final RemoveBookCommand removeBookCommand;
    private final EditBookCommand editBookCommand;
    private final SearchBookCommand searchBookCommand;
    private final CaricaLibreriaCommand caricaLibreriaCommand ;

    public InizializzaAmbienteDiLavoro(Applicazione applicazione) {
        this.applicazione = applicazione;
        libreria = new LibreriaConcreta(LibreriaJSON.INSTANCE);
        addBookCommand = new AddBookCommand(applicazione, libreria);
        removeBookCommand = new RemoveBookCommand(applicazione, libreria);
        editBookCommand = new EditBookCommand(applicazione, libreria);
        searchBookCommand = new SearchBookCommand(applicazione, libreria);
        caricaLibreriaCommand = new CaricaLibreriaCommand(applicazione,libreria);
    }

    public AddBookCommand getAddBookCommand() {
        return addBookCommand.clone();
    }

    public RemoveBookCommand getRemoveBookCommand() {
        return removeBookCommand.clone();
    }

    public EditBookCommand getEditBookCommand() {
        return editBookCommand.clone();
    }

    public SearchBookCommand getSearchBookCommand() {
        return searchBookCommand.clone();
    }
    public CaricaLibreriaCommand getCaricaLibreriaCommand(){
        return  caricaLibreriaCommand.clone();
    }

    public Libreria getLibreria() {
        return libreria;
    }
}
