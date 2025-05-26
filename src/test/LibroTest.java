package test;

import main.Libro;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Nested
public class LibroTest {
    @Test
    void testCompletoLibroImpl() {
        // Creazione con ISBN-10 valido
        Libro libro1 = new Libro("1984", Set.of("George Orwell"), "123456789X",
                Libro.Genere.DISTOPIA, 5, Libro.Stato.LETTO);

        assertEquals("1984", libro1.getTitolo());
        assertTrue(libro1.getAutori().contains("George Orwell"));
        assertEquals("123456789X", libro1.getIsbn());
        assertEquals(Libro.Genere.DISTOPIA, libro1.getGenere());
        assertEquals(5, libro1.getValutazione());
        assertEquals(Libro.Stato.LETTO, libro1.getStato());

        libro1.setStato(Libro.Stato.IN_CORSO);
        assertEquals(Libro.Stato.IN_CORSO, libro1.getStato());

        // ISBN invalido
        assertThrows(IllegalArgumentException.class, () -> {
            new Libro("Errore", Set.of("Autore"), "BADISBN", Libro.Genere.ROMANZO, 3, Libro.Stato.LETTO);
        });

        // Valutazione invalida
        assertThrows(IllegalArgumentException.class, () -> {
            new Libro("Errore", Set.of("Autore"), "1234567890", Libro.Genere.ROMANZO, 0, Libro.Stato.LETTO);
        });

        // ISBN-13 valido
        assertDoesNotThrow(() -> {
            new Libro("Altro", Set.of("Autore"), "9781234567890", Libro.Genere.CLASSICO, 3, Libro.Stato.DA_LEGGERE);
        });
    }
}
