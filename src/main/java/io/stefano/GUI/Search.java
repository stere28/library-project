package io.stefano.GUI;

import io.stefano.GUI.commandLibrary.CommandHandler;
import io.stefano.GUI.commandLibrary.NaiveCommandHandler;
import io.stefano.GUI.commandLibrary.SearchBookCommand;
import io.stefano.Libreria;
import io.stefano.Libro;
import io.stefano.comparatore.ComparatoreAutore;
import io.stefano.comparatore.ComparatoreLibri;
import io.stefano.comparatore.ComparatoreTitolo;
import io.stefano.comparatore.ComparatoreValutazione;
import io.stefano.filtri.BuildFiltro;
import io.stefano.filtri.BuildFiltroPredicato;
import io.stefano.filtri.Filtro;
import io.stefano.filtri.NessunFiltro;

import javax.swing.*;
import java.awt.*;

public class Search extends JPanel {
    private final CommandHandler handler;
    private final Applicazione app;
    private Filtro filtro;
    private ComparatoreLibri comparatore;
    private boolean reverse = false;
    private final SearchBookCommand searchBookCommand;

    private JTextField searchField;
    private JComboBox<String> ordinamentoCombo;
    private JButton filtriButton;
    private JButton searchButton;
    private JButton reverseButton;

    public Search(Applicazione app, SearchBookCommand searchBookCommand) {
        super();
        this.filtro = new NessunFiltro();
        this.comparatore = new ComparatoreTitolo();
        this.handler = new NaiveCommandHandler();
        this.app = app;
        this.searchBookCommand = searchBookCommand.clone();


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


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
            BuildFiltro builder = new BuildFiltroPredicato();
            String searchText = searchField.getText().trim();
            if(!searchText.equals("")) builder.addPerText(searchText);
            filtro = builder.getFiltro();
            SearchBookCommand cmd = searchBookCommand.clone();
            cmd.setComparatore(comparatore);
            cmd.setReverse(reverse);
            cmd.setFiltro(filtro);
            handler.handle(cmd);
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
                    comparatore = new ComparatoreTitolo();
            }
            if(reverse) comparatore = comparatore.reversed();
            SearchBookCommand cmd = searchBookCommand.clone();
            cmd.setComparatore(comparatore);
            cmd.setReverse(reverse);
            cmd.setFiltro(filtro);
            handler.handle(cmd);
        });
        reverseButton.addActionListener((e)->{
            reverse =  !reverse;
            SearchBookCommand cmd = searchBookCommand.clone();
            cmd.setComparatore(comparatore);
            cmd.setReverse(reverse);
            cmd.setFiltro(filtro);
            handler.handle(cmd);
        });
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
            //TODO implementare command

            // Imposta i filtri solo se attivi

            BuildFiltroPredicato builder = new BuildFiltroPredicato(filtro);
            if(filtroGenereCheck.isSelected()){
                builder.addPerGenere((Libro.Genere)genereBox.getSelectedItem());
            }
            if(filtroStatoCheck.isSelected()){
                builder.addPerStato((Libro.Stato) statoBox.getSelectedItem());
            }
            if (filtroValutazioneCheck.isSelected()) {
                builder.addPerValutazione((Integer) valutazioneMin.getValue(), (Integer) valutazioneMax.getValue());
            }

            filtriDialog.dispose();
            SearchBookCommand cmd = searchBookCommand.clone();
            cmd.setComparatore(comparatore);
            cmd.setReverse(reverse);
            cmd.setFiltro(builder.getFiltro());
            handler.handle(cmd);
        });

        filtriDialog.add(new JLabel()); // spazio vuoto
        filtriDialog.add(applica);

        filtriDialog.pack();
        filtriDialog.setLocationRelativeTo(this);
        filtriDialog.setVisible(true);
    }
}