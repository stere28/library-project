package main.GUI;

import main.GUI.commandLibrary.CommandHandler;
import main.GUI.commandLibrary.NaiveCommandHandler;
import main.GUI.commandLibrary.SearchBookCommand;
import main.Libreria;
import main.LibreriaConcreta;
import main.Libro;
import main.comparatore.ComparatoreAutore;
import main.comparatore.ComparatoreLibri;
import main.comparatore.ComparatoreTitolo;
import main.comparatore.ComparatoreValutazione;
import main.filtri.BuildFiltro;
import main.filtri.BuildFiltroPredicato;
import main.filtri.Filtro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Search extends JPanel {
    private CommandHandler handler;
    private Applicazione app;
    private Libreria libreria;
    private Filtro filtro = new BuildFiltroPredicato().getFiltro();
    private ComparatoreLibri comparatore = new ComparatoreTitolo();
    private boolean reverse = false;

    private JTextField searchField;
    private JComboBox<String> ordinamentoCombo;
    private JButton filtriButton;
    private JButton searchButton;
    private JButton reverseButton;

    public Search(Applicazione app, Libreria libreria) {
        super();
        this.handler = handler;
        this.app = app;
        this.libreria = libreria;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        handler = new NaiveCommandHandler();

        // Pannello superiore
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(20);
        searchButton = new JButton("Cerca");
        filtriButton = new JButton("Filtri");
        filtriButton.addActionListener((e)-> mostraFinestraFiltri());

        topPanel.add(new JLabel("Ricerca:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(filtriButton);

        // Pannello ordinamento
        JPanel orderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ordinamentoCombo = new JComboBox<>(new String[]{"Titolo", "Autore", "Valutazione"});
        reverseButton = new JButton("Reverse");
        orderPanel.add(new JLabel("Ordina per:"));
        orderPanel.add(ordinamentoCombo);
        orderPanel.add(reverseButton);

        add(topPanel);
        add(orderPanel);

        // Gestione eventi
        searchButton.addActionListener((e)->{
            BuildFiltroPredicato builder = new BuildFiltroPredicato();
            builder.addPerText(searchField.getText().trim());
            filtro = builder.getFiltro();
            handler.handle(new SearchBookCommand(libreria, app, filtro, comparatore, reverse));
         });
        ordinamentoCombo.addActionListener((e)->{
            switch ((String) ordinamentoCombo.getSelectedItem()) {
                case "Titolo":
                    comparatore = new ComparatoreTitolo();
                    break;
                case "Autore":
                    comparatore = new ComparatoreAutore();
                    break;
                case "Valutazione":
                    comparatore = new ComparatoreValutazione();
                    break;
                default:
                    comparatore = new ComparatoreTitolo(); // Default per sicurezza
            }
            if(reverse) comparatore = comparatore.reversed();
            handler.handle(new SearchBookCommand(libreria, app, filtro, comparatore, reverse));
        });
        reverseButton.addActionListener((e)->{
            reverse =  !reverse;
            handler.handle(new SearchBookCommand(libreria, app, filtro, comparatore, reverse));});
    }

    private void mostraFinestraFiltri() {
        JDialog filtriDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Filtri");
        filtriDialog.setLayout(new GridLayout(0, 2, 5, 5));

        // Checkbox per attivare/disattivare i filtri
        JCheckBox filtroGenereCheck = new JCheckBox("Filtra per genere");
        JCheckBox filtroStatoCheck = new JCheckBox("Filtra per stato");
        JCheckBox filtroValutazioneCheck = new JCheckBox("Filtra per valutazione");

        // Campi filtro - inizialmente disabilitati
        JComboBox<Libro.Genere> genereBox = new JComboBox<>(Libro.Genere.values());
        genereBox.setEnabled(false);
        JComboBox<Libro.Stato> statoBox = new JComboBox<>(Libro.Stato.values());
        statoBox.setEnabled(false);
        JSpinner valutazioneMin = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        valutazioneMin.setEnabled(false);
        JSpinner valutazioneMax = new JSpinner(new SpinnerNumberModel(5, 1, 5, 1));
        valutazioneMax.setEnabled(false);

        // Abilita/disabilita i campi in base ai checkbox
        filtroGenereCheck.addItemListener(e -> genereBox.setEnabled(filtroGenereCheck.isSelected()));
        filtroStatoCheck.addItemListener(e -> statoBox.setEnabled(filtroStatoCheck.isSelected()));
        filtroValutazioneCheck.addItemListener(e -> {
            valutazioneMin.setEnabled(filtroValutazioneCheck.isSelected());
            valutazioneMax.setEnabled(filtroValutazioneCheck.isSelected());
        });

        // Aggiunta componenti alla finestra
        filtriDialog.add(filtroGenereCheck);
        filtriDialog.add(genereBox);
        filtriDialog.add(filtroStatoCheck);
        filtriDialog.add(statoBox);
        filtriDialog.add(filtroValutazioneCheck);
        filtriDialog.add(new JLabel()); // spazio vuoto

        JPanel valutazionePanel = new JPanel(new FlowLayout());
        valutazionePanel.add(valutazioneMin);
        valutazionePanel.add(new JLabel("a"));
        valutazionePanel.add(valutazioneMax);

        filtriDialog.add(new JLabel("Valutazione:"));
        filtriDialog.add(valutazionePanel);

        // Pulsante applica
        JButton applica = new JButton("Applica Filtro");
        applica.addActionListener(e -> {
            // Imposta i filtri solo se attivi

            BuildFiltroPredicato builder = new BuildFiltroPredicato(filtro); //TODO attenzione al fatto che potrebbero essere impostati piu filtri
            if(filtroGenereCheck.isSelected()){
                builder.addPerGenere((Libro.Genere)genereBox.getSelectedItem());
            }
            if(filtroStatoCheck.isSelected()){
                builder.addPerStato((Libro.Stato) statoBox.getSelectedItem());
            }
            if (filtroValutazioneCheck.isSelected()) {
                builder.addPerValutazione((Integer) valutazioneMin.getValue(), (Integer) valutazioneMax.getValue());
            }
            filtro = builder.getFiltro();
            filtriDialog.dispose();
            handler.handle(new SearchBookCommand(libreria, app, filtro,comparatore,reverse));
        });

        filtriDialog.add(new JLabel()); // spazio vuoto
        filtriDialog.add(applica);

        filtriDialog.pack();
        filtriDialog.setLocationRelativeTo(this);
        filtriDialog.setVisible(true);
    }
}