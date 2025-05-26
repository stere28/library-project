package main.filtri;

import main.Libro;

import java.util.function.Predicate;


public interface Filtro extends Predicate<Libro>{ }//marker interface