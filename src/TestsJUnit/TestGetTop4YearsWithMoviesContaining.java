package TestsJUnit;
import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGetTop4YearsWithMoviesContaining {

    @Test
    public void testSimplesCom1Ano() {
        Main.filmeArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(1, "Star Wars", 8.5, 121, "25-05-1977"));
        Main.filmeArrayList.add(new Filme(2, "Star Trek", 7.9, 110, "01-06-1977"));

        Result r = Queries.getTop4YearsWithMoviesContaining("Star");
        assertEquals("1977:2", r.result);
    }

    @Test
    public void testVariosAnosTop4() {
        Main.filmeArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(3, "Star 1", 7.0, 100, "01-01-2001"));
        Main.filmeArrayList.add(new Filme(4, "Star 2", 7.0, 100, "01-01-2001"));
        Main.filmeArrayList.add(new Filme(5, "Star 3", 7.0, 100, "01-01-2002"));
        Main.filmeArrayList.add(new Filme(6, "Star 4", 7.0, 100, "01-01-2003"));
        Main.filmeArrayList.add(new Filme(7, "Star 5", 7.0, 100, "01-01-2004"));
        Main.filmeArrayList.add(new Filme(8, "Star 6", 7.0, 100, "01-01-2004"));

        Result r = Queries.getTop4YearsWithMoviesContaining("Star");
        assertEquals("2001:2\n2004:2\n2002:1\n2003:1", r.result);
    }

    @Test
    public void testSensibilidadeMaiusculas() {
        Main.filmeArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(9, "Avengers", 8.4, 143, "04-05-2012"));
        Main.filmeArrayList.add(new Filme(10, "avengers", 8.4, 143, "05-05-2013")); // lowercase

        Result r = Queries.getTop4YearsWithMoviesContaining("Avengers");
        assertEquals("2012:1", r.result); // apenas 1 com maiúscula igual
    }

    @Test
    public void testSemResultados() {
        Main.filmeArrayList = new ArrayList<>();
        Main.filmeArrayList.add(new Filme(11, "Titanic", 7.8, 195, "11-12-1997"));

        Result r = Queries.getTop4YearsWithMoviesContaining("Batman");
        assertEquals("No results", r.result);
    }

    @Test
    public void testEmpateNumeroFilmesDesempateAno() {
        Main.filmeArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(12, "Flash", 6.5, 130, "01-01-2010"));
        Main.filmeArrayList.add(new Filme(13, "Flash", 6.5, 130, "01-01-2012"));
        Main.filmeArrayList.add(new Filme(14, "Flash", 6.5, 130, "01-01-2012"));
        Main.filmeArrayList.add(new Filme(15, "Flash", 6.5, 130, "01-01-2010"));

        Result r = Queries.getTop4YearsWithMoviesContaining("Flash");
        assertEquals("2010:2\n2012:2", r.result); // 2010 vem primeiro por ser mais antigo
    }
}
