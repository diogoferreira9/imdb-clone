package TestsJUnit;
import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestTopMonthMovieCount {
    @Test
    public void testMesComMaisFilmesUnico() {
        Main.filmeArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(1, "Movie A", 7.0, 100, "01-03-2022"));
        Main.filmeArrayList.add(new Filme(2, "Movie B", 7.0, 100, "05-03-2022"));
        Main.filmeArrayList.add(new Filme(3, "Movie C", 7.0, 100, "01-06-2022"));

        Result r = Queries.topMonthMovieCount("2022");
        assertEquals("3:2", r.result); // Março tem 2 filmes
    }

    @Test
    public void testMesComEmpateEscolheMaisAntigo() {
        Main.filmeArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(4, "Movie D", 6.5, 95, "01-01-2021"));
        Main.filmeArrayList.add(new Filme(5, "Movie E", 6.5, 95, "01-01-2021"));
        Main.filmeArrayList.add(new Filme(6, "Movie F", 6.5, 95, "01-05-2021"));
        Main.filmeArrayList.add(new Filme(7, "Movie G", 6.5, 95, "01-05-2021"));

        Result r = Queries.topMonthMovieCount("2021");
        assertEquals("1:2", r.result); // Janeiro e Maio têm 2, mas Janeiro é mais antigo
    }

    @Test
    public void testAnoSemFilmes() {
        Main.filmeArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(8, "Movie H", 8.0, 110, "01-01-2020"));

        Result r = Queries.topMonthMovieCount("2019");
        assertEquals("No results", r.result); // Nenhum filme de 2019
    }

    @Test
    public void testAnoComDataMalFormada() {
        Main.filmeArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(9, "Movie I", 8.0, 110, "MAL-FORMATADA"));

        Result r = Queries.topMonthMovieCount("2022");
        assertEquals("No results", r.result); // Nenhuma data válida
    }

    @Test
    public void testAnoInvalidoFormato() {
        Result r = Queries.topMonthMovieCount("22"); // ano mal formatado (2 dígitos)
        assertFalse(r.success);
        assertEquals("Erro: Comando invalido", r.result);
    }
}
