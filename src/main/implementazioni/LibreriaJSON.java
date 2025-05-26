package main.implementazioni;

import main.Libro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.comparatore.ComparatoreLibri;
import main.filtri.Filtro;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class LibreriaJSON implements LibreriaPersistente {
    private Set<Libro> cache;

    private static final String FILE_PATH = "libri.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public LibreriaJSON(){
        cache = caricaPersistenti();
    }
    private Set<Libro> caricaPersistenti(){
        try (java.io.FileReader reader = new java.io.FileReader(FILE_PATH)) {
            java.lang.reflect.Type listType = new com.google.gson.reflect.TypeToken<List<Libro>>() {}.getType();
            List<Libro> libri = gson.fromJson(reader, listType);
            return libri != null ? new HashSet<>(libri) : new HashSet<>();
        } catch (IOException e) {
            return new HashSet<>();
        }
    }

    @Override
    public void salvaLibro(Libro libro) {
        cache.add(libro);
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(cache, writer);
        } catch (IOException e) {
            throw new RuntimeException("Errore nel salvataggio JSON", e);
        }
    }

    @Override
    public void rimuoviLibro(Libro libro) {
        cache.remove(libro);
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(cache, writer);
        } catch (IOException e) {
            throw new RuntimeException("Errore nella rimozione JSON", e);
        }
    }

    @Override
    public List<Libro> caricaLibreria(Filtro filtro, ComparatoreLibri comparatore) {
        return cache.stream().filter(filtro).sorted(comparatore).toList();
    }
}
