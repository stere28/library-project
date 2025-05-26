package test;

import main.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Nested
public class LibreriaTest {
    protected Libreria libreria;

    @BeforeEach
    void setUp() {
        libreria = new LibreriaConcreta();
    }



    @Test
    void testGenericoDiBase() {
        Libro libro1 = new Libro("1984", Set.of("George Orwell"), "123456789X",
                Libro.Genere.DISTOPIA, 5, Libro.Stato.LETTO);
        Libro libro2 = new Libro("1984", Set.of("George"), "123456779X",
                Libro.Genere.DISTOPIA, 5, Libro.Stato.LETTO);

        libreria.aggiungiLibro(libro1);
        assertTrue(libreria.getLibri().contains(libro1));
        libreria.aggiungiLibro(libro2);

        int count = 0;
        for (Libro l : libreria) {
            count++;
        }
        assertEquals(2, count);

        libreria.rimuoviLibro(libro1);
        assertFalse(libreria.getLibri().contains(libro1));
    }

    @Test
    void testCaricoMassivo() {
        int libriIniziali = libreria.size();
        int N = 10000;
        for (int i = 0; i < N; i++) {
            String isbn = String.format("%010d", i);
            Libro libro = new Libro("Titolo" + i, Set.of("Autore" + i), isbn,
                    Libro.Genere.ROMANZO, 3, Libro.Stato.DA_LEGGERE);
            libreria.aggiungiLibro(libro);
        }
        assertEquals(N + libriIniziali, libreria.size());

        // Applica un filtro qualsiasi se implementato
        libreria.setFiltro(libro -> libro.getValutazione() >= 3);
        List<Libro> filtrati = libreria.getLibri();
        assertTrue(filtrati.stream().allMatch(l -> l.getValutazione() >= 3));
    }
}
