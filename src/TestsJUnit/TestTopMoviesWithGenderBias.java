package TestsJUnit;
import org.junit.jupiter.api.Test;
import Main.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestTopMoviesWithGenderBias {
    @Test
    public void testFilmeComGeneroDominanteM() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(1, "Filme M", 8.1, 100, "10-10-2020"));

        // 12 atores M, 11 F
        for (int i = 0; i < 12; i++) {
            Main.atorArrayList.add(new Ator(i, "M" + i, 'M', 1));
        }
        for (int i = 0; i < 11; i++) {
            Main.atorArrayList.add(new Ator(100 + i, "F" + i, 'F', 1));
        }

        Result r = Queries.topMoviesWithGenderBias("1", "2020");
        assertEquals("Filme M:M:4", r.result);
    }

    @Test
    public void testGeneroFemininoDominante() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(2, "Filme F", 7.5, 90, "01-01-2020"));

        for (int i = 0; i < 11; i++) {
            Main.atorArrayList.add(new Ator(i, "F" + i, 'F', 2));
        }
        for (int i = 0; i < 13; i++) {
            Main.atorArrayList.add(new Ator(50 + i, "M" + i, 'M', 2));
        }

        Result r = Queries.topMoviesWithGenderBias("1", "2020");
        assertEquals("Filme F:M:8", r.result);
    }

    @Test
    public void testOrdenacaoPorPercentagemEAlfabetica() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        // Criar dois filmes no mesmo ano
        Main.filmeArrayList.add(new Filme(3, "Alpha", 8.0, 100, "10-10-2022"));
        Main.filmeArrayList.add(new Filme(4, "Beta", 8.0, 100, "11-10-2022"));

        // Ambos têm 11 M e 11 F — percentagem de diferença = 0%
        for (int i = 0; i < 11; i++) {
            Main.atorArrayList.add(new Ator(i, "M", 'M', 3));
            Main.atorArrayList.add(new Ator(100 + i, "F", 'F', 3));

            Main.atorArrayList.add(new Ator(200 + i, "M", 'M', 4));
            Main.atorArrayList.add(new Ator(300 + i, "F", 'F', 4));
        }

        Result r = Queries.topMoviesWithGenderBias("2", "2022");

        // Validação robusta
        String[] esperado = {"Alpha:M:0", "Beta:M:0"};
        String[] obtido = r.result.split("\n");

        assertArrayEquals(esperado, obtido);
    }
    @Test
    public void testFilmeSemAtoresSuficientesNaoEntra() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(5, "Sem Atores", 6.5, 100, "01-01-2021"));

        for (int i = 0; i < 10; i++) {
            Main.atorArrayList.add(new Ator(i, "M", 'M', 5));
            Main.atorArrayList.add(new Ator(100 + i, "F", 'F', 5));
        }

        Result r = Queries.topMoviesWithGenderBias("5", "2021");
        assertEquals("No results", r.result);
    }

    @Test
    public void testEntradaInvalidaAnoErrado() {
        Result r = Queries.topMoviesWithGenderBias("2", "20a0");
        assertFalse(r.success);
        assertEquals("Erro: Comando invalido", r.result);
    }
}
