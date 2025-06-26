package TestsJUnit;

import org.junit.jupiter.api.Test;
import pt.ulusofona.aed.deisimdb.Main;
import pt.ulusofona.aed.deisimdb.TipoEntidade;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestMainLeituraFicheiros {

    @Test
    public void testLeituraCompletaDosFicheirosSemErros() {
        File pastaFicheiros = new File("test-files/TestMain");

        boolean sucesso = Main.parseFiles(pastaFicheiros);
        System.out.println("✔ parseFiles (sem erros): " + sucesso);
        assertTrue(sucesso, "parseFiles() falhou com ficheiros válidos");

        ArrayList<String> filmes = Main.getObjects(TipoEntidade.FILME);
        System.out.println("✔ Filmes lidos: " + filmes.size());
        assertNotNull(filmes);
        assertTrue(filmes.size() >= 2, "Devem existir pelo menos 2 filmes");

        ArrayList<String> atores = Main.getObjects(TipoEntidade.ATOR);
        System.out.println("✔ Atores lidos: " + atores.size());
        assertNotNull(atores);
        assertTrue(atores.size() >= 2, "Devem existir pelo menos 2 atores");

        ArrayList<String> realizadores = Main.getObjects(TipoEntidade.REALIZADOR);
        System.out.println("✔ Realizadores lidos: " + realizadores.size());
        assertNotNull(realizadores);
        assertTrue(realizadores.size() >= 2, "Devem existir pelo menos 2 realizadores");

        ArrayList<String> generos = Main.getObjects(TipoEntidade.GENERO_CINEMATOGRAFICO);
        System.out.println("✔ Géneros lidos: " + generos.size());
        assertNotNull(generos);
        assertTrue(generos.size() >= 2, "Devem existir pelo menos 2 géneros");

        ArrayList<String> invalidos = Main.getObjects(TipoEntidade.INPUT_INVALIDO);
        System.out.println("✔ Ficheiros analisados (INPUT_INVALIDO): " + invalidos.size());
        assertNotNull(invalidos);

        for (String linha : invalidos) {
            String[] partes = linha.split(" \\| ");
            int linhasInvalidas = Integer.parseInt(partes[2].trim());
            System.out.println("   ↪ " + linha);
            assertEquals(0, linhasInvalidas, "Ficheiro contém linhas inválidas: " + linha);
        }
    }

    @Test
    public void testLeituraFicheirosComErros() {
        File pastaFicheiros = new File("test-files/TestMain");

        boolean sucesso = Main.parseFiles(pastaFicheiros);
        System.out.println("✔ parseFiles (com erros): " + sucesso);
        assertTrue(sucesso, "parseFiles() falhou ao processar ficheiros com erros");

        ArrayList<String> invalidos = Main.getObjects(TipoEntidade.INPUT_INVALIDO);
        System.out.println("✔ Total de ficheiros com erros: " + invalidos.size());
        assertNotNull(invalidos);
        assertEquals(6, invalidos.size(), "Devem existir 6 entradas (uma por ficheiro)");

        for (String linha : invalidos) {
            System.out.println("   ✖ " + linha);
            String[] partes = linha.split(" \\| ");
            assertEquals(4, partes.length, "Formato inválido na linha de erro");

            int linhasInvalidas = Integer.parseInt(partes[2].trim());
            assertTrue(linhasInvalidas > 0, "Cada ficheiro deve conter pelo menos 1 linha inválida");
        }
    }
}
