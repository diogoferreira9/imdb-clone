package TestsJUnit;

import org.junit.jupiter.api.Test;
import Main.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFilmeIdMenor1000 {
    @Test
    public void testFilmeIdBaixo_semAtores() {
        Main.atorArrayList.clear();

        Filme filme = new Filme(999, "Indie Film", 90.0, 5000, "10/10/2020");
        String esperado = "999 | Indie Film | 2020-10-10 |  |  | 0 | 0";
        assertEquals(esperado, filme.toString());
    }

    @Test
    public void testFilmeIdBaixo_com1Ator() {
        Main.atorArrayList.clear();
        Main.atorArrayList.add(new Ator(1, "João Silva", 'M', 998));

        Filme filme = new Filme(998, "Solo Project", 80.0, 1000, "01/01/2021");
        String esperado = "998 | Solo Project | 2021-01-01 |  |  | 1 | 0";
        assertEquals(esperado, filme.toString());
    }

    @Test
    public void testFilmeIdBaixo_comVariosAtores() {
        Main.atorArrayList.clear();
        Main.atorArrayList.add(new Ator(1, "Ana", 'F', 997));
        Main.atorArrayList.add(new Ator(2, "Bruno", 'M', 997));
        Main.atorArrayList.add(new Ator(3, "Carla", 'F', 997));

        Filme filme = new Filme(997, "Team Film", 120.0, 200000, "25/12/2019");
        String esperado = "997 | Team Film | 2019-12-25 |  |  | 1 | 2";
        assertEquals(esperado, filme.toString());
    }

    @Test
    public void testFilmeIdBaixo_dataComTracos() {
        Main.atorArrayList.clear();

        Filme filme = new Filme(996, "Dash Date", 100.0, 3000, "05-05-2018");
        String esperado = "996 | Dash Date | 2018-05-05 |  |  | 0 | 0";
        assertEquals(esperado, filme.toString());
    }

    @Test
    public void testFilmeIdBaixo_comAtoresErradoId() {
        Main.atorArrayList.clear();
        // Ator com movieId diferente do filme
        Main.atorArrayList.add(new Ator(1, "Alguém", 'M', 1234));

        Filme filme = new Filme(995, "No Matching Actor", 90.0, 12345, "12/12/2012");
        String esperado = "995 | No Matching Actor | 2012-12-12 |  |  | 0 | 0";
        assertEquals(esperado, filme.toString());
    }
}
