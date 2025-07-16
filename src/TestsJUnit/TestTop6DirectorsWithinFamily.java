package TestsJUnit;
import org.junit.jupiter.api.Test;
import Main.*;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestTop6DirectorsWithinFamily {
    @Test
    public void testDoisRealizadoresComMesmoApelido() {
        Main.filmeArrayList = new ArrayList<>();
        Main.realizadorArrayList = new ArrayList<>();
        Main.realizadoresPorFilmeMap = new HashMap<>();

        Filme f = new Filme(1, "Família", 8.0, 100, "01-01-2000");
        Main.filmeArrayList.add(f);

        Realizador r1 = new Realizador(4, "Joao Castro", 4);
        Realizador r2 = new Realizador(4, "Joao Castro", 4);
        ArrayList<Realizador> familia = new ArrayList<>();
        familia.add(r1);
        familia.add(r2);
        Main.realizadoresPorFilmeMap.put(1, familia);

        Result r = Queries.top6DirectorsWithinFamily("1990", "2010");
        assertTrue(r.success);
        assertEquals("TOP_6_DIRECTORS_WITHIN_FAMILY", r.error);
        assertEquals("Joao Castro:2", r.result);
    }

    @Test
    public void testForaDoIntervalo() {
        Main.filmeArrayList = new ArrayList<>();
        Main.realizadoresPorFilmeMap = new HashMap<>();

        Filme f = new Filme(2, "Antigo", 7.0, 90, "01-01-1980");
        Main.filmeArrayList.add(f);

        Realizador r1 = new Realizador(4, "Joao Castro", 4);
        Realizador r2 = new Realizador(4, "Joao Castro", 4);
        ArrayList<Realizador> familia = new ArrayList<>();
        familia.add(r1);
        familia.add(r2);
        Main.realizadoresPorFilmeMap.put(2, familia);

        Result r = Queries.top6DirectorsWithinFamily("1990", "2000");
        assertTrue(r.success);
        assertEquals("TOP_6_DIRECTORS_WITHIN_FAMILY", r.error);
        assertEquals("No results", r.result);
    }

    @Test
    public void testIntervaloInvalido() {
        Result r = Queries.top6DirectorsWithinFamily("2020", "2010");
        assertFalse(r.success);
        assertEquals("TOP_6_DIRECTORS_WITHIN_FAMILY", r.error);
        assertEquals("Erro: Comando invalido", r.result);
    }

    @Test
    public void testSoUmRealizador() {
        Main.filmeArrayList = new ArrayList<>();
        Main.realizadoresPorFilmeMap = new HashMap<>();

        Filme f = new Filme(3, "Solo", 6.5, 80, "01-01-2005");
        Main.filmeArrayList.add(f);

        Realizador r = new Realizador(4, "Joao Castro", 4);
        ArrayList<Realizador> lista = new ArrayList<>();
        lista.add(r);
        Main.realizadoresPorFilmeMap.put(3, lista);

        Result res = Queries.top6DirectorsWithinFamily("2000", "2010");
        assertTrue(res.success);
        assertEquals("TOP_6_DIRECTORS_WITHIN_FAMILY", res.error);
        assertEquals("No results", res.result);
    }

    @Test
    public void testMaisDeSeisRealizadores() {
        Main.filmeArrayList = new ArrayList<>();
        Main.realizadoresPorFilmeMap = new HashMap<>();

        Filme f = new Filme(4, "Família Grande", 7.5, 120, "01-01-2000");
        Main.filmeArrayList.add(f);

        ArrayList<Realizador> familia = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            familia.add(new Realizador(4, "Joao Castro", 4));
        }
        Main.realizadoresPorFilmeMap.put(4, familia);

        Result r = Queries.top6DirectorsWithinFamily("1990", "2010");
        assertTrue(r.success);
        assertEquals("TOP_6_DIRECTORS_WITHIN_FAMILY", r.error);
        assertEquals(1, r.result.split("\\n").length);
    }
}
