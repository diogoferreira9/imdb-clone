package TestsJUnit;

import org.junit.jupiter.api.Test;
import Main.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFilmeIdMaior1000 {
    @Test
    public void testFilmeId1000_formatoPadrao() {
        Filme filme = new Filme(5000, "Bad Format", 120.0, 100000, "2022/10/15");
        String esperado = "5000 | Bad Format | 2022-10-15 | 0 | 0 | 0 | 0";
        assertEquals(esperado, filme.toString());
    }
    @Test
    public void testFilmeIdAcima1000_dataComTracos() {
        Filme filme = new Filme(1500, "Avatar", 162.0, 237000000, "18-12-2009");
        String esperado = "1500 | Avatar | 2009-12-18 | 0 | 0 | 0 | 0";
        assertEquals(esperado, filme.toString());
    }
    @Test
    public void testFilmeId1000_semAtores() {
        Main.atorArrayList.clear();
        // Cria um filme com id < 1000 (deve mostrar totalAtores = 0)
        Filme filme = new Filme(999, "No Cast", 90.0, 100000, "01/01/2020");

        String esperado = "999 | No Cast | 2020-01-01 |  |  | 0 | 0";
        assertEquals(esperado, filme.toString());
    }
    @Test
    public void testFilmeId1001_nomeComEspacos() {
        Filme filme = new Filme(1001, " The Batman ", 175.0, 200000000, "04/03/2022");
        String esperado = "1001 |  The Batman  | 2022-03-04 | 0 | 0 | 0 | 0";
        assertEquals(esperado, filme.toString());
    }
    @Test
    public void testFilmeDataMalFormatada_naoAltera() {
        Filme filme = new Filme(5000, "Bad Format", 120.0, 100000, "2022/10/15"); // formato não tratado
        String esperado = "5000 | Bad Format | 2022-10-15 | 0 | 0 | 0 | 0";
        assertEquals(esperado, filme.toString());
    }
}
