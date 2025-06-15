import io.stefano.Libreria;
import io.stefano.LibreriaConcreta;
import io.stefano.Libro;
import io.stefano.comparatore.ComparatoreAutore;
import io.stefano.comparatore.ComparatoreLibri;
import io.stefano.comparatore.ComparatoreTitolo;
import io.stefano.comparatore.ComparatoreValutazione;
import io.stefano.filtri.BuildFiltroPredicato;
import io.stefano.filtri.Filtro;
import io.stefano.filtri.NessunFiltro;
import io.stefano.implementazioni.LibreriaJSON;
import io.stefano.implementazioni.LibreriaPersistente;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Libreria")
public abstract class LibreriaTest {
    private Libreria libreria;
    public abstract LibreriaPersistente createLibreriaPersistente();
    public abstract Libreria createLibreria(LibreriaPersistente libreriaPersistente);

    LibreriaTest(){
        libreria = createLibreria(createLibreriaPersistente());
    }

    private void pulisciLibreria() {
        List<Libro> libriEsistenti = libreria.getLibri();
        for (Libro libro : libriEsistenti) {
            libreria.rimuoviLibro(libro);
        }
    }

    @Nested
    @DisplayName("Test Base della Libreria")
    class TestBase {
        private Libro libro1;
        private Libro libro2;
        private Libro libro3;

        @BeforeEach
        void setUp() {
            // Pulisce la libreria prima di ogni test
            pulisciLibreria();

            // Libri di test
            libro1 = new Libro("1984", Set.of("George Orwell"), "0451524934",
                    Libro.Genere.DISTOPIA, 5, Libro.Stato.LETTO);
            libro2 = new Libro("Animal Farm", Set.of("George Orwell"), "0451526341",
                    Libro.Genere.CLASSICO, 4, Libro.Stato.DA_LEGGERE);
            libro3 = new Libro("Brave New World", Set.of("Aldous Huxley"), "0060850523",
                    Libro.Genere.DISTOPIA, 4, Libro.Stato.IN_CORSO);
        }

        @AfterEach
        void tearDown() {
            // Pulisce la libreria dopo ogni test per evitare interferenze
            pulisciLibreria();
        }

        @Test
        @DisplayName("Test aggiunta e rimozione libri")
        void testAggiungiRimuoviLibri() {
            assertTrue(libreria.isEmpty(), "La libreria dovrebbe essere vuota inizialmente");
            assertEquals(0, libreria.size(), "La size dovrebbe essere 0 inizialmente");

            // Test aggiunta
            libreria.aggiungiLibro(libro1);
            assertFalse(libreria.isEmpty(), "La libreria non dovrebbe essere vuota dopo l'aggiunta");
            assertEquals(1, libreria.size(), "La size dovrebbe essere 1 dopo l'aggiunta");
            assertTrue(libreria.contiene(libro1), "La libreria dovrebbe contenere il libro aggiunto");

            // Test aggiunta secondo libro
            libreria.aggiungiLibro(libro2);
            assertEquals(2, libreria.size(), "La size dovrebbe essere 2 dopo la seconda aggiunta");
            assertTrue(libreria.contiene(libro2), "La libreria dovrebbe contenere il secondo libro");

            // Test rimozione
            libreria.rimuoviLibro(libro1);
            assertEquals(1, libreria.size(), "La size dovrebbe essere 1 dopo la rimozione");
            assertFalse(libreria.contiene(libro1), "La libreria non dovrebbe più contenere il libro rimosso");
            assertTrue(libreria.contiene(libro2), "La libreria dovrebbe ancora contenere il secondo libro");

            // Test rimozione ultimo libro
            libreria.rimuoviLibro(libro2);
            assertTrue(libreria.isEmpty(), "La libreria dovrebbe essere vuota dopo aver rimosso tutti i libri");
            assertEquals(0, libreria.size(), "La size dovrebbe essere 0 dopo aver rimosso tutti i libri");
        }

