package main.GUI;

import main.GUI.commandLibrary.HistoryCommandHandler;
import main.LibreriaConcreta;
import main.Libro;
import main.Libreria;
import main.implementazioni.LibreriaJSON;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class Applicazione extends JFrame {
    private Libreria libreria;
    private final HistoryCommandHandler handler;
    private JPanel campiDiRicerca;
    private JPanel barraInferiore;
    private JList<Libro> bookListPanel;


    public static void main(String[] args) {
        new Applicazione();
    }

    public Applicazione() {
        super("Libreria");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setVisible(true);

        this.libreria = new LibreriaConcreta();
        this.handler = new HistoryCommandHandler();

        //BARRA INFERIORE
        JButton addBook = new JButton("ADD");
        JButton undoButt = new JButton("<-");
        JButton redoButt = new JButton("->");
        barraInferiore = new JPanel();
        barraInferiore.add(addBook);
        barraInferiore.add(undoButt);
        barraInferiore.add(redoButt);

        undoButt.addActionListener(evt -> handler.undo());
        redoButt.addActionListener(evt -> handler.redo());
        addBook.addActionListener(e -> new AddBookDialog(this, handler, libreria));

        //RICERCA
        campiDiRicerca = new Search(this,libreria);

        //VISTA LIBRI
        bookListPanel = new JList<>();
        bookListPanel.setCellRenderer(new ListCellRenderer<Libro>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends Libro> list, Libro value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = new JLabel(value.getTitolo() + " - " + String.join(", ", value.getAutori()));
                if (isSelected) {
                    label.setBackground(list.getSelectionBackground());
                    label.setForeground(list.getSelectionForeground());
                    label.setOpaque(true);
                }
                return label;
            }
        });


        add(campiDiRicerca, BorderLayout.PAGE_START);
        add(barraInferiore, BorderLayout.PAGE_END);
        add(bookListPanel,BorderLayout.CENTER);


        aggiornaListaLibri();
    }


    public void aggiornaListaLibri() {
        DefaultListModel<Libro> model = new DefaultListModel<>();
        for (Libro libro : libreria.getLibri()) {
            model.addElement(libro);
        }
        bookListPanel.setModel(model);
    }
}

