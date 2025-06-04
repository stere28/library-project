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

        this.libreria = new LibreriaConcreta(LibreriaJSON.INSTANCE);
        this.handler = new HistoryCommandHandler();

        //BARRA INFERIORE
        JButton addBook = new JButton("ADD");
        JButton undoButt = new JButton("<-");
        JButton redoButt = new JButton("->");
        barraInferiore = new JPanel();
        barraInferiore.add(addBook);
        barraInferiore.add(undoButt);
        barraInferiore.add(redoButt);

        addBook.addActionListener(e -> new AddBookDialog(this, handler, libreria));
        undoButt.addActionListener(handler);
        redoButt.addActionListener(handler);


        //RICERCA
        campiDiRicerca = new Search(this,libreria);

        //VISTA LIBRI
        bookListPanel = new JList<>();
        bookListPanel.setCellRenderer(new BookCellRenderer());
        bookListPanel.setFixedCellHeight(70);

        bookListPanel.addListSelectionListener(e -> {
            boolean hasSelection = !bookListPanel.isSelectionEmpty();
            if(hasSelection) {
                Libro selected = bookListPanel.getSelectedValue();
                new BookDetailDialog(this, handler, libreria, selected);
            }
        });


        add(campiDiRicerca, BorderLayout.PAGE_START);
        add(barraInferiore, BorderLayout.PAGE_END);
        add(bookListPanel,BorderLayout.CENTER);

        aggiornaListaLibri();
    }


    class BookCellRenderer extends JPanel implements ListCellRenderer<Libro> {
        private final JLabel titleLabel = new JLabel();
        private final JLabel authorLabel = new JLabel();
        private final JLabel yearLabel = new JLabel();

        public BookCellRenderer() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 14f));
            authorLabel.setFont(authorLabel.getFont().deriveFont(Font.PLAIN, 12f));
            yearLabel.setFont(yearLabel.getFont().deriveFont(Font.ITALIC, 11f));
            yearLabel.setForeground(Color.GRAY);

            add(titleLabel);
            add(authorLabel);
            add(yearLabel);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Libro> list, Libro libro,
                                                      int index, boolean isSelected, boolean cellHasFocus) {

            titleLabel.setText(libro.getTitolo());
            authorLabel.setText("Autori: " + String.join(", ", libro.getAutori()));
            yearLabel.setText("Valutazione: " + libro.getValutazione()+ " | Genere: " + libro.getGenere()
                    + " | Stato: " + libro.getStato() + " | ISBN: " + libro.getIsbn());

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            setOpaque(true);
            return this;
        }
    }

    public void aggiornaListaLibri() {
        DefaultListModel<Libro> model = new DefaultListModel<>();
        for (Libro libro : libreria.getLibri()) {
            model.addElement(libro);
        }
        bookListPanel.setModel(model);
    }
}

