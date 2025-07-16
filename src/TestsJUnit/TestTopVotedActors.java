package TestsJUnit;
import org.junit.jupiter.api.Test;
import Main.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestTopVotedActors {
    @Test
    public void testUnicoAtorComUmFilme() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(1, "Titanic", 8.5, 195, "12-01-1997"));
        Main.atorArrayList.add(new Ator(100, "Leonardo DiCaprio", 'M', 1));

        Result r = Queries.topVotedActors("1", "1997");
        assertEquals("Leonardo DiCaprio:0,0", r.result);
    }

    @Test
    public void testMediaComDoisFilmes() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(2, "Inception", 9.0, 148, "16-07-2010"));
        Main.filmeArrayList.add(new Filme(3, "Shutter Island", 8.0, 138, "19-02-2010"));
        Main.atorArrayList.add(new Ator(101, "Leonardo DiCaprio", 'M', 2));
        Main.atorArrayList.add(new Ator(101, "Leonardo DiCaprio", 'M', 3));

        Result r = Queries.topVotedActors("1", "2010");
        assertEquals("Leonardo DiCaprio:0,0", r.result);
    }

    @Test
    public void testVariosAtoresComOrdenacaoPorMedia() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.filmeArrayList.add(new Filme(4, "Movie A", 7.0, 100, "01-01-2022"));
        Main.filmeArrayList.add(new Filme(5, "Movie B", 9.0, 110, "01-01-2022"));
        Main.atorArrayList.add(new Ator(200, "Actor Menor", 'M', 4));
        Main.atorArrayList.add(new Ator(201, "Actor Maior", 'F', 5));

        Result r = Queries.topVotedActors("2", "2022");
        assertEquals("Actor Maior:9.0\nActor Menor:7.0", r.result);
    }

    @Test
    public void testAnoSemFilmesValidos() {
        Main.filmeArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Result r = Queries.topVotedActors("5", "1990");
        assertEquals("", r.result);
    }

    @Test
    public void testEntradaInvalida() {
        Result r = Queries.topVotedActors("zero", "2022");
        assertFalse(r.success);
        assertEquals("Erro: Comando invalido", r.result);
    }
}