        @Test
        @DisplayName("Test iteratore")
        void testIteratore() {
            libreria.aggiungiLibro(libro1);
            libreria.aggiungiLibro(libro2);

            int count = 0;
            for (Libro libro : libreria) {
                count++;
                assertNotNull(libro, "I libri nell'iteratore non dovrebbero essere null");
            }
            assertEquals(2, count, "L'iteratore dovrebbe restituire 2 libri");

            // Test iteratore esplicito
            Iterator<Libro> iterator = libreria.iterator();
            assertTrue(iterator.hasNext(), "L'iteratore dovrebbe avere elementi");
            assertNotNull(iterator.next(), "Il primo elemento non dovrebbe essere null");
            assertTrue(iterator.hasNext(), "L'iteratore dovrebbe avere ancora elementi");
            assertNotNull(iterator.next(), "Il secondo elemento non dovrebbe essere null");
            assertFalse(iterator.hasNext(), "L'iteratore non dovrebbe avere più elementi");
        }

        @Test
        @DisplayName("Test eccezioni per parametri null")
        void testEccezioniNull() {
            assertThrows(NullPointerException.class, () -> libreria.aggiungiLibro(null),
                    "Aggiungere un libro null dovrebbe lanciare NullPointerException");

            assertThrows(NullPointerException.class, () -> libreria.rimuoviLibro(null),
                    "Rimuovere un libro null dovrebbe lanciare NullPointerException");
        }

        @Test
        @DisplayName("Test eccezioni per libri duplicati e inesistenti")
        void testEccezioniLibri() {
            libreria.aggiungiLibro(libro1);

            // Test libro duplicato
            assertThrows(IllegalArgumentException.class, () -> libreria.aggiungiLibro(libro1),
                    "Aggiungere un libro già presente dovrebbe lanciare IllegalArgumentException");

            // Test rimozione libro inesistente
            assertThrows(IllegalArgumentException.class, () -> libreria.rimuoviLibro(libro2),
                    "Rimuovere un libro non presente dovrebbe lanciare IllegalArgumentException");
        }
    }

    @Nested
    @DisplayName("Test Ordinamento")
    class TestOrdinamento {
        private Libreria libreria;
        private Libro libroA, libroB, libroC;

        @BeforeEach
        void setUp() {
            libreria = new LibreriaConcreta(LibreriaJSON.INSTANCE);
            pulisciLibreria();

            libroA = new Libro("Animal Farm", Set.of("George Orwell"), "0451526341",
                    Libro.Genere.CLASSICO, 4, Libro.Stato.DA_LEGGERE);
            libroB = new Libro("1984", Set.of("George Orwell"), "0451524934",
                    Libro.Genere.DISTOPIA, 5, Libro.Stato.LETTO);
            libroC = new Libro("Brave New World", Set.of("Aldous Huxley"), "0060850523",
                    Libro.Genere.DISTOPIA, 3, Libro.Stato.IN_CORSO);

            libreria.aggiungiLibro(libroA);
            libreria.aggiungiLibro(libroB);
            libreria.aggiungiLibro(libroC);
        }

        @AfterEach
        void tearDown() {
            pulisciLibreria();
        }

        @Test
        @DisplayName("Test ordinamento per titolo (default)")
        void testOrdinamentoTitolo() {
            List<Libro> libri = libreria.getLibri();
            assertEquals(3, libri.size());
            assertEquals("1984", libri.get(0).getTitolo());
            assertEquals("Animal Farm", libri.get(1).getTitolo());
            assertEquals("Brave New World", libri.get(2).getTitolo());
        }

        @Test
        @DisplayName("Test ordinamento con mock comparator")
        void testOrdinamentoConMock() {
            @SuppressWarnings("unchecked")
            ComparatoreLibri comparatorMock = Mockito.mock(ComparatoreLibri.class);

            libreria.setOrdine(comparatorMock);
            libreria.getLibri(); // Questo dovrebbe triggerare la sort

            // Verifica che il comparator sia stato chiamato
            Mockito.verify(comparatorMock, Mockito.atLeastOnce()).compare(Mockito.any(), Mockito.any());
        }
    }

    @Nested
    @DisplayName("Test Filtri")
    class TestFiltri {
        private Libreria libreria;
        private Libro libro1, libro2, libro3;

        @BeforeEach
        void setUp() {
            libreria = new LibreriaConcreta(LibreriaJSON.INSTANCE);
            pulisciLibreria();

            libro1 = new Libro("1984", Set.of("George Orwell"), "0451526341",
                    Libro.Genere.DISTOPIA, 5, Libro.Stato.LETTO);
            libro2 = new Libro("Animal Farm", Set.of("George Orwell"), "0451524934",
                    Libro.Genere.CLASSICO, 4, Libro.Stato.DA_LEGGERE);
            libro3 = new Libro("Dune", Set.of("Frank Herbert"), "0060850523",
                    Libro.Genere.FANTASCIENZA, 5, Libro.Stato.LETTO);

            libreria.aggiungiLibro(libro1);
            libreria.aggiungiLibro(libro2);
            libreria.aggiungiLibro(libro3);
        }

