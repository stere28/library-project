package io.stefano.implementazioni;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.stefano.Libro;

import io.stefano.comparatore.ComparatoreLibri;
import io.stefano.filtri.Filtro;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public enum LibreriaJSON implements LibreriaPersistente {

    //TODO riguardare la sincronizzazione, al momento non è un problema in quanto non multi thread
    INSTANCE;
    private static final String FILE_PATH = "libri.json";
    private final Set<Libro> cache;
    private final Gson gson;

    // Costruttore Singleton
    LibreriaJSON() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        cache = caricaPersistenti();
    }

    @Override
    public synchronized void salvaLibro(Libro libro) {
        if(cache.contains(libro)) throw new IllegalArgumentException("Libro gia presente");
        cache.add(libro);
        salvaSuDisco();

    }

    @Override
    public synchronized void rimuoviLibro(Libro libro) {
        if(! cache.contains(libro)) throw new IllegalArgumentException("Libro non presente nella libreria");
        cache.remove(libro);
        salvaSuDisco();
    }
    @Override
    public synchronized List<Libro> caricaLibreria(Filtro filtro, ComparatoreLibri comparatore) {
        Objects.requireNonNull(filtro, "Il filtro non può essere null");
        Objects.requireNonNull(comparatore, "Il comparatore non può essere null");

        return cache.stream()
                .filter(filtro)
                .sorted(comparatore)
                .toList();
    }
    private  Set<Libro> caricaPersistenti() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<List<Libro>>() {}.getType();
            List<Libro> libri = gson.fromJson(reader, listType);
            return libri != null ? new HashSet<>(libri) : new HashSet<>();
        } catch (IOException e) {
            return new HashSet<>();
        }
    }
    private void salvaSuDisco() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(cache, writer);
        } catch (IOException e) {
            throw new RuntimeException("Errore nel salvataggio JSON", e);
        }
    }
}
