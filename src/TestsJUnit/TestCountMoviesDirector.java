package TestsJUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.*;

import java.util.ArrayList;

public class TestCountMoviesDirector {
    @Test
    public void testDiretorCom1Filme() {
        Main.realizadorArrayList = new ArrayList<>();
        Main.realizadorArrayList.add(new Realizador(1, "Steven Spielberg", 101));
        Result r = Queries.countMoviesDirector("Steven Spielberg");
        assertEquals("1", r.result);
    }

    @Test
    public void testDiretorComVariosFilmes() {
        Main.realizadorArrayList = new ArrayList<>();
        Main.realizadorArrayList.add(new Realizador(2, "Christopher Nolan", 201));
        Main.realizadorArrayList.add(new Realizador(3, "Christopher Nolan", 202));
        Main.realizadorArrayList.add(new Realizador(4, "Christopher Nolan", 203));
        Result r = Queries.countMoviesDirector("Christopher Nolan");
        assertEquals("3", r.result);
    }

    @Test
    public void testDiretorInexistente() {
        Main.realizadorArrayList = new ArrayList<>();
        Main.realizadorArrayList.add(new Realizador(5, "James Cameron", 301));
        Result r = Queries.countMoviesDirector("Peter Jackson");
        assertEquals("0", r.result);
    }

    @Test
    public void testDiretorCaseInsensitive() {
        Main.realizadorArrayList = new ArrayList<>();
        Main.realizadorArrayList.add(new Realizador(6, "Quentin Tarantino", 401));
        Result r = Queries.countMoviesDirector("quentin tarantino");
        assertEquals("1", r.result);
    }

    @Test
    public void testDiretorComEspacos() {
        Main.realizadorArrayList = new ArrayList<>();
        Main.realizadorArrayList.add(new Realizador(7, "  Sofia Coppola  ", 501));
        Result r = Queries.countMoviesDirector("  Sofia Coppola  ");
        assertEquals("1", r.result);
    }
}
