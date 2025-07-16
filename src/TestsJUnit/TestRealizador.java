package TestsJUnit;

import org.junit.jupiter.api.Test;
import Main.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRealizador {

    @Test
    public void testRealizadorSimples() {
        Realizador r = new Realizador(1, "Christopher Nolan", 1000);
        assertEquals("1 | Christopher Nolan | 1000", r.toString());
    }

    @Test
    public void testRealizadorNomeComEspacos() {
        Realizador r = new Realizador(2, "  Sofia Coppola  ", 1001);
        assertEquals("2 |   Sofia Coppola   | 1001", r.toString());
    }

    @Test
    public void testRealizadorNomeVazio() {
        Realizador r = new Realizador(3, "", 1002);
        assertEquals("3 |  | 1002", r.toString());
    }

    @Test
    public void testRealizadorComIdNegativo() {
        Realizador r = new Realizador(-1, "Erro de ID", 1003);
        assertEquals("-1 | Erro de ID | 1003", r.toString());
    }

    @Test
    public void testRealizadorComFilmeIdIncomum() {
        Realizador r = new Realizador(4, "Kubrick", 0);
        assertEquals("4 | Kubrick | 0", r.toString());
    }
}