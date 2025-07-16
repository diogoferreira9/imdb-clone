package TestsJUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import Main.*;

import java.util.ArrayList;

public class TestCountActorsIn2Years {
    @Test
    public void testMesmoAno2FilmesMesmoAtor() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(1, "Filme A", 7.0, 120, "01-01-2020"));
        Main.filmeArrayList.add(new Filme(2, "Filme B", 7.5, 110, "02-03-2020"));
        Main.atorArrayList.add(new Ator(10, "João", 'M', 1));
        Main.atorArrayList.add(new Ator(10, "João", 'M', 2)); // mesmo ator, 2 filmes

        Result r = Queries.countActorsIn2Years("2020", "2020");
        assertEquals("1", r.result); // 1 ator com ≥2 filmes
    }

    @Test
    public void testAnosDiferentesMesmoAtor() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(1, "Filme A", 6.5, 100, "01-01-2019"));
        Main.filmeArrayList.add(new Filme(2, "Filme B", 7.0, 90, "15-04-2020"));
        Main.atorArrayList.add(new Ator(20, "Ana", 'F', 1));
        Main.atorArrayList.add(new Ator(20, "Ana", 'F', 2)); // mesma atriz em ambos os anos

        Result r = Queries.countActorsIn2Years("2019", "2020");
        assertEquals("1", r.result); // 1 ator em ambos os anos
    }

    @Test
    public void testAnosDiferentesSemIntersecao() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(3, "Filme C", 6.0, 80, "01-01-2021"));
        Main.filmeArrayList.add(new Filme(4, "Filme D", 7.8, 100, "15-06-2022"));
        Main.atorArrayList.add(new Ator(30, "Carlos", 'M', 3));
        Main.atorArrayList.add(new Ator(31, "Beatriz", 'F', 4)); // atores diferentes

        Result r = Queries.countActorsIn2Years("2021", "2022");
        assertEquals("0", r.result);
    }

    @Test
    public void testAnoInvalido() {
        Result r = Queries.countActorsIn2Years("202", "2022"); // ano com 3 dígitos
        assertFalse(r.success);
        assertEquals("Erro: Comando invalido", r.result);
    }

    @Test
    public void testMesmoAnoSemRepeticaoDeFilmes() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(5, "Filme E", 5.0, 95, "01-01-2020"));
        Main.atorArrayList.add(new Ator(40, "Miguel", 'M', 5)); // só um filme

        Result r = Queries.countActorsIn2Years("2020", "2020");
        assertEquals("0", r.result); // nenhum ator com ≥2 filmes
    }

}
