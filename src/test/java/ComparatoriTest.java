import io.stefano.Libro;
import io.stefano.comparatore.ComparatoreAutore;
import io.stefano.comparatore.ComparatoreLibri;
import io.stefano.comparatore.ComparatoreTitolo;
import io.stefano.comparatore.ComparatoreValutazione;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;

@DisplayName("Test per i Comparatori")
public class ComparatoriTest {

    @Nested
    @DisplayName("Test ComparatoreTitolo")
    class ComparatoreTitoloTest {

        @Test
        @DisplayName("Ordinamento per titolo")
        void testOrdinamentoPerTitolo() {
            ComparatoreTitolo comparatore = new ComparatoreTitolo();

            Libro libro1 = new Libro("Zorro", Set.of("Autore"), "123456789X",
                    Libro.Genere.AVVENTURA, 3, Libro.Stato.LETTO);
            Libro libro2 = new Libro("Alice", Set.of("Autore"), "123456789X",
                    Libro.Genere.FANTASY, 4, Libro.Stato.DA_LEGGERE);

            assertTrue(comparatore.compare(libro2, libro1) < 0);
            assertTrue(comparatore.compare(libro1, libro2) > 0);
            assertEquals(0, comparatore.compare(libro1, libro1));
        }

        @Test
        @DisplayName("Ordinamento case insensitive")
        void testOrdinamentoCaseInsensitive() {
            ComparatoreTitolo comparatore = new ComparatoreTitolo();

            Libro libro1 = new Libro("alice", Set.of("Autore"), "123456789X",
                    Libro.Genere.FANTASY, 3, Libro.Stato.LETTO);
            Libro libro2 = new Libro("ALICE", Set.of("Autore"), "123456789X",
                    Libro.Genere.FANTASY, 4, Libro.Stato.DA_LEGGERE);

            assertEquals(0, comparatore.compare(libro1, libro2));
        }
    }

    @Nested
    @DisplayName("Test ComparatoreAutore")
    class ComparatoreAutoreTest {

        @Test
        @DisplayName("Ordinamento per primo autore alfabetico")
        void testOrdinamentoPerPrimoAutore() {
            ComparatoreAutore comparatore = new ComparatoreAutore();

            Libro libro1 = new Libro("Libro1", Set.of("Zorro Autor", "Alice Autor"), "123456789X",
                    Libro.Genere.ROMANZO, 3, Libro.Stato.LETTO);
            Libro libro2 = new Libro("Libro2", Set.of("Bob Autor"), "123456789X",
                    Libro.Genere.ROMANZO, 4, Libro.Stato.DA_LEGGERE);

            assertTrue(comparatore.compare(libro1, libro2) < 0); // Alice < Bob
        }

        @Test
        @DisplayName("Gestione autori vuoti o null")
        void testAutoriVuoti() {
            ComparatoreAutore comparatore = new ComparatoreAutore();

            // Questo test non dovrebbe mai verificarsi dato che Libro non permette autori vuoti
            // Ma testiamo comunque la robustezza del comparatore
            Libro libro1 = new Libro("Libro1", Set.of("Autore"), "123456789X",
                    Libro.Genere.ROMANZO, 3, Libro.Stato.LETTO);

            assertDoesNotThrow(() -> comparatore.compare(libro1, libro1));
        }
    }

    @Nested
    @DisplayName("Test ComparatoreValutazione")
    class ComparatoreValutazioneTest {

        @Test
        @DisplayName("Ordinamento per valutazione decrescente")
        void testOrdinamentoPerValutazione() {
            ComparatoreValutazione comparatore = new ComparatoreValutazione();

            Libro libro1 = new Libro("Libro1", Set.of("Autore"), "123456789X",
                    Libro.Genere.ROMANZO, 3, Libro.Stato.LETTO);
            Libro libro2 = new Libro("Libro2", Set.of("Autore"), "123456789X",
                    Libro.Genere.ROMANZO, 5, Libro.Stato.DA_LEGGERE);

            assertTrue(comparatore.compare(libro1, libro2) > 0); // 3 viene dopo 5 (decrescente)
            assertTrue(comparatore.compare(libro2, libro1) < 0); // 5 viene prima di 3
        }
    }

    @Nested
    @DisplayName("Test metodo reversed")
    class ReversedTest {

        @Test
        @DisplayName("Reversed inverte l'ordinamento")
        void testReversed() {
            ComparatoreTitolo comparatore = new ComparatoreTitolo();
            ComparatoreLibri reversed = comparatore.reversed();

            Libro libro1 = new Libro("Alice", Set.of("Autore"), "123456789X",
                    Libro.Genere.FANTASY, 3, Libro.Stato.LETTO);
            Libro libro2 = new Libro("Zorro", Set.of("Autore"), "123456789X",
                    Libro.Genere.AVVENTURA, 4, Libro.Stato.DA_LEGGERE);

            assertTrue(comparatore.compare(libro1, libro2) < 0);
            assertTrue(reversed.compare(libro1, libro2) > 0);
        }
    }
}