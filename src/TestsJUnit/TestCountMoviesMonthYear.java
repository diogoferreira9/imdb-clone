package TestsJUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.*;

import java.util.ArrayList;

public class TestCountMoviesMonthYear {
    @Test
    public void testContagemValida1Filme() {
        Main.filmeArrayList = new ArrayList<>();
        Main.filmeArrayList.add(new Filme(1, "Matrix", 8.7, 136, "15-03-2020"));
        Result r = Queries.countMoviesMonthYear("3", "2020");
        assertEquals("1", r.result);
    }
    @Test
    public void testContagemZeroFilmes() {
        Main.filmeArrayList = new ArrayList<>();
        Main.filmeArrayList.add(new Filme(2, "Titanic", 7.8, 195, "11-01-1997"));
        Result r = Queries.countMoviesMonthYear("12", "2021");
        assertEquals("0", r.result);
    }

    @Test
    public void testContagemVariosFilmesMesmoMesAno() {
        Main.filmeArrayList = new ArrayList<>();
        Main.filmeArrayList.add(new Filme(3, "Filme A", 6.5, 100, "01-05-2021"));
        Main.filmeArrayList.add(new Filme(4, "Filme B", 7.2, 90, "12-05-2021"));
        Main.filmeArrayList.add(new Filme(5, "Filme C", 5.9, 80, "30-04-2021")); // mês diferente
        Result r = Queries.countMoviesMonthYear("5", "2021");
        assertEquals("2", r.result);
    }

    @Test
    public void testMesInvalido() {
        Result r = Queries.countMoviesMonthYear("13", "2020");
        assertFalse(r.success);
        assertEquals("Erro: Comando invalido", r.result);
    }

    @Test
    public void testAnoInvalidoComMenosDigitos() {
        Result r = Queries.countMoviesMonthYear("4", "98");
        assertFalse(r.success);
        assertEquals("Erro: Comando invalido", r.result);
    }
}
