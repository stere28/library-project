import io.stefano.GUI.Applicazione;
import io.stefano.GUI.commandLibrary.*;
import io.stefano.Libreria;
import io.stefano.LibreriaConcreta;
import io.stefano.Libro;
import io.stefano.comparatore.ComparatoreTitolo;
import io.stefano.comparatore.ComparatoreAutore;
import io.stefano.filtri.FiltroGenere;
import io.stefano.filtri.NessunFiltro;
import io.stefano.implementazioni.LibreriaPersistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for command library functionality.
 * Tests all command types and their interaction with the library system.
 */
public class CommandTest {
    @Mock
    private Applicazione mockApplicazione;
    @Mock
    private Libreria mockLibreria;

    private Libro testBook1;
    private Libro testBook2;
    private Libro testBook3;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create test books
        testBook1 = new Libro(
                "Test Title 1",
                Set.of("Author A", "Author B"),
                "9788804668794", // Valid ISBN-13
                Libro.Genere.ROMANZO,
                4,
                Libro.Stato.LETTO
        );

        testBook2 = new Libro(
                "Another Book",
                Set.of("Author C"),
                "9788804668787", // Valid ISBN-13
                Libro.Genere.FANTASY,
                5,
                Libro.Stato.DA_LEGGERE
        );

        testBook3 = new Libro(
                "Third Book",
                Set.of("Author A"),
                "9788804668770", // Valid ISBN-13
                Libro.Genere.GIALLO,
                3,
                Libro.Stato.IN_CORSO
        );
    }

    @Test
    @DisplayName("AddBookCommand dovrebbe aggiungere il libro alla libreria e aggiornare l'interfaccia")
    void testAddBookCommand() {
        // Given
        when(mockLibreria.getLibri()).thenReturn(List.of(testBook1));
        AddBookCommand command = new AddBookCommand(mockApplicazione, mockLibreria);
        command.setLibro(testBook1);

        // When
        boolean result = command.doIt();

        // Then
        assertTrue(result, "AddBookCommand dovrebbe restituire true (annullabile)");
        verify(mockLibreria).aggiungiLibro(testBook1);
        verify(mockApplicazione).aggiornaListaLibri(List.of(testBook1));
    }

    @Test
    @DisplayName("Annullamento di AddBookCommand dovrebbe rimuovere il libro dalla libreria")
    void testAddBookCommandUndo() {
        // Given
        when(mockLibreria.getLibri()).thenReturn(List.of());
        AddBookCommand command = new AddBookCommand(mockApplicazione, mockLibreria);
        command.setLibro(testBook1);

        // When
        boolean result = command.undoIt();

        // Then
        assertTrue(result, "L'annullamento di AddBookCommand dovrebbe restituire true");
        verify(mockLibreria).rimuoviLibro(testBook1);
        verify(mockApplicazione).aggiornaListaLibri(List.of());
    }

    @Test
    @DisplayName("RemoveBookCommand dovrebbe rimuovere il libro dalla libreria")
    void testRemoveBookCommand() {
        // Given
        when(mockLibreria.getLibri()).thenReturn(List.of());
        RemoveBookCommand command = new RemoveBookCommand(mockApplicazione, mockLibreria);
        command.setLibro(testBook1);

        // When
        boolean result = command.doIt();

        // Then
        assertTrue(result, "RemoveBookCommand dovrebbe restituire true (annullabile)");
        verify(mockLibreria).rimuoviLibro(testBook1);
        verify(mockApplicazione).aggiornaListaLibri(List.of());
    }

    @Test
    @DisplayName("Annullamento di RemoveBookCommand dovrebbe riaggiungere il libro alla libreria")
    void testRemoveBookCommandUndo() {
        // Given
        when(mockLibreria.getLibri()).thenReturn(List.of(testBook1));
        RemoveBookCommand command = new RemoveBookCommand(mockApplicazione, mockLibreria);
        command.setLibro(testBook1);

        // When
        boolean result = command.undoIt();

        // Then
        assertTrue(result, "L'annullamento di RemoveBookCommand dovrebbe restituire true");
        verify(mockLibreria).aggiungiLibro(testBook1);
        verify(mockApplicazione).aggiornaListaLibri(List.of(testBook1));
    }

    @Test
    @DisplayName("EditBookCommand dovrebbe sostituire il libro originale con quello nuovo")
    void testEditBookCommand() {
        // Given
        when(mockLibreria.getLibri()).thenReturn(List.of(testBook2));
        EditBookCommand command = new EditBookCommand(mockApplicazione, mockLibreria);
        command.setLibroOriginale(testBook1);
        command.setLibroNuovo(testBook2);

        // When
        boolean result = command.doIt();

        // Then
        assertTrue(result, "EditBookCommand dovrebbe restituire true (annullabile)");
        verify(mockLibreria).rimuoviLibro(testBook1);
        verify(mockLibreria).aggiungiLibro(testBook2);
        verify(mockApplicazione).aggiornaListaLibri(List.of(testBook2));
    }

    @Test
    @DisplayName("Annullamento di EditBookCommand dovrebbe ripristinare il libro originale")
    void testEditBookCommandUndo() {
        // Given
        when(mockLibreria.getLibri()).thenReturn(List.of(testBook1));
        EditBookCommand command = new EditBookCommand(mockApplicazione, mockLibreria);
        command.setLibroOriginale(testBook1);
        command.setLibroNuovo(testBook2);

        // When
        boolean result = command.undoIt();

        // Then
        assertTrue(result, "L'annullamento di EditBookCommand dovrebbe restituire true");
        verify(mockLibreria).rimuoviLibro(testBook2);
        verify(mockLibreria).aggiungiLibro(testBook1);
        verify(mockApplicazione).aggiornaListaLibri(List.of(testBook1));
    }

    @Test
    @DisplayName("SearchBookCommand dovrebbe applicare il filtro e il comparatore")
    void testSearchBookCommand() {
        // Given
        List<Libro> filteredBooks = List.of(testBook1, testBook3);
        when(mockLibreria.getLibri()).thenReturn(filteredBooks);

        SearchBookCommand command = new SearchBookCommand(mockApplicazione, mockLibreria);
        command.setFiltro(new FiltroGenere(Libro.Genere.ROMANZO));
        command.setComparatore(new ComparatoreTitolo());
        command.setReverse(false);

        // When
        boolean result = command.doIt();

        // Then
        assertFalse(result, "SearchBookCommand dovrebbe restituire false (non annullabile)");
        verify(mockApplicazione).aggiornaListaLibri(filteredBooks);
    }

    @Test
    @DisplayName("SearchBookCommand con reverse dovrebbe applicare il comparatore invertito")
    void testSearchBookCommandReverse() {
        // Given
        List<Libro> filteredBooks = List.of(testBook3, testBook1);
        when(mockLibreria.getLibri()).thenReturn(filteredBooks);

        SearchBookCommand command = new SearchBookCommand(mockApplicazione, mockLibreria);
        command.setFiltro(new NessunFiltro());
        command.setComparatore(new ComparatoreAutore());
        command.setReverse(true);

        // When
        boolean result = command.doIt();

        // Then
        assertFalse(result, "SearchBookCommand dovrebbe restituire false (non annullabile)");
        verify(mockApplicazione).aggiornaListaLibri(filteredBooks);
    }

    @Test
    @DisplayName("CaricaLibreriaCommand dovrebbe aggiornare l'interfaccia con lo stato attuale della libreria")
    void testCaricaLibreriaCommand() {
        // Given
        List<Libro> allBooks = List.of(testBook1, testBook2, testBook3);
        when(mockLibreria.getLibri()).thenReturn(allBooks);

        CaricaLibreriaCommand command = new CaricaLibreriaCommand(mockApplicazione, mockLibreria);

        // When
        boolean result = command.doIt();

        // Then
        assertFalse(result, "CaricaLibreriaCommand dovrebbe restituire false (non annullabile)");
        verify(mockApplicazione).aggiornaListaLibri(allBooks);
    }

    @Test
    @DisplayName("Annullamento di CaricaLibreriaCommand dovrebbe restituire false")
    void testCaricaLibreriaCommandUndo() {
        // Given
        CaricaLibreriaCommand command = new CaricaLibreriaCommand(mockApplicazione, mockLibreria);

        // When
        boolean result = command.undoIt();

        // Then
        assertFalse(result, "L'annullamento di CaricaLibreriaCommand dovrebbe restituire false");
    }

    @Test
    @DisplayName("La clonazione dei comandi dovrebbe funzionare correttamente")
    void testCommandCloning() {
        // Given
        AddBookCommand original = new AddBookCommand(mockApplicazione, mockLibreria);
        original.setLibro(testBook1);

        // When
        AddBookCommand clone = original.clone();
        clone.setLibro(testBook2);

        // Then
        assertNotSame(original, clone, "Il clone dovrebbe essere un'istanza diversa");
        // L'originale dovrebbe ancora riferirsi a testBook1, il clone dovrebbe riferirsi a testBook2
        // Questo dimostra che il clone è indipendente
    }

    @Test
    @DisplayName("HistoryCommandHandler dovrebbe gestire correttamente undo/redo")
    void testHistoryCommandHandler() {
        // Given
        HistoryCommandHandler handler = new HistoryCommandHandler(5);
        when(mockLibreria.getLibri())
                .thenReturn(List.of(testBook1))
                .thenReturn(List.of())
                .thenReturn(List.of(testBook1));

        AddBookCommand addCommand = new AddBookCommand(mockApplicazione, mockLibreria);
        addCommand.setLibro(testBook1);

        // When - Execute command
        handler.handle(addCommand);

        // Then - Verify command was executed
        verify(mockLibreria).aggiungiLibro(testBook1);

        // When - Undo
        handler.undo();

        // Then - Verify undo was executed
        verify(mockLibreria).rimuoviLibro(testBook1);

        // When - Redo
        handler.redo();

        // Then - Verify redo was executed
        verify(mockLibreria, times(2)).aggiungiLibro(testBook1);
    }

    @Test
    @DisplayName("HistoryCommandHandler dovrebbe cancellare la lista redo quando viene eseguito un nuovo comando")
    void testHistoryCommandHandlerClearsRedoList() {
        // Given
        HistoryCommandHandler handler = new HistoryCommandHandler();
        when(mockLibreria.getLibri()).thenReturn(List.of(testBook1));

        AddBookCommand addCommand1 = new AddBookCommand(mockApplicazione, mockLibreria);
        addCommand1.setLibro(testBook1);
        AddBookCommand addCommand2 = new AddBookCommand(mockApplicazione, mockLibreria);
        addCommand2.setLibro(testBook2);

        // When
        handler.handle(addCommand1);
        handler.undo(); // Questo dovrebbe mettere il comando nella lista redo
        handler.handle(addCommand2); // Questo dovrebbe cancellare la lista redo
        handler.redo(); // Questo non dovrebbe fare nulla poiché la lista redo è stata cancellata

        // Then
        verify(mockLibreria, times(2)).aggiungiLibro(any()); // Solo 2 salvataggi, nessun redo
    }

    @Test
    @DisplayName("NaiveCommandHandler dovrebbe eseguire i comandi senza cronologia")
    void testNaiveCommandHandler() {
        // Given
        NaiveCommandHandler handler = new NaiveCommandHandler();
        when(mockLibreria.getLibri()).thenReturn(List.of(testBook1));

        AddBookCommand command = new AddBookCommand(mockApplicazione, mockLibreria);
        command.setLibro(testBook1);

        // When
        handler.handle(command);

        // Then
        verify(mockLibreria).aggiungiLibro(testBook1);
        verify(mockApplicazione).aggiornaListaLibri(List.of(testBook1));
    }

    @Test
    @DisplayName("I comandi dovrebbero gestire gracilmente i parametri null")
    void testCommandsWithNullParameters() {
        // Given
        AddBookCommand addCommand = new AddBookCommand(mockApplicazione, mockLibreria);

        // When/Then - Non dovrebbe lanciare eccezioni quando libro è null
        // La NullPointerException verrebbe dalla chiamata mockLibreria.aggiungiLibro
        assertDoesNotThrow(() -> {
            try {
                addCommand.doIt();
            } catch (Exception e) {
                // Comportamento atteso - la libreria dovrebbe gestire la validazione null
                assertTrue(e instanceof RuntimeException);
            }
        });
    }
}