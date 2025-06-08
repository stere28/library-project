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
    private final Libreria libreria;
    private final HistoryCommandHandler handler;
    private final Libro libroOriginale;

    private JTextField titoloField;
    private JTextField autoriField;
    private JTextField isbnField;
    private JComboBox<Integer> valutazioneCombo;
    private JComboBox<Libro.Genere> genereCombo;
    private JComboBox<Libro.Stato> statoCombo;

    public BookDetailDialog(Applicazione applicazione, HistoryCommandHandler handler, Libreria libreria, Libro libro) {
        super(applicazione, "Dettagli Libro", true);
        this.libreria = libreria;
        this.handler = handler;
        this.libroOriginale = libro;

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
            Libro libroModificato = new Libro(
                                titoloField.getText(),
                                autori,
                                isbnField.getText(),
                                (Libro.Genere) genereCombo.getSelectedItem(),
                                (int) valutazioneCombo.getSelectedItem(),
                                (Libro.Stato) statoCombo.getSelectedItem());
            handler.handle(new EditBookCommand(applicazione,libreria,libroOriginale,libroModificato));
            dispose();
        });
        deleteButton.addActionListener(e -> {
            handler.handle(new RemoveBookCommand(applicazione,libreria, libroOriginale));
            dispose();});
        cancelButton.addActionListener(e -> dispose());
        setVisible(true);
    }
}
