package main.comparatore;

import main.Libro;
import java.util.Comparator;

public interface ComparatoreLibri extends Comparator<Libro> {
    @Override
    default ComparatoreLibri reversed() {
        //MODO piu elegante di scrivere un decoratore
        return (o1, o2) -> compare(o2, o1);
    }
}