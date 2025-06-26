package TestsJUnit;
import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGetMoviesWithActorContaining {
    @Test
    public void testSubstringSimples() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(1, "Gladiator", 8.5, 155, "01-05-2000"));
        Main.atorArrayList.add(new Ator(101, "Russell Crowe", 'M', 1));

        Result r = Queries.getMoviesWithActorContaining("russell");
        assertEquals("Gladiator", r.result);
    }

    @Test
    public void testSubstringComVariosFilmes() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(2, "Fight Club", 8.8, 139, "15-10-1999"));
        Main.filmeArrayList.add(new Filme(3, "Seven", 8.6, 127, "22-09-1995"));
        Main.atorArrayList.add(new Ator(102, "Brad Pitt", 'M', 2));
        Main.atorArrayList.add(new Ator(102, "Brad Pitt", 'M', 3));

        Result r = Queries.getMoviesWithActorContaining("pitt");
        assertEquals("Fight Club\nSeven", r.result);
    }

    @Test
    public void testSubstringSemResultados() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(4, "Forrest Gump", 8.8, 142, "06-07-1994"));
        Main.atorArrayList.add(new Ator(103, "Tom Hanks", 'M', 4));

        Result r = Queries.getMoviesWithActorContaining("vin diesel");
        assertEquals("No results", r.result);
    }

    @Test
    public void testEvitarDuplicados() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(5, "Ocean's Eleven", 7.7, 116, "07-12-2001"));
        Main.atorArrayList.add(new Ator(104, "Brad Pitt", 'M', 5));
        Main.atorArrayList.add(new Ator(105, "brad pitt", 'M', 5)); // ator repetido em minúsculas

        Result r = Queries.getMoviesWithActorContaining("brad");
        assertEquals("Ocean's Eleven", r.result); // apenas 1 filme, sem duplicação
    }

    @Test
    public void testOrdenacaoAlfabetica() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(6, "Zodiac", 7.7, 157, "02-03-2007"));
        Main.filmeArrayList.add(new Filme(7, "Allied", 7.1, 124, "18-11-2016"));
        Main.atorArrayList.add(new Ator(106, "Brad Pitt", 'M', 6));
        Main.atorArrayList.add(new Ator(106, "Brad Pitt", 'M', 7));

        Result r = Queries.getMoviesWithActorContaining("Pitt");
        assertEquals("Allied\nZodiac", r.result); // verificação da ordenação
    }
}
