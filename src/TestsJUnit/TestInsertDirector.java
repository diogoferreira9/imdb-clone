package TestsJUnit;
import org.junit.jupiter.api.Test;
import Main.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestInsertDirector {
    @Test
    void testInsercaoValidaDeRealizador() {
        Main.filmeArrayList.clear();
        Main.realizadorArrayList.clear();
        Main.realizadoresPorFilmeMap.clear();

        Main.filmeArrayList.add(new Filme(1, "Inception", 8.8, 2010, "01-01-2010"));

        Result r = Queries.insertDirector("100;Christopher Nolan;1");

        assertTrue(r.success);
        assertEquals("INSERT_DIRECTOR", r.error);
        assertEquals("OK", r.result);
    }

    @Test
    void testIdJaExiste() {
        Main.filmeArrayList.clear();
        Main.realizadorArrayList.clear();
        Main.realizadoresPorFilmeMap.clear();

        Main.realizadorArrayList.add(new Realizador(101, "Spielberg", 1));
        Main.filmeArrayList.add(new Filme(1, "Jaws", 8.0, 1975, "01-06-1975"));

        Result r = Queries.insertDirector("101;Steven Spielberg;1");

        assertFalse(r.success);
        assertEquals("INSERT_DIRECTOR", r.error);
        assertEquals("Erro", r.result);
    }

    @Test
    void testFilmeNaoExiste() {
        Main.filmeArrayList.clear();
        Main.realizadorArrayList.clear();
        Main.realizadoresPorFilmeMap.clear();

        Result r = Queries.insertDirector("102;Quentin Tarantino;99");

        assertFalse(r.success);
        assertEquals("INSERT_DIRECTOR", r.error);
        assertEquals("Erro", r.result);
    }

    @Test
    void testArgumentosIncompletos() {
        Result r = Queries.insertDirector("103;Martin Scorsese");

        assertFalse(r.success);
        assertEquals("INSERT_DIRECTOR", r.error);
        assertEquals("Erro", r.result);
    }

    @Test
    void testIdInvalido() {
        Result r = Queries.insertDirector("abc;James Cameron;1");

        assertFalse(r.success);
        assertEquals("INSERT_DIRECTOR", r.error);
        assertEquals("Erro", r.result);
    }

}
