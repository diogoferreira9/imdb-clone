package TestsJUnit;

import org.junit.jupiter.api.Test;
import Main.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAtor {

    @Test
    public void testAtorMasculino_formatacaoSimples() {
        Ator ator = new Ator(1, "João Silva", 'M', 101);
        assertEquals("1 | João Silva | Masculino | 101", ator.toString());
    }

    @Test
    public void testAtorFeminino_formatacaoSimples() {
        Ator ator = new Ator(2, "Maria Santos", 'F', 202);
        assertEquals("2 | Maria Santos | Feminino | 202", ator.toString());
    }

    @Test
    public void testAtorComEspacosNoNome() {
        Ator ator = new Ator(3, "  Ana Beatriz  ", 'F', 303);
        assertEquals("3 |   Ana Beatriz   | Feminino | 303", ator.toString());
    }

    @Test
    public void testAtorComGeneroMinusculo() {
        Ator ator = new Ator(4, "Carlos Dias", 'm', 404);
        assertEquals("4 | Carlos Dias | Feminino | 404", ator.toString());
    }

    @Test
    public void testAtorGeneroInvalido() {
        Ator ator = new Ator(5, "Desconhecido", 'X', 505);
        assertEquals("5 | Desconhecido | Feminino | 505", ator.toString());
    }
}