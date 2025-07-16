package TestsJUnit;
import org.junit.jupiter.api.Test;
import Main.*;

import static org.junit.jupiter.api.Assertions.*;


public class TestInsertActor {
    @Test
    void testInsertValidActor() {
        Result r = Queries.insertActor("1;Leonardo DiCaprio;M;100");
        assertFalse(r.success);
        assertEquals("INSERT_ACTOR", r.error);
        assertEquals("Erro", r.result);
        assertEquals(0, Main.atorArrayList.size());
    }

    @Test
    void testInsertActorWithInvalidGender() {
        Result r = Queries.insertActor("2;Kate Winslet;X;100");
        assertFalse(r.success);
        assertEquals("Erro", r.result);
    }

    @Test
    void testInsertActorWithExistingId() {
        Main.atorArrayList.add(new Ator(3, "Tom Hardy", 'M', 100));
        Result r = Queries.insertActor("3;Tom Hardy;M;100");
        assertFalse(r.success);
        assertEquals("Erro", r.result);
    }

    @Test
    void testInsertActorWithNonexistentMovie() {
        Result r = Queries.insertActor("4;Joseph Gordon-Levitt;M;999");
        assertFalse(r.success);
        assertEquals("Erro", r.result);
    }

    @Test
    void testInsertActorWithMalformedInput() {
        Result r = Queries.insertActor("5;Anne Hathaway;F");
        assertFalse(r.success);
        assertEquals("Erro", r.result);
    }
}
