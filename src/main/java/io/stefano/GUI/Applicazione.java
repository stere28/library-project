package io.stefano.GUI;

import io.stefano.GUI.commandLibrary.CaricaLibreriaCommand;
import io.stefano.GUI.commandLibrary.HistoryCommandHandler;
import io.stefano.GUI.commandLibrary.NaiveCommandHandler;
import io.stefano.InizializzaAmbienteDiLavoro;
import io.stefano.LibreriaConcreta;
import io.stefano.Libro;
import io.stefano.Libreria;
import io.stefano.implementazioni.LibreriaJSON;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Applicazione extends JFrame {
    private final HistoryCommandHandler historyCommandHandler;
    private final NaiveCommandHandler naiveCommandHandler;
    private final JPanel campiDiRicerca;
    private final BarraInferiore barraInferiore;
    private final JList<Libro> bookListPanel;
    private DefaultListModel<Libro> model = new DefaultListModel<>();
    private final InizializzaAmbienteDiLavoro ambienteDiLavoro ;
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

        naiveCommandHandler = new NaiveCommandHandler();
        ambienteDiLavoro = new InizializzaAmbienteDiLavoro(this);
        this.historyCommandHandler = new HistoryCommandHandler();

        //BARRA INFERIORE
        barraInferiore = new BarraInferiore();

        //RICERCA
        campiDiRicerca = new Search(this,ambienteDiLavoro.getSearchBookCommand());

        //VISTA LIBRI
        bookListPanel = new JList<>();
        bookListPanel.setCellRenderer(new BookCellRenderer());
        bookListPanel.setFixedCellHeight(70);

        naiveCommandHandler.handle(ambienteDiLavoro.getCaricaLibreriaCommand());

        bookListPanel.setModel(model);

        bookListPanel.addListSelectionListener(e -> {
            boolean hasSelection = !bookListPanel.isSelectionEmpty();
            if(hasSelection) {
                Libro selected = bookListPanel.getSelectedValue();
                new BookDetailDialog(this, historyCommandHandler, selected,
                        ambienteDiLavoro.getEditBookCommand(),ambienteDiLavoro.getRemoveBookCommand());
            }
        });


        add(campiDiRicerca, BorderLayout.PAGE_START);
        add(barraInferiore, BorderLayout.PAGE_END);
        JScrollPane scrollPane = new JScrollPane(bookListPanel);
        add(scrollPane, BorderLayout.CENTER);
        revalidate();
    }
    private class BarraInferiore extends JPanel{
        public BarraInferiore(){

            JButton addBook = new JButton("ADD");
            JButton undoButt = new JButton("<-");
            JButton redoButt = new JButton("->");
            JButton reload = new JButton("Reload");

            add(addBook);
            add(undoButt);
            add(redoButt);
            add(reload);

            addBook.addActionListener(e -> new AddBookDialog(Applicazione.this, historyCommandHandler,
                    ambienteDiLavoro.getAddBookCommand()));
            undoButt.addActionListener(historyCommandHandler);
            redoButt.addActionListener(historyCommandHandler);
            reload.addActionListener(e -> {naiveCommandHandler.handle(ambienteDiLavoro.getCaricaLibreriaCommand());});
        }
    }
    private class BookCellRenderer extends JPanel implements ListCellRenderer<Libro> {
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

    public void aggiornaListaLibri(List<Libro> libri) {
        model.clear(); // Pulisce solo gli elementi
        for (Libro libro : libri) {
            model.addElement(libro);
        }
    }
}

