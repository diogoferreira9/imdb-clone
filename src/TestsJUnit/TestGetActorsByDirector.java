package TestsJUnit;
import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestGetActorsByDirector {
    @Test
    public void testUmAtorComParticipacaoSuficiente() {
        Main.realizadorArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.realizadorArrayList.add(new Realizador(1, "James Cameron", 100));
        Main.realizadorArrayList.add(new Realizador(1, "James Cameron", 101));

        Main.atorArrayList.add(new Ator(10, "Arnold Schwarzenegger", 'M', 100));
        Main.atorArrayList.add(new Ator(10, "Arnold Schwarzenegger", 'M', 101));

        Result r = Queries.getActorsByDirector("2", "James Cameron");
        assertEquals("Arnold Schwarzenegger:2", r.result);
    }

    @Test
    public void testVariosAtoresAcimaDoMinimo() {
        Main.realizadorArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.realizadorArrayList.add(new Realizador(2, "Steven Spielberg", 200));
        Main.realizadorArrayList.add(new Realizador(2, "Steven Spielberg", 201));
        Main.realizadorArrayList.add(new Realizador(2, "Steven Spielberg", 202));

        Main.atorArrayList.add(new Ator(20, "Tom Hanks", 'M', 200));
        Main.atorArrayList.add(new Ator(20, "Tom Hanks", 'M', 201));
        Main.atorArrayList.add(new Ator(21, "Mark Rylance", 'M', 202));
        Main.atorArrayList.add(new Ator(21, "Mark Rylance", 'M', 201));

        Result r = Queries.getActorsByDirector("2", "Steven Spielberg");
        assertEquals("Mark Rylance:2\nTom Hanks:2", r.result);
    }

    @Test
    public void testAtorNaoAtingeMinimo() {
        Main.realizadorArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.realizadorArrayList.add(new Realizador(3, "Christopher Nolan", 300));
        Main.atorArrayList.add(new Ator(30, "Cillian Murphy", 'M', 300));

        Result r = Queries.getActorsByDirector("2", "Christopher Nolan");
        assertEquals("No results", r.result);
    }

    @Test
    public void testRealizadorInexistente() {
        Main.realizadorArrayList = new ArrayList<>();
        Main.atorArrayList = new ArrayList<>();

        Main.realizadorArrayList.add(new Realizador(4, "Ridley Scott", 400));
        Main.atorArrayList.add(new Ator(40, "Russell Crowe", 'M', 400));

        Result r = Queries.getActorsByDirector("1", "Zack Snyder");
        assertEquals("No results", r.result);
    }

    @Test
    public void testMinimoInvalido() {
        Result r = Queries.getActorsByDirector("0", "James Cameron");
        assertFalse(r.success);
        assertEquals("Erro: Comando invalido", r.result);
    }

}
