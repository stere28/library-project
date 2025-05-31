package main.implementazioni;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import main.Libro;

import main.comparatore.ComparatoreLibri;
import main.filtri.Filtro;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public enum LibreriaJSON implements LibreriaPersistente {
    INSTANCE;

    private static final String FILE_PATH = "libri.json";
    private final Set<Libro> cache;
    private final Gson gson;

    // Costruttore Singleton
    LibreriaJSON() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        cache = caricaPersistenti();
    }

    private Set<Libro> caricaPersistenti() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Libro>>() {}.getType();
            List<Libro> libri = gson.fromJson(reader, listType);
            return libri != null ? new HashSet<>(libri) : new HashSet<>();
        } catch (IOException e) {
            return new HashSet<>();
        }
    }

    @Override
    public void salvaLibro(Libro libro) {
        cache.add(libro);
        salvaSuDisco();
    }

    @Override
    public void rimuoviLibro(Libro libro) {
        cache.remove(libro);
        salvaSuDisco();
    }

    private void salvaSuDisco() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(cache, writer);
        } catch (IOException e) {
            throw new RuntimeException("Errore nel salvataggio JSON", e);
        }
    }

    @Override
    public List<Libro> caricaLibreria(Filtro filtro, ComparatoreLibri comparatore) {
        return cache.stream()
                .filter(filtro)
                .sorted(comparatore)
                .toList();
    }
}
