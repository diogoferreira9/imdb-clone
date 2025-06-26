package TestsJUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.*;

import java.util.ArrayList;
public class TestGetMoviesActorYear {
    @Test
    public void test1FilmeCorreto() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(1, "Inception", 8.8, 148, "16-07-2010"));
        Main.atorArrayList.add(new Ator(101, "Leonardo DiCaprio", 'M', 1));

        Result r = Queries.getMoviesActorYear("2010", "Leonardo DiCaprio");
        assertEquals("Inception", r.result);
    }

    @Test
    public void testVariosFilmesMesmoAno() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(1, "The Revenant", 8.0, 156, "10-01-2015"));
        Main.filmeArrayList.add(new Filme(2, "The Wolf of Wall Street", 8.2, 180, "25-12-2015"));
        Main.atorArrayList.add(new Ator(101, "Leonardo DiCaprio", 'M', 1));
        Main.atorArrayList.add(new Ator(101, "Leonardo DiCaprio", 'M', 2));

        Result r = Queries.getMoviesActorYear("2015", "Leonardo DiCaprio");
        assertEquals("The Revenant\nThe Wolf of Wall Street", r.result);
    }

    @Test
    public void testAnoSemFilmes() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(1, "Titanic", 7.8, 195, "19-12-1997"));
        Main.atorArrayList.add(new Ator(101, "Leonardo DiCaprio", 'M', 1));

        Result r = Queries.getMoviesActorYear("2020", "Leonardo DiCaprio");
        assertEquals("No results", r.result);
    }

    @Test
    public void testAtorInexistente() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(2, "Interstellar", 8.6, 169, "07-11-2014"));
        Main.atorArrayList.add(new Ator(102, "Matthew McConaughey", 'M', 2));

        Result r = Queries.getMoviesActorYear("2014", "Morgan Freeman");
        assertEquals("No results", r.result);
    }

    @Test
    public void testEntradaInvalidaAno() {
        Result r = Queries.getMoviesActorYear("20a4", "Leonardo DiCaprio");
        assertFalse(r.success);
        assertEquals("Erro: Comando invalido", r.result);
    }
}
