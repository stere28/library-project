package main.comparatore;

import main.Libro;

public class ComparatoreTitolo implements ComparatoreLibri{
    @Override
    public int compare(Libro o1, Libro o2) {
        return o1.getTitolo().compareTo(o2.getTitolo());
    }
}
