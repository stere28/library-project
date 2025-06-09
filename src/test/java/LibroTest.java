import io.stefano.Libro;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.HashSet;

@DisplayName("Test per la classe Libro")
public class LibroTest {

    @Nested
    @DisplayName("Test di costruzione e validazione")
    class CostruzioneTest {

        @Test
        @DisplayName("Creazione libro valido con ISBN-10")
        void testCreazioneLibroValidoISBN10() {
            Libro libro = new Libro("1984", Set.of("George Orwell"), "123456789X",
                    Libro.Genere.DISTOPIA, 5, Libro.Stato.LETTO);

            assertEquals("1984", libro.getTitolo());
            assertTrue(libro.getAutori().contains("George Orwell"));
            assertEquals("123456789X", libro.getIsbn());
            assertEquals(Libro.Genere.DISTOPIA, libro.getGenere());
            assertEquals(5, libro.getValutazione());
            assertEquals(Libro.Stato.LETTO, libro.getStato());
        }

        @Test
        @DisplayName("Creazione libro valido con ISBN-13")
        void testCreazioneLibroValidoISBN13() {
            assertDoesNotThrow(() -> {
                new Libro("Il Nome della Rosa", Set.of("Umberto Eco"), "9788845292613",
                        Libro.Genere.ROMANZO, 4, Libro.Stato.DA_LEGGERE);
            });
        }

        @Test
        @DisplayName("Titolo nullo o vuoto")
        void testTitoloInvalido() {
            assertThrows(IllegalArgumentException.class, () ->
                    new Libro(null, Set.of("Autore"), "123456789X",
                            Libro.Genere.ROMANZO, 3, Libro.Stato.LETTO));

            assertThrows(IllegalArgumentException.class, () ->
                    new Libro("", Set.of("Autore"), "123456789X",
                            Libro.Genere.ROMANZO, 3, Libro.Stato.LETTO));
        }

        @Test
        @DisplayName("Autori nulli o vuoti")
        void testAutoriInvalidi() {
            assertThrows(IllegalArgumentException.class, () ->
                    new Libro("Titolo", null, "123456789X",
                            Libro.Genere.ROMANZO, 3, Libro.Stato.LETTO));

            assertThrows(IllegalArgumentException.class, () ->
                    new Libro("Titolo", new HashSet<>(), "123456789X",
                            Libro.Genere.ROMANZO, 3, Libro.Stato.LETTO));
        }

        @Test
        @DisplayName("ISBN invalidi")
        void testISBNInvalido() {
            // ISBN troppo corto
            assertThrows(IllegalArgumentException.class, () ->
                    new Libro("Titolo", Set.of("Autore"), "123",
                            Libro.Genere.ROMANZO, 3, Libro.Stato.LETTO));

            // ISBN con caratteri non validi
            assertThrows(IllegalArgumentException.class, () ->
                    new Libro("Titolo", Set.of("Autore"), "ABCDEFGHIJ",
                            Libro.Genere.ROMANZO, 3, Libro.Stato.LETTO));

            // ISBN-10 con checksum sbagliato
            assertThrows(IllegalArgumentException.class, () ->
                    new Libro("Titolo", Set.of("Autore"), "1234567899",
                            Libro.Genere.ROMANZO, 3, Libro.Stato.LETTO));
        }

        @Test
        @DisplayName("Valutazione invalida")
        void testValutazioneInvalida() {
            assertThrows(IllegalArgumentException.class, () ->
                    new Libro("Titolo", Set.of("Autore"), "123456789X",
                            Libro.Genere.ROMANZO, 0, Libro.Stato.LETTO));

            assertThrows(IllegalArgumentException.class, () ->
                    new Libro("Titolo", Set.of("Autore"), "123456789X",
                            Libro.Genere.ROMANZO, 6, Libro.Stato.LETTO));
        }
    }

    @Nested
    @DisplayName("Test di modifica stato")
    class ModificaStatoTest {
        private Libro libro;

        @BeforeEach
        void setUp() {
            libro = new Libro("Test", Set.of("Autore Test"), "123456789X",
                    Libro.Genere.ROMANZO, 3, Libro.Stato.DA_LEGGERE);
        }

        @Test
        @DisplayName("Modifica stato direttamente")
        void testSetStato() {
            libro.setStato(Libro.Stato.IN_CORSO);
            assertEquals(Libro.Stato.IN_CORSO, libro.getStato());
        }

        @Test
        @DisplayName("Segna come letto")
        void testSegnaComeLetto() {
            libro.segnaComeLetto();
            assertEquals(Libro.Stato.LETTO, libro.getStato());
        }

        @Test
        @DisplayName("Segna come in corso")
        void testSegnaComeInCorso() {
            libro.segnaComeInCorso();
            assertEquals(Libro.Stato.IN_CORSO, libro.getStato());
        }

        @Test
        @DisplayName("Segna come da leggere")
        void testSegnaComeDaLeggere() {
            libro.setStato(Libro.Stato.LETTO);
            libro.segnaComeDaLeggere();
            assertEquals(Libro.Stato.DA_LEGGERE, libro.getStato());
        }
    }
}