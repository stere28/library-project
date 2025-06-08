package io.stefano.GUI;

import io.stefano.GUI.commandLibrary.CommandHandler;
import io.stefano.GUI.commandLibrary.AddBookCommand;
import io.stefano.Libreria;
import io.stefano.Libro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class AddBookDialog extends JDialog implements ActionListener {
    private JTextField isbnField;
    private JTextField titoloField;
    private JTextField autoriField;
    private JComboBox<Libro.Genere> genereCombo;
    private JSpinner valutazioneSpinner;
    private JComboBox<Libro.Stato> statoCombo;
    private CommandHandler hadler;
    private Libreria libreria ;
    private Applicazione app;
    public AddBookDialog(Applicazione app, CommandHandler handler, Libreria libreria){

        super(app,"Aggiungi Nuovo Libro");
        setLayout(new GridLayout(0, 2, 5, 5));
        this.hadler = handler;
        this.libreria = libreria;
        this.app = app;

        // Componenti del form
        isbnField = new JTextField();
        titoloField = new JTextField();
        autoriField = new JTextField();
        genereCombo = new JComboBox<>(Libro.Genere.values());
        valutazioneSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
        statoCombo = new JComboBox<>(Libro.Stato.values());

        // Aggiungi componenti al dialog
        addFormRow( "ISBN:", isbnField);
        addFormRow( "Titolo:", titoloField);
        addFormRow( "Autori (separati da virgola):", autoriField);
        addFormRow( "Genere:", genereCombo);
        addFormRow( "Valutazione:", valutazioneSpinner);
        addFormRow( "Stato:", statoCombo);

        JPanel buttonPanel = new JPanel();
        JButton salvaButton = new JButton("Salva");
        salvaButton.addActionListener(this);
        buttonPanel.add(salvaButton);
        add(buttonPanel);

        pack();
        setLocationRelativeTo(this);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String isbn = isbnField.getText().trim();
        String titolo = titoloField.getText().trim();
        String autoriInput = autoriField.getText().trim();
        Libro.Genere genere = (Libro.Genere) genereCombo.getSelectedItem();
        int valutazione = (Integer) valutazioneSpinner.getValue();
        Libro.Stato stato = (Libro.Stato) statoCombo.getSelectedItem();

        // Suddividi gli autori da stringa a Set
        //TODO attenzione a gli input
        Set<String> autori = new HashSet<>();
        for (String autore : autoriInput.split(",")) {
            String nomePulito = autore.trim();
            if (!nomePulito.isEmpty()) {
                autori.add(nomePulito);
            }
        }

        // Crea oggetto Libro
        Libro nuovoLibro = null;
        try{nuovoLibro = new Libro(titolo, autori ,isbn,genere,valutazione,stato);}
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore di validazione", JOptionPane.ERROR_MESSAGE);
            return;
        }

        hadler.handle(new AddBookCommand(libreria, nuovoLibro, app));

        System.out.println("Nuovo libro creato: " + nuovoLibro);
        dispose(); // Chiude il dialog
    }


    private void addFormRow(String label, JComponent component) {
        this.add(new JLabel(label));
        this.add(component);
    }
}
