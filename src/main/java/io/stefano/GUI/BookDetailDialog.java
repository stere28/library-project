package io.stefano.GUI;

import io.stefano.GUI.commandLibrary.EditBookCommand;
import io.stefano.GUI.commandLibrary.HistoryCommandHandler;
import io.stefano.GUI.commandLibrary.RemoveBookCommand;
import io.stefano.Libreria;
import io.stefano.Libro;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class BookDetailDialog extends JDialog {
    private final HistoryCommandHandler handler;
    private final Libro libroOriginale;

    private final JTextField titoloField;
    private final JTextField autoriField;
    private final JTextField isbnField;
    private final JComboBox<Integer> valutazioneCombo;
    private final JComboBox<Libro.Genere> genereCombo;
    private final JComboBox<Libro.Stato> statoCombo;
    private final EditBookCommand editBookCommand;
    private final RemoveBookCommand removeBookCommand;

    public BookDetailDialog(Applicazione applicazione, HistoryCommandHandler handler, Libro libro,
                            EditBookCommand editBookCommand, RemoveBookCommand removeBookCommand) {
        super(applicazione, "Dettagli Libro", true);
        this.handler = handler;
        this.libroOriginale = libro;
        this.editBookCommand = editBookCommand.clone();
        this.removeBookCommand = removeBookCommand.clone();

        setSize(400, 400);
        setLocationRelativeTo(applicazione);
        setLayout(new BorderLayout());

        // Pannello contenitore
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));

        // Campi di input
        titoloField = new JTextField(libro.getTitolo());
        autoriField = new JTextField(String.join(", ", libro.getAutori()));
        isbnField = new JTextField(libro.getIsbn());

        // Combo box per valutazione
        Integer[] valutazioni = {1, 2, 3, 4, 5};
        valutazioneCombo = new JComboBox<>(valutazioni);
        valutazioneCombo.setSelectedItem(libro.getValutazione());

        // Combo box per genere e stato
        genereCombo = new JComboBox<>(Libro.Genere.values());
        genereCombo.setSelectedItem(libro.getGenere());


        statoCombo = new JComboBox<>(Libro.Stato.values());
        statoCombo.setSelectedItem(libro.getStato());

        // Aggiunta componenti al form
        formPanel.add(new JLabel("Titolo:"));
        formPanel.add(titoloField);
        formPanel.add(new JLabel("Autori:"));
        formPanel.add(autoriField);
        formPanel.add(new JLabel("ISBN:"));
        formPanel.add(isbnField);
        formPanel.add(new JLabel("Valutazione:"));
        formPanel.add(valutazioneCombo);
        formPanel.add(new JLabel("Genere:"));
        formPanel.add(genereCombo);
        formPanel.add(new JLabel("Stato:"));
        formPanel.add(statoCombo);

        // Pannello pulsanti
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Salva Modifiche");
        JButton deleteButton = new JButton("Elimina Libro");
        JButton cancelButton = new JButton("Annulla");

        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);

        // Aggiunta al dialog
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Gestione eventi
        saveButton.addActionListener(e -> {
            Set<String> autori = new HashSet<>();
            for (String autore : autoriField.getText().split(",")) {
                String nomePulito = autore.trim();
                if (!nomePulito.isEmpty()) {
                    autori.add(nomePulito);
                }
            }
            try {
                Libro libroModificato = new Libro(titoloField.getText(), autori, isbnField.getText(),
                        (Libro.Genere) genereCombo.getSelectedItem(),
                        (int) valutazioneCombo.getSelectedItem(),
                        (Libro.Stato) statoCombo.getSelectedItem());
                EditBookCommand cmd = editBookCommand.clone();
                cmd.setLibroNuovo(libroModificato);
                cmd.setLibroOriginale(libroOriginale);
                handler.handle(cmd);
            }catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore di validazione", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dispose();
        });
        deleteButton.addActionListener(e -> {
            RemoveBookCommand cmd = removeBookCommand.clone();
            cmd.setLibro(libroOriginale);
            handler.handle(cmd);
            dispose();});
        cancelButton.addActionListener(e -> dispose());
        setVisible(true);
    }
}
