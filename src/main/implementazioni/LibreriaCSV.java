//package main.implementazioni;
//
//import main.Libro;
//import
//import java.io.*;
//
//public class LibreriaCSV {
//    private final String filePath = "libreria.csv";
//    public void salvaLibro(Libro libro) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
//            writer.write(libro.toCSV());
//            writer.newLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public void rimuoviLibro(Libro libro) {
//        List<Libro> libri = listaLibri();  // carica tutti i libri
//        boolean rimosso = libri.removeIf(libro -> libro.getTitolo().equalsIgnoreCase(titolo));
//        if (rimosso) {
//            // riscrive il file con i libri rimasti
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
//                for (Libro libro : libri) {
//                    writer.write(libro.toCSV());
//                    writer.newLine();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("Nessun libro trovato con il titolo: " + titolo);
//        }
//    }
//
//    // Restituisce la lista dei libri letti dal file
//    public List<Libro> listaLibri() {
//        List<Libro> libri = new ArrayList<>();
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String riga;
//            while ((riga = reader.readLine()) != null) {
//                libri.add(Libro.fromCSV(riga));
//            }
//        } catch (FileNotFoundException e) {
//            // File non ancora creato → ritorna lista vuota
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return libri;
//    }
//}
