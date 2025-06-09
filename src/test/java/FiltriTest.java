import io.stefano.Libro;
import io.stefano.filtri.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

@DisplayName("Test per i Filtri")
public class FiltriTest {

    @Nested
    @DisplayName("Test FiltroGenere")
    class FiltroGenereTest {

        @Test
        @DisplayName("Filtra correttamente per genere")
        void testFiltroGenere() {
            FiltroGenere filtro = new FiltroGenere(Libro.Genere.FANTASY);

            Libro libroFantasy = new Libro("Fantasy Book", Set.of("Autore"), "123456789X",
                    Libro.Genere.FANTASY, 4, Libro.Stato.LETTO);
            Libro libroRomanzo = new Libro("Romanzo Book", Set.of("Autore"), "123456789X",
                    Libro.Genere.ROMANZO, 3, Libro.Stato.DA_LEGGERE);

            assertTrue(filtro.test(libroFantasy));
            assertFalse(filtro.test(libroRomanzo));
        }
    }

    @Nested
    @DisplayName("Test FiltroStato")
    class FiltroStatoTest {

        @Test
        @DisplayName("Filtra correttamente per stato")
        void testFiltroStato() {
            FiltroStato filtro = new FiltroStato(Libro.Stato.LETTO);

            Libro libroLetto = new Libro("Letto", Set.of("Autore"), "123456789X",
                    Libro.Genere.ROMANZO, 4, Libro.Stato.LETTO);
            Libro libroDaLeggere = new Libro("Da Leggere", Set.of("Autore"), "123456789X",
                    Libro.Genere.ROMANZO, 3, Libro.Stato.DA_LEGGERE);

            assertTrue(filtro.test(libroLetto));
            assertFalse(filtro.test(libroDaLeggere));
        }
    }

    @Nested
    @DisplayName("Test FiltroValutazione")
    class FiltroValutazioneTest {

        @Test
        @DisplayName("Filtra correttamente per valutazione")
        void testFiltroValutazione() {
            FiltroValutazione filtro = new FiltroValutazione(3, 4);

            Libro libro3 = new Libro("Val 3", Set.of("Autore"), "123456789X",
                    Libro.Genere.ROMANZO, 3, Libro.Stato.LETTO);
            Libro libro4 = new Libro("Val 4", Set.of("Autore"), "123456789X",
                    Libro.Genere.ROMANZO, 4, Libro.Stato.LETTO);
            Libro libro5 = new Libro("Val 5", Set.of("Autore"), "123456789X",
                    Libro.Genere.ROMANZO, 5, Libro.Stato.LETTO);
            Libro libro2 = new Libro("Val 2", Set.of("Autore"), "123456789X",
                    Libro.Genere.ROMANZO, 2, Libro.Stato.LETTO);

            assertTrue(filtro.test(libro3));
            assertTrue(filtro.test(libro4));
            assertFalse(filtro.test(libro5));
            assertFalse(filtro.test(libro2));
        }
    }

    @Nested
    @DisplayName("Test FiltroTitoloAutore")
    class FiltroTitoloAutoreTest {

        @Test
        @DisplayName("Filtra per titolo")
        void testFiltroPerTitolo() {
            FiltroTitoloAutore filtro = new FiltroTitoloAutore("harry");

            Libro libro1 = new Libro("Harry Potter", Set.of("J.K. Rowling"), "123456789X",
                    Libro.Genere.FANTASY, 5, Libro.Stato.LETTO);
            Libro libro2 = new Libro("Il Signore degli Anelli", Set.of("Tolkien"), "123456789X",
                    Libro.Genere.FANTASY, 5, Libro.Stato.LETTO);

            assertTrue(filtro.test(libro1));
            assertFalse(filtro.test(libro2));
        }

        @Test
        @DisplayName("Filtra per autore")
        void testFiltroPerAutore() {
            FiltroTitoloAutore filtro = new FiltroTitoloAutore("rowling");

            Libro libro1 = new Libro("Fantasy Book", Set.of("J.K. Rowling"), "123456789X",
                    Libro.Genere.FANTASY, 5, Libro.Stato.LETTO);
            Libro libro2 = new Libro("Another Book", Set.of("Altro Autore"), "123456789X",
                    Libro.Genere.ROMANZO, 3, Libro.Stato.LETTO);

            assertTrue(filtro.test(libro1));
            assertFalse(filtro.test(libro2));
        }

        @Test
        @DisplayName("Ricerca case insensitive")
        void testRicercaCaseInsensitive() {
            FiltroTitoloAutore filtro = new FiltroTitoloAutore("HARRY");

            Libro libro = new Libro("harry potter", Set.of("autore"), "123456789X",
                    Libro.Genere.FANTASY, 5, Libro.Stato.LETTO);

            assertTrue(filtro.test(libro));
        }
    }

    @Nested
    @DisplayName("Test NessunFiltro")
    class NessunFiltroTest {

        @Test
        @DisplayName("NessunFiltro accetta tutti i libri")
        void testNessunFiltro() {
            NessunFiltro filtro = new NessunFiltro();

            Libro libro = new Libro("Qualsiasi", Set.of("Autore"), "123456789X",
                    Libro.Genere.ALTRO, 1, Libro.Stato.DA_LEGGERE);

            assertTrue(filtro.test(libro));
        }
    }

    @Nested
    @DisplayName("Test BuildFiltroPredicato")
    class BuildFiltroPredicatoTest {

        @Test
        @DisplayName("Builder crea filtro combinato")
        void testBuilderFiltroCombinato() {
            BuildFiltroPredicato builder = new BuildFiltroPredicato();
            builder.addPerGenere(Libro.Genere.FANTASY);
            builder.addPerValutazione(4, 5);

            Filtro filtro = builder.getFiltro();

            Libro libroOk = new Libro("Fantasy", Set.of("Autore"), "123456789X",
                    Libro.Genere.FANTASY, 5, Libro.Stato.LETTO);
            Libro libroGenereWrong = new Libro("Romanzo", Set.of("Autore"), "123456789X",
                    Libro.Genere.ROMANZO, 5, Libro.Stato.LETTO);
            Libro libroValutazioneWrong = new Libro("Fantasy", Set.of("Autore"), "123456789X",
                    Libro.Genere.FANTASY, 2, Libro.Stato.LETTO);

            assertTrue(filtro.test(libroOk));
            assertFalse(filtro.test(libroGenereWrong));
            assertFalse(filtro.test(libroValutazioneWrong));
        }

        @Test
        @DisplayName("Builder con filtro esistente")
        void testBuilderConFiltroEsistente() {
            Filtro filtroBase = new FiltroGenere(Libro.Genere.FANTASY);
            BuildFiltroPredicato builder = new BuildFiltroPredicato(filtroBase);
            builder.addPerValutazione(4, 5);

            Filtro filtroFinale = builder.getFiltro();

            Libro libro = new Libro("Fantasy", Set.of("Autore"), "123456789X",
                    Libro.Genere.FANTASY, 5, Libro.Stato.LETTO);

            assertTrue(filtroFinale.test(libro));
        }
    }
}