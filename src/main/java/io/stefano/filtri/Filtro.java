package io.stefano.filtri;

import io.stefano.Libro;
import java.util.function.Predicate;

public interface Filtro extends Predicate<Libro> {
//    @Override
//    default Filtro and(Predicate<? super Libro> other) {
//        return libro -> test(libro) && other.test(libro);
//    }
    void setNext(Filtro next);
}