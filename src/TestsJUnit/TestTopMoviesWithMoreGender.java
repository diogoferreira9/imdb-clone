package TestsJUnit;
import org.junit.jupiter.api.Test;
import Main.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestTopMoviesWithMoreGender {
    @Test
    public void testGeneroMasculinoMaisAtores() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(1, "Action Man", 8.1, 100, "01-03-2022"));
        Main.filmeArrayList.add(new Filme(2, "Romantic Girl", 7.3, 95, "01-03-2022"));

        Main.atorArrayList.add(new Ator(10, "Actor M1", 'M', 1));
        Main.atorArrayList.add(new Ator(11, "Actor M2", 'M', 1));
        Main.atorArrayList.add(new Ator(12, "Actor F1", 'F', 2));
        Main.atorArrayList.add(new Ator(13, "Actor F2", 'F', 2));

        Result r = Queries.topMoviesWithMoreGender("1", "2022", "M");
        assertEquals("Action Man:2", r.result);
    }

    @Test
    public void testGeneroFemininoMaisDeUmFilme() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(3, "Film A", 7.0, 90, "01-01-2023"));
        Main.filmeArrayList.add(new Filme(4, "Film B", 6.5, 85, "01-01-2023"));

        Main.atorArrayList.add(new Ator(20, "Actress 1", 'F', 3));
        Main.atorArrayList.add(new Ator(21, "Actress 2", 'F', 3));
        Main.atorArrayList.add(new Ator(22, "Actress 3", 'F', 4));

        Result r = Queries.topMoviesWithMoreGender("2", "2023", "F");
        assertEquals("Film A:2\nFilm B:1", r.result);
    }

    @Test
    public void testOrdenacaoPorNomeEmEmpate() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(5, "Alpha", 7.0, 90, "01-01-2024"));
        Main.filmeArrayList.add(new Filme(6, "Beta", 7.0, 90, "01-01-2024"));

        Main.atorArrayList.add(new Ator(30, "Actor 1", 'M', 5));
        Main.atorArrayList.add(new Ator(31, "Actor 2", 'M', 6));

        Result r = Queries.topMoviesWithMoreGender("2", "2024", "M");
        assertEquals("Alpha:1\nBeta:1", r.result);
    }

    @Test
    public void testAnoSemFilmesValidos() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Result r = Queries.topMoviesWithMoreGender("3", "1990", "F");
        assertEquals("", r.result);
    }

    @Test
    public void testComandoInvalidoGeneroErrado() {
        Result r = Queries.topMoviesWithMoreGender("2", "2022", "X");
        assertFalse(r.success);
        assertEquals("Erro: Comando invalido", r.result);
    }
}