        @AfterEach
        void tearDown() {
            pulisciLibreria();
        }

        @Test
        @DisplayName("Test filtro con mock")
        void testFiltroConMock() {
            Filtro filtroMock = Mockito.mock(Filtro.class);

            // Simulo che il filtro accetti solo libri con valutazione >= 4
            Mockito.when(filtroMock.test(Mockito.any())).thenAnswer(invocation -> {
                Libro libro = invocation.getArgument(0);
                return libro.getValutazione() >= 4;
            });

            libreria.setFiltro(filtroMock);
            List<Libro> filtrati = libreria.getLibri();

            // Verifica chiamata al filtro
            Mockito.verify(filtroMock, Mockito.atLeast(2)).test(Mockito.any());
        }
    }

    @Nested
    @DisplayName("Test Performance")
    class TestPerformance {
        private Libreria libreria;

        @BeforeEach
        void setUp() {
            libreria = new LibreriaConcreta(LibreriaJSON.INSTANCE);
            pulisciLibreria();
        }

        @AfterEach
        void tearDown() {
            pulisciLibreria();
        }

        @Test
        @DisplayName("Test carico massivo")
        void testCaricoMassivo() {
            int N = 500;

            long startTime = System.currentTimeMillis();
            for (int i = 0; i < N; i++) {
                String isbn = generaIsbnValido(i); // Correzione: genera un ISBN valido
                Libro libro = new Libro("Titolo" + i, Set.of("Autore" + i), isbn,
                        Libro.Genere.ROMANZO, (i % 5) + 1, Libro.Stato.DA_LEGGERE);
                libreria.aggiungiLibro(libro);
            }
            long loadTime = System.currentTimeMillis() - startTime;

            assertEquals(N, libreria.size(), "Dovrebbero essere presenti tutti i libri caricati");
            assertTrue(loadTime < 10000, "Il caricamento dovrebbe richiedere meno di 10 secondi");

            // Test filtro su molti elementi
            startTime = System.currentTimeMillis();
            BuildFiltroPredicato builder = new BuildFiltroPredicato();
            builder.addPerValutazione(4, 5);
            libreria.setFiltro(builder.getFiltro());
            List<Libro> filtrati = libreria.getLibri();
            long filterTime = System.currentTimeMillis() - startTime;

            assertTrue(filtrati.stream().allMatch(l -> l.getValutazione() >= 4 && l.getValutazione() <= 5),
                    "Tutti i libri filtrati dovrebbero rispettare il criterio");
            assertTrue(filterTime < 1000, "Il filtraggio dovrebbe richiedere meno di 1 secondo");

            // Test ordinamento su molti elementi
            startTime = System.currentTimeMillis();
            libreria.setFiltro(new NessunFiltro()); // Rimuove filtro
            libreria.setOrdine(new ComparatoreValutazione());
            List<Libro> ordinati = libreria.getLibri();
            long sortTime = System.currentTimeMillis() - startTime;

            assertEquals(N, ordinati.size(), "Dovrebbero essere presenti tutti i libri");
            assertTrue(sortTime < 1000, "L'ordinamento dovrebbe richiedere meno di 1 secondo");

            // Verifica ordinamento per valutazione (decrescente)
            for (int i = 0; i < ordinati.size() - 1; i++) {
                assertTrue(ordinati.get(i).getValutazione() >= ordinati.get(i + 1).getValutazione(),
                        "I libri dovrebbero essere ordinati per valutazione decrescente");
            }
        }

        // Metodo di supporto per generare ISBN-13 validi
        private String generaIsbnValido(int i) {
            String base = String.format("97800000%04d", i); // 12 cifre di base
            int somma = 0;
            for (int j = 0; j < base.length(); j++) {
                int cifra = Character.getNumericValue(base.charAt(j));
                somma += (j % 2 == 0) ? cifra : 3 * cifra;
            }
            int checkDigit = (10 - (somma % 10)) % 10;
            return base + checkDigit;
        }

    }
}