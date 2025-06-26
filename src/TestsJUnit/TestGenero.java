package TestsJUnit;

import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.Genero;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGenero {

    @Test
    public void testGeneroSimples() {
        Genero genero = new Genero(1, "Ação");
        assertEquals("1 | Ação", genero.toString());
    }

    @Test
    public void testGeneroComEspacos() {
        Genero genero = new Genero(2, "  Drama  ");
        assertEquals("2 |   Drama  ", genero.toString());
    }

    @Test
    public void testGeneroComNomeVazio() {
        Genero genero = new Genero(3, "");
        assertEquals("3 | ", genero.toString());
    }

    @Test
    public void testGeneroComCaracteresEspeciais() {
        Genero genero = new Genero(4, "Sci-Fi/Fantasia");
        assertEquals("4 | Sci-Fi/Fantasia", genero.toString());
    }

    @Test
    public void testGeneroComIdZero() {
        Genero genero = new Genero(0, "Documentário");
        assertEquals("0 | Documentário", genero.toString());
    }
}
