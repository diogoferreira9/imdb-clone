package TestsJUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.*;

import java.util.ArrayList;

public class TestCountMoviesBetweenYearsWithNActors {
    @Test
    public void test1FilmeDentroDosLimites() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(1, "Filme A", 6.5, 120, "01-02-2015"));
        Main.atorArrayList.add(new Ator(10, "João", 'M', 1));
        Main.atorArrayList.add(new Ator(11, "Ana", 'F', 1));

        Result r = Queries.countMoviesBetweenYearsWithNActors("2014", "2016", "2", "4");
        assertEquals("1", r.result);
    }

    @Test
    public void testFilmeForaDoIntervaloDeAnos() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(2, "Filme B", 7.0, 100, "10-01-2010"));
        Main.atorArrayList.add(new Ator(12, "Carlos", 'M', 2));
        Main.atorArrayList.add(new Ator(13, "Beatriz", 'F', 2));

        Result r = Queries.countMoviesBetweenYearsWithNActors("2011", "2015", "1", "3");
        assertEquals("0", r.result);
    }

    @Test
    public void testFilmeComAtoresRepetidos() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(3, "Filme C", 8.0, 110, "05-05-2018"));
        Main.atorArrayList.add(new Ator(14, "Maria", 'F', 3));
        Main.atorArrayList.add(new Ator(14, "Maria", 'F', 3));  // Mesmo ator repetido

        Result r = Queries.countMoviesBetweenYearsWithNActors("2017", "2019", "1", "3");
        assertEquals("1", r.result);  // Apenas 1 ator distinto
    }

    @Test
    public void testParametrosInvalidos() {
        Result r = Queries.countMoviesBetweenYearsWithNActors("2020", "2015", "1", "3"); // início > fim
        assertFalse(r.success);
        assertEquals("Erro: Comando invalido", r.result);
    }

    @Test
    public void testLimitesDeAtoresNaoIncluidos() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(4, "Filme D", 5.5, 90, "11-11-2013"));
        Main.atorArrayList.add(new Ator(15, "Pedro", 'M', 4));
        Main.atorArrayList.add(new Ator(16, "Lúcia", 'F', 4));
        Main.atorArrayList.add(new Ator(17, "Miguel", 'M', 4));

        Result r = Queries.countMoviesBetweenYearsWithNActors("2010", "2020", "3", "3"); // min == max (inválido)
        assertFalse(r.success);
        assertEquals("Erro: Comando invalido", r.result);
    }
}